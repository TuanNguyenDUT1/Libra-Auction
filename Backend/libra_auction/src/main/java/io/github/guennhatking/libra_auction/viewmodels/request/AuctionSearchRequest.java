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
        String trangThaiKiemDuyet,

        // Pagination
        Integer page,
        Integer pageSize,

        // Sorting
        String sortBy,
        String sortOrder,
        // Owner id – optional, null when not filtering by owner
        String chuSoHuuId) {

    // Secondary constructor for legacy calls (13 args)
    public AuctionSearchRequest(String name, String categoryId,
                               Long priceFrom, Long priceTo, Long startingPrice,
                               OffsetDateTime timeStart, OffsetDateTime timeEnd,
                               List<Map<String, String>> attributes,
                               String status, Integer page, Integer pageSize,
                               String sortBy, String sortOrder) {
        this(name, categoryId, priceFrom, priceTo, startingPrice,
                timeStart, timeEnd, attributes, status, null, page, pageSize,
                sortBy, sortOrder, null);
    }
}
