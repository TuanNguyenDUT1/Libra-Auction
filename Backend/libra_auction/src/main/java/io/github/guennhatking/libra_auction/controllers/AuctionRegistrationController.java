package io.github.guennhatking.libra_auction.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.guennhatking.libra_auction.services.AuctionRegistrationService;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionRegistrationCreateRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.AuctionRegistrationResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auction-registrations")
public class AuctionRegistrationController {
    private final AuctionRegistrationService auctionRegistrationService;

    public AuctionRegistrationController(AuctionRegistrationService auctionRegistrationService) {
        this.auctionRegistrationService = auctionRegistrationService;
    }

    @GetMapping
    public List<AuctionRegistrationResponse> getAllRegistrations() {
        return auctionRegistrationService.getAllRegistrations();
    }

    @GetMapping("/{id}")
    public AuctionRegistrationResponse getRegistrationById(@PathVariable String id) {
        return auctionRegistrationService.getRegistrationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionRegistrationResponse registerForAuction(@Valid @RequestBody AuctionRegistrationCreateRequest request) {
        return auctionRegistrationService.registerForAuction(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegistration(@PathVariable String id) {
        auctionRegistrationService.deleteRegistration(id);
    }

    @GetMapping("/user/{userId}")
    public List<AuctionRegistrationResponse> getRegistrationsByUserId(@PathVariable String userId) {
        return auctionRegistrationService.getRegistrationsByUserId(userId);
    }

    @GetMapping("/auction/{auctionSessionId}")
    public List<AuctionRegistrationResponse> getRegistrationsByAuctionSessionId(@PathVariable String auctionSessionId) {
        return auctionRegistrationService.getRegistrationsByAuctionSessionId(auctionSessionId);
    }
}
