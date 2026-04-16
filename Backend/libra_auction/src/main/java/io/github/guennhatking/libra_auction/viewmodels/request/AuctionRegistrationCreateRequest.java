package io.github.guennhatking.libra_auction.viewmodels.request;

import jakarta.validation.constraints.NotBlank;

public record AuctionRegistrationCreateRequest(
    @NotBlank(message = "userId is required")
    String userId,

    @NotBlank(message = "auctionSessionId is required")
    String auctionSessionId
) {
}
