package io.github.guennhatking.libra_auction.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Service to schedule and manage automatic auction state transitions
 * Uses Redis ZSET to track events and @Scheduled to periodically check
 * 
 * Scheduler runs every 10 seconds to check for:
 * 1. Auctions that should start
 * 2. Auctions that should end
 */
@Service
@EnableScheduling
public class AuctionSchedulerService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuctionSchedulerService.class);
    
    private final AuctionStateRedisService auctionStateRedisService;
    private final AuctionStateTransitionService auctionStateTransitionService;
    
    public AuctionSchedulerService(
            AuctionStateRedisService auctionStateRedisService,
            AuctionStateTransitionService auctionStateTransitionService) {
        this.auctionStateRedisService = auctionStateRedisService;
        this.auctionStateTransitionService = auctionStateTransitionService;
    }
    
    /**
     * Check for auctions that should start
     * Runs every 10 seconds
     */
    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void checkAuctionStarting() {
        try {
            LocalDateTime now = LocalDateTime.now();
            Set<String> auctionsToStart = auctionStateRedisService.getAuctionsToStart(now);
            
            if (!auctionsToStart.isEmpty()) {
                logger.info("Found {} auctions to start", auctionsToStart.size());
                
                for (String auctionId : auctionsToStart) {
                    try {
                        auctionStateTransitionService.startAuction(auctionId);
                        auctionStateRedisService.removeAuctionStartEvent(auctionId);
                    } catch (Exception e) {
                        logger.error("Error starting auction {}: {}", auctionId, e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in checkAuctionStarting: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Check for auctions that should end
     * Runs every 10 seconds
     */
    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void checkAuctionEnding() {
        try {
            LocalDateTime now = LocalDateTime.now();
            Set<String> auctionsToEnd = auctionStateRedisService.getAuctionsToEnd(now);
            
            if (!auctionsToEnd.isEmpty()) {
                logger.info("Found {} auctions to end", auctionsToEnd.size());
                
                for (String auctionId : auctionsToEnd) {
                    try {
                        auctionStateTransitionService.endAuction(auctionId);
                        auctionStateRedisService.removeAuctionEndEvent(auctionId);
                    } catch (Exception e) {
                        logger.error("Error ending auction {}: {}", auctionId, e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in checkAuctionEnding: {}", e.getMessage(), e);
        }
    }
}
