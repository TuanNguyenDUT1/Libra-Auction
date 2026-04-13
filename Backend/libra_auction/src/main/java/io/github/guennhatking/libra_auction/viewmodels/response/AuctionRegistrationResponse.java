package io.github.guennhatking.libra_auction.viewmodels.response;

import java.time.LocalDateTime;

public record AuctionRegistrationResponse(
    String id,
    String userId,
    String userName,
    String auctionSessionId,
    String auctionTitle,
    LocalDateTime registrationTime
) {
}
