package io.github.guennhatking.libra_auction.services;

import io.github.guennhatking.libra_auction.enums.auction.TrangThaiPhien;
import io.github.guennhatking.libra_auction.models.auction.KetQuaDauGia;
import io.github.guennhatking.libra_auction.models.auction.PhienDauGia;
import io.github.guennhatking.libra_auction.repositories.auction.KetQuaDauGiaRepository;
import io.github.guennhatking.libra_auction.repositories.auction.PhienDauGiaRepository;
import io.github.guennhatking.libra_auction.viewmodels.response.BidResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * Service to handle auction state transitions
 * Manages transitions from CHUA_BAT_DAU -> DANG_DIEN_RA -> DA_KET_THUC
 */
@Service
public class AuctionStateTransitionService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuctionStateTransitionService.class);
    
    private final PhienDauGiaRepository phienDauGiaRepository;
    private final KetQuaDauGiaRepository ketQuaDauGiaRepository;
    private final BidHistoryService bidHistoryService;
    private final SimpMessagingTemplate messagingTemplate;
    private final EmailNotificationService emailNotificationService;
    
    public AuctionStateTransitionService(
            PhienDauGiaRepository phienDauGiaRepository,
            KetQuaDauGiaRepository ketQuaDauGiaRepository,
            BidHistoryService bidHistoryService,
            SimpMessagingTemplate messagingTemplate,
            EmailNotificationService emailNotificationService) {
        this.phienDauGiaRepository = phienDauGiaRepository;
        this.ketQuaDauGiaRepository = ketQuaDauGiaRepository;
        this.bidHistoryService = bidHistoryService;
        this.messagingTemplate = messagingTemplate;
        this.emailNotificationService = emailNotificationService;
    }
    
    /**
     * Transition an auction to STARTED state (DANG_DIEN_RA)
     * - Send start notifications to participants
     * - Change status in database
     * @param auctionId The auction ID
     */
    @Transactional
    public void startAuction(String auctionId) {
        try {
            Optional<PhienDauGia> auctionOpt = phienDauGiaRepository.findById(auctionId);
            if (auctionOpt.isEmpty()) {
                logger.warn("Auction not found: {}", auctionId);
                return;
            }
            
            PhienDauGia auction = auctionOpt.get();
            
            // Only transition if currently in CHUA_BAT_DAU state
            if (auction.getTrangThaiPhien() != TrangThaiPhien.CHUA_BAT_DAU) {
                logger.warn("Auction {} is not in CHUA_BAT_DAU state, current: {}", 
                    auctionId, auction.getTrangThaiPhien());
                return;
            }
            
            // Change status to DANG_DIEN_RA
            auction.setTrangThaiPhien(TrangThaiPhien.DANG_DIEN_RA);
            phienDauGiaRepository.save(auction);
            
            logger.info("Auction {} started", auctionId);
            
            // Send email notification
            try {
                emailNotificationService.sendAuctionStartedNotification(auction);
            } catch (Exception e) {
                logger.error("Failed to send auction start email notification", e);
            }
            
            // Send WebSocket notification
            sendAuctionStatusUpdate(auctionId, TrangThaiPhien.DANG_DIEN_RA.toString());
            
        } catch (Exception e) {
            logger.error("Error starting auction {}: {}", auctionId, e.getMessage(), e);
        }
    }
    
    /**
     * Transition an auction to ENDED state (DA_KET_THUC)
     * - Find the winner
     * - Create KetQuaDauGia (auction result)
     * - Send notification emails to winner and auction creator
     * - Change status in database
     * @param auctionId The auction ID
     */
    @Transactional
    public void endAuction(String auctionId) {
        try {
            Optional<PhienDauGia> auctionOpt = phienDauGiaRepository.findById(auctionId);
            if (auctionOpt.isEmpty()) {
                logger.warn("Auction not found: {}", auctionId);
                return;
            }
            
            PhienDauGia auction = auctionOpt.get();
            
            // Only transition if currently in DANG_DIEN_RA state
            if (auction.getTrangThaiPhien() != TrangThaiPhien.DANG_DIEN_RA) {
                logger.warn("Auction {} is not in DANG_DIEN_RA state, current: {}", 
                    auctionId, auction.getTrangThaiPhien());
                return;
            }
            
            // Find the winner (latest bid from history)
            BidResponse latestBidResponse = bidHistoryService.getLatestBid(auctionId);
            
            // Create KetQuaDauGia (Auction Result)
            KetQuaDauGia result = new KetQuaDauGia();
            result.setPhienDauGia(auction);
            result.setThoiGianKetThuc(OffsetDateTime.now(ZoneOffset.ofHours(7)));
            
            if (latestBidResponse != null) {
                // There is a winner - set the winner and price
                // Note: We're using the BidResponse, not database record
                // In production, you should query NguoiDung by email from latestBidResponse.bidderId
                result.setGiaTrungDauGia(latestBidResponse.bidAmount());
                
                logger.info("Auction {} ended with winner bid: {} VND", 
                    auctionId, latestBidResponse.bidAmount());
            } else {
                // No bids placed - auction ends with no winner
                logger.info("Auction {} ended with no bids", auctionId);
            }
            
            KetQuaDauGia savedResult = ketQuaDauGiaRepository.save(result);
            auction.setKetQuaDauGia(savedResult);
            
            // Change status to DA_KET_THUC
            auction.setTrangThaiPhien(TrangThaiPhien.DA_KET_THUC);
            phienDauGiaRepository.save(auction);
            
            // Send email notifications
            try {
                if (latestBidResponse != null) {
                    // Send winner notification (to bidder email)
                    logger.info("Sending winner notification to {}", latestBidResponse.bidderId());
                }
                emailNotificationService.sendAuctionEndedNotification(auction);
            } catch (Exception e) {
                logger.error("Failed to send auction end email notifications", e);
            }
            
            // Send WebSocket notification
            sendAuctionStatusUpdate(auctionId, TrangThaiPhien.DA_KET_THUC.toString());
            
        } catch (Exception e) {
            logger.error("Error ending auction {}: {}", auctionId, e.getMessage(), e);
        }
    }
    
    /**
     * Send auction status update via WebSocket
     * @param auctionId The auction ID
     * @param status The new status
     */
    private void sendAuctionStatusUpdate(String auctionId, String status) {
        try {
            messagingTemplate.convertAndSend(
                "/topic/auction/" + auctionId + "/status",
                "{\"auctionId\":\"" + auctionId + "\",\"status\":\"" + status + "\",\"timestamp\":\"" + OffsetDateTime.now(ZoneOffset.ofHours(7)) + "\"}"
            );
        } catch (Exception e) {
            logger.error("Failed to send WebSocket notification for auction {}", auctionId, e);
        }
    }
}
