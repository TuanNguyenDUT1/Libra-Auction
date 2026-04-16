package io.github.guennhatking.libra_auction.controllers;

import io.github.guennhatking.libra_auction.enums.Enums;
import io.github.guennhatking.libra_auction.models.PhienDauGia;
import io.github.guennhatking.libra_auction.repositories.PhienDauGiaRepository;
import io.github.guennhatking.libra_auction.services.AuctionStateRedisService;
import io.github.guennhatking.libra_auction.services.AuctionWebSocketNotificationService;
import io.github.guennhatking.libra_auction.viewmodels.request.BidMessage;
import io.github.guennhatking.libra_auction.viewmodels.response.BidResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Controller for handling auction bids
 * Supports 4 types of auctions:
 * - DAU_GIA_LEN (Ascending Auction)
 * - DAU_GIA_XUONG (Descending/Dutch Auction)
 * - DAU_GIA_KIN (Sealed Bid Auction)
 * - DAU_GIA_NGUOC (Reverse Dutch Auction)
 */
@Controller
public class AuctionWebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final PhienDauGiaRepository phienDauGiaRepository;
    private final AuctionStateRedisService auctionStateRedisService;
    private final AuctionWebSocketNotificationService auctionWebSocketNotificationService;
    
    // In-memory storage for bid history per auction
    private static final Map<String, List<BidResponse>> auctionBids = new ConcurrentHashMap<>();
    
    // Current winner tracking for sealed bid auctions (revealed only at end)
    private static final Map<String, BidResponse> sealedBidWinners = new ConcurrentHashMap<>();
    
    // Configuration
    private static final int FINAL_MINUTES_WINDOW = 5;
    private static final int EXTENSION_MINUTES = 5;

    public AuctionWebSocketController(SimpMessagingTemplate messagingTemplate,
                                      PhienDauGiaRepository phienDauGiaRepository,
                                      AuctionStateRedisService auctionStateRedisService,
                                      AuctionWebSocketNotificationService auctionWebSocketNotificationService) {
        this.messagingTemplate = messagingTemplate;
        this.phienDauGiaRepository = phienDauGiaRepository;
        this.auctionStateRedisService = auctionStateRedisService;
        this.auctionWebSocketNotificationService = auctionWebSocketNotificationService;
    }

    /**
     * Main bid handler - routes to appropriate auction type handler
     */
    @MessageMapping("/bid")
    public void handleBid(BidMessage bidMessage) {
        try {
            // Validate auction exists
            PhienDauGia auction = phienDauGiaRepository.findById(bidMessage.getAuctionId())
                .orElseThrow(() -> new IllegalArgumentException("Auction not found"));

            // Validate auction is active
            if (!auction.getTrangThaiPhien().equals(Enums.TrangThaiPhien.DANG_DIEN_RA)) {
                sendErrorNotification(bidMessage.getAuctionId(), 
                    "Auction is not active. Current status: " + auction.getTrangThaiPhien());
                return;
            }

            // Route to appropriate handler based on auction type
            switch (auction.getLoaiDauGia()) {
                case DAU_GIA_LEN:
                    handleAscendingAuction(bidMessage, auction);
                    break;
                case DAU_GIA_XUONG:
                    handleDescendingAuction(bidMessage, auction);
                    break;
                case DAU_GIA_KIN:
                    handleSealedBidAuction(bidMessage, auction);
                    break;
                case DAU_GIA_NGUOC:
                    handleReverseAuction(bidMessage, auction);
                    break;
                default:
                    sendErrorNotification(bidMessage.getAuctionId(), 
                        "Unknown auction type: " + auction.getLoaiDauGia());
            }
            
            // After processing bid, check if auction should be extended
            // If a bid is placed within 5 minutes of end time, extend by 5 minutes
            checkAndExtendAuctionIfNeeded(bidMessage.getAuctionId());

        } catch (IllegalArgumentException e) {
            sendErrorNotification(bidMessage.getAuctionId(), e.getMessage());
        } catch (Exception e) {
            sendErrorNotification(bidMessage.getAuctionId(), 
                "Internal error: " + e.getMessage());
        }
    }

    /**
     * DAU_GIA_LEN (English Auction - Ascending)
     * Rules:
     * - Starting price is giaKhoiDiem
     * - Each bid must be at least (currentPrice + buocGiaNhoNhat)
     * - Highest bidder at the end wins
     * - All bids are visible to participants
     */
    private void handleAscendingAuction(BidMessage bidMessage, PhienDauGia auction) {
        long minimumBid = auction.getGiaHienTai() > 0 
            ? auction.getGiaHienTai() + auction.getBuocGiaNhoNhat()
            : auction.getGiaKhoiDiem();

        // Validate bid amount
        if (bidMessage.getBidAmount() < minimumBid) {
            sendErrorNotification(bidMessage.getAuctionId(), 
                String.format("Bid must be at least %.0f (current price: %.0f + minimum step: %.0f)",
                    minimumBid, auction.getGiaHienTai(), auction.getBuocGiaNhoNhat()));
            return;
        }

        // Create and store bid response
        BidResponse bidResponse = createBidResponse(bidMessage, "SUCCESS");
        recordBid(bidMessage.getAuctionId(), bidResponse);

        // Update current price
        auction.setGiaHienTai(bidMessage.getBidAmount());
        phienDauGiaRepository.save(auction);

        // Broadcast to all participants (bids are visible)
        broadcastBid(bidMessage.getAuctionId(), bidResponse);
    }

    /**
     * DAU_GIA_XUONG (Dutch Auction - Descending)
     * Rules:
     * - Starting price is giaKhoiDiem (very high)
     * - Price decreases automatically over time or manually
     * - First bidder to accept the current price wins immediately
     * - Winner gets the item at that price
     */
    private void handleDescendingAuction(BidMessage bidMessage, PhienDauGia auction) {
        // In Dutch auction, any bid accepts the current price
        if (auction.getGiaHienTai() == 0) {
            auction.setGiaHienTai(auction.getGiaKhoiDiem());
        }

        // Bid amount should equal or be close to current price
        if (bidMessage.getBidAmount() < auction.getGiaHienTai()) {
            sendErrorNotification(bidMessage.getAuctionId(), 
                String.format("Bid (%.0f) must be at least current price (%.0f)",
                    bidMessage.getBidAmount(), auction.getGiaHienTai()));
            return;
        }

        // Create bid response
        BidResponse bidResponse = createBidResponse(bidMessage, "WINNER");
        recordBid(bidMessage.getAuctionId(), bidResponse);

        // Update auction - mark as sold to first bidder
        auction.setGiaHienTai(bidMessage.getBidAmount());
        auction.setTrangThaiPhien(Enums.TrangThaiPhien.DA_KET_THUC);
        phienDauGiaRepository.save(auction);

        // Broadcast winner announcement
        messagingTemplate.convertAndSend(
            "/topic/auction/" + bidMessage.getAuctionId(),
            createWinnerMessage(bidResponse, "Auction won! Item sold at: " + bidMessage.getBidAmount())
        );
    }

    /**
     * DAU_GIA_KIN (Sealed Bid Auction)
     * Rules:
     * - All bids are kept secret during the auction
     * - Bidders don't see each other's bids or current price
     * - Only the winner is revealed after auction ends
     * - Highest unique bid wins (or second-highest price is paid in some variants)
     */
    private void handleSealedBidAuction(BidMessage bidMessage, PhienDauGia auction) {
        // Validate minimum bid
        if (bidMessage.getBidAmount() < auction.getGiaKhoiDiem()) {
            sendErrorNotification(bidMessage.getAuctionId(), 
                "Bid amount must be at least: " + auction.getGiaKhoiDiem());
            return;
        }

        // Create bid response but DON'T show details to others
        BidResponse bidResponse = createBidResponse(bidMessage, "SEALED");
        recordBid(bidMessage.getAuctionId(), bidResponse);

        // Update winner tracking (highest bid so far)
        updateSealedBidWinner(bidMessage.getAuctionId(), bidResponse);

        // Send confirmation ONLY to the bidder (not public)
        messagingTemplate.convertAndSendToUser(
            bidMessage.getBidderId(),
            "/queue/bid-confirmation/" + bidMessage.getAuctionId(),
            createConfirmation(bidMessage, "Your sealed bid has been received and recorded")
        );

        // Send generic message to all (no bid amounts revealed)
        messagingTemplate.convertAndSend(
            "/topic/auction/" + bidMessage.getAuctionId(),
            createGenericNotification("A new sealed bid has been registered")
        );
    }

    /**
     * DAU_GIA_NGUOC (Reverse Dutch Auction)
     * Rules:
     * - Starting price is low (giaKhoiDiem)
     * - Price increases over time
     * - Buyers decide when to accept (similar to regular Dutch but inverted)
     * - First to accept the current price wins
     */
    private void handleReverseAuction(BidMessage bidMessage, PhienDauGia auction) {
        long currentPrice = auction.getGiaHienTai() > 0 
            ? auction.getGiaHienTai()
            : auction.getGiaKhoiDiem();

        // Bid must accept or exceed current asking price
        if (bidMessage.getBidAmount() < currentPrice) {
            sendErrorNotification(bidMessage.getAuctionId(), 
                String.format("Bid (%.0f) must be at least asking price (%.0f)",
                    bidMessage.getBidAmount(), currentPrice));
            return;
        }

        // Create bid response
        BidResponse bidResponse = createBidResponse(bidMessage, "WINNER");
        recordBid(bidMessage.getAuctionId(), bidResponse);

        // Update auction - mark as sold
        auction.setGiaHienTai(bidMessage.getBidAmount());
        auction.setTrangThaiPhien(Enums.TrangThaiPhien.DA_KET_THUC);
        phienDauGiaRepository.save(auction);

        // Broadcast winner
        messagingTemplate.convertAndSend(
            "/topic/auction/" + bidMessage.getAuctionId(),
            createWinnerMessage(bidResponse, "Auction won at: " + bidMessage.getBidAmount())
        );
    }

    /**
     * Helper: Create standard bid response
     */
    private BidResponse createBidResponse(BidMessage message, String status) {
        return new BidResponse(
            message.getAuctionId(),
            message.getBidAmount(),
            message.getBidderId(),
            message.getBidderName(),
            LocalDateTime.now(),
            status
        );
    }

    /**
     * Helper: Record bid in history
     */
    private void recordBid(String auctionId, BidResponse bidResponse) {
        auctionBids.computeIfAbsent(auctionId, k -> new ArrayList<>())
            .add(bidResponse);
    }

    /**
     * Helper: Broadcast bid to all auction participants
     */
    private void broadcastBid(String auctionId, BidResponse bidResponse) {
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId, bidResponse);
    }

    /**
     * Helper: Update winner for sealed bid auctions
     */
    private void updateSealedBidWinner(String auctionId, BidResponse bidResponse) {
        BidResponse currentWinner = sealedBidWinners.get(auctionId);
        if (currentWinner == null || bidResponse.getBidAmount() > currentWinner.getBidAmount()) {
            sealedBidWinners.put(auctionId, bidResponse);
        }
    }

    /**
     * Helper: Create winner message
     */
    private BidResponse createWinnerMessage(BidResponse bidResponse, String message) {
        BidResponse winnerMsg = new BidResponse();
        winnerMsg.setAuctionId(bidResponse.getAuctionId());
        winnerMsg.setBidderId(bidResponse.getBidderId());
        winnerMsg.setBidderName(bidResponse.getBidderName());
        winnerMsg.setBidAmount(bidResponse.getBidAmount());
        winnerMsg.setBidTime(LocalDateTime.now());
        winnerMsg.setStatus("WINNER");
        return winnerMsg;
    }

    /**
     * Helper: Create confirmation message for sealed bids
     */
    private BidResponse createConfirmation(BidMessage message, String confirmationText) {
        BidResponse confirmation = new BidResponse();
        confirmation.setAuctionId(message.getAuctionId());
        confirmation.setBidderId(message.getBidderId());
        confirmation.setBidAmount(message.getBidAmount());
        confirmation.setBidTime(LocalDateTime.now());
        confirmation.setStatus("CONFIRMED");
        return confirmation;
    }

    /**
     * Helper: Create generic notification (no sensitive details)
     */
    private BidResponse createGenericNotification(String message) {
        BidResponse notification = new BidResponse();
        notification.setStatus("NOTIFICATION");
        notification.setBidTime(LocalDateTime.now());
        return notification;
    }

    /**
     * Helper: Send error notification
     */
    private void sendErrorNotification(String auctionId, String errorMessage) {
        BidResponse errorResponse = new BidResponse();
        errorResponse.setAuctionId(auctionId);
        errorResponse.setStatus("ERROR");
        errorResponse.setBidTime(LocalDateTime.now());
        messagingTemplate.convertAndSend("/topic/auction/" + auctionId, errorResponse);
    }

    /**
     * Get bid history for an auction (for viewing past bids)
     */
    public List<BidResponse> getBidHistory(String auctionId) {
        return auctionBids.getOrDefault(auctionId, new ArrayList<>());
    }

    /**
     * Get sealed bid winner (called when auction ends)
     */
    public BidResponse getSealedBidWinner(String auctionId) {
        return sealedBidWinners.get(auctionId);
    }

    /**
     * Clear auction data (cleanup after auction ends)
     */
    public void clearAuctionData(String auctionId) {
        auctionBids.remove(auctionId);
        sealedBidWinners.remove(auctionId);
    }
    
    /**
     * Check if auction is within the final minutes and extend if needed
     * If a bid is placed within FINAL_MINUTES_WINDOW (5 minutes), extend by EXTENSION_MINUTES (5 minutes)
     * @param auctionId The auction ID
     */
    private void checkAndExtendAuctionIfNeeded(String auctionId) {
        try {
            // Check if auction is within final 5 minutes
            if (auctionStateRedisService.isWithinFinalMinutes(auctionId, FINAL_MINUTES_WINDOW)) {
                // Get current end time
                Long currentEndTimeMillis = auctionStateRedisService.getAuctionEndTime(auctionId);
                if (currentEndTimeMillis != null) {
                    // Calculate new end time (5 minutes later)
                    LocalDateTime currentEndTime = java.time.Instant.ofEpochMilli(currentEndTimeMillis).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                    LocalDateTime newEndTime = currentEndTime.plusMinutes(EXTENSION_MINUTES);
                    
                    // Update Redis with new end time
                    auctionStateRedisService.extendAuctionEnd(auctionId, newEndTime);
                    
                    // Notify all participants about the extension
                    auctionWebSocketNotificationService.sendAuctionExtensionNotification(auctionId, newEndTime);
                }
            }
        } catch (Exception e) {
            // Log error but don't fail the bid
            System.err.println("Error checking/extending auction: " + e.getMessage());
        }
    }
}

