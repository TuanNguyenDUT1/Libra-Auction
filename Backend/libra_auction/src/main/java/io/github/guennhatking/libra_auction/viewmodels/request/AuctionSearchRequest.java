package io.github.guennhatking.libra_auction.viewmodels.request;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record AuctionSearchRequest(
        // Product search
        String name,
        String categoryId,

        // Price range
        Long priceFrom,
        Long priceTo,
        Long startingPrice,

        // Time range
        OffsetDateTime timeStart,
        OffsetDateTime timeEnd,

        // Attributes filter
        List<Map<String, String>> attributes,

        // Status filters
        String status,

        // Pagination
        Integer page,
        Integer pageSize,

        // Sorting
        String sortBy,
        String sortOrder) {
}
