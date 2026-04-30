package io.github.guennhatking.libra_auction.controllers;

import io.github.guennhatking.libra_auction.services.AuctionSearchService;
import io.github.guennhatking.libra_auction.services.AuctionService;
import io.github.guennhatking.libra_auction.utils.ParseDateTime;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionCreateRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionSearchRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionUpdateRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.AuctionResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.PageResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.ServerAPIResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api")
public class AuctionController {
    private final AuctionService auctionService;
    private final AuctionSearchService searchService;

    public AuctionController(AuctionService auctionService,
            AuctionSearchService searchService) {
        this.auctionService = auctionService;
        this.searchService = searchService;
    }

    @GetMapping("/public/categories/{categoryId}/auctions")
    public ResponseEntity<ServerAPIResponse<PageResponse<AuctionResponse>>> getAuctionsByCategory(
            @PathVariable String categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long priceFrom,
            @RequestParam(required = false) Long priceTo,
            @RequestParam(required = false) Long startingPrice,
            @RequestParam(required = false) String timeStart,
            @RequestParam(required = false) String timeEnd,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "thoiGianBatDau") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {

        AuctionSearchRequest criteria = buildSearchCriteria(
                name, categoryId, priceFrom, priceTo, startingPrice,
                timeStart, timeEnd, status,
                page, pageSize, sortBy, sortOrder);

        PageResponse<AuctionResponse> result = searchService.searchAuctions(criteria);

        return ResponseEntity.ok(ServerAPIResponse.success(result));
    }

    @GetMapping("/public/auctions")
    public ResponseEntity<ServerAPIResponse<PageResponse<AuctionResponse>>> getAuctions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long priceFrom,
            @RequestParam(required = false) Long priceTo,
            @RequestParam(required = false) Long startingPrice,
            @RequestParam(required = false) String timeStart,
            @RequestParam(required = false) String timeEnd,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "thoiGianBatDau") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {

        AuctionSearchRequest criteria = buildSearchCriteria(
                name, null, priceFrom, priceTo, startingPrice,
                timeStart, timeEnd, status,
                page, pageSize, sortBy, sortOrder);

        PageResponse<AuctionResponse> result = searchService.searchAuctions(criteria);

        return ResponseEntity.ok(ServerAPIResponse.success(result));
    }

    @GetMapping("/public/categories/{categoryId}/auctions/{id}")
    public ResponseEntity<ServerAPIResponse<AuctionResponse>> getAuctionByIdInCategory(
            @PathVariable String categoryId,
            @PathVariable String id) {

        AuctionResponse response = auctionService.getAuctionByIdAndCategory(id, categoryId);

        return ResponseEntity.ok(ServerAPIResponse.success(response));
    }

    @PostMapping("/auctions")
    public ResponseEntity<ServerAPIResponse<AuctionResponse>> createAuction(
            @Valid @RequestBody AuctionCreateRequest request) {

        AuctionResponse response = auctionService.createAuction(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ServerAPIResponse.success(response));
    }

    @PutMapping("/auctions/{id}")
    public ResponseEntity<ServerAPIResponse<AuctionResponse>> updateAuction(
            @PathVariable String id,
            @Valid @RequestBody AuctionUpdateRequest request) {

        AuctionResponse response = auctionService.updateAuction(id, request);

        return ResponseEntity.ok(ServerAPIResponse.success(response));
    }

    @DeleteMapping("/auctions/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable String id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }

    private AuctionSearchRequest buildSearchCriteria(
            String name, String categoryId, Long priceFrom, Long priceTo, Long startingPrice,
            String timeStart, String timeEnd, String status,
            Integer page, Integer pageSize, String sortBy, String sortOrder) {

        // Logic parse thời gian
        OffsetDateTime parsedStart = ParseDateTime.parse(timeStart);
        OffsetDateTime parsedEnd = ParseDateTime.parse(timeEnd);

        // Gọi constructor của Record (PHẢI ĐỦ 13 THAM SỐ theo định nghĩa Record)
        return new AuctionSearchRequest(
                name,
                categoryId,
                priceFrom,
                priceTo,
                startingPrice,
                parsedStart,
                parsedEnd,
                null,
                status,
                page,
                pageSize,
                sortBy,
                sortOrder);
    }
}
