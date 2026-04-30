package io.github.guennhatking.libra_auction.viewmodels.response;

import java.time.OffsetDateTime;

public record BidResponse(
        String auctionId,
        Long bidAmount,
        String bidderId,
        String bidderName,
        OffsetDateTime bidTime,
        String status) {
}