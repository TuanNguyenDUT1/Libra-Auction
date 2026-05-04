package io.github.guennhatking.libra_auction.viewmodels.request;

import java.util.List;
import java.util.Map;

public record ProductSearchRequest(
        // Product search
        String name,
        String categoryId,

        // Attributes filter (Dựa trên thuocTinhTaiSanList)
        List<Map<String, String>> attributes,

        // Pagination
        Integer page,
        Integer pageSize,

        // Sorting
        String sortBy,
        String sortOrder,
        
        // Creator/Owner id – optional, null when not filtering by creator
        String nguoiTaoId) {

    // Secondary constructor for standard calls (without creator id)
    public ProductSearchRequest(String name, String categoryId,
                                List<Map<String, String>> attributes,
                                Integer page, Integer pageSize,
                                String sortBy, String sortOrder) {
        this(name, categoryId, attributes, page, pageSize, sortBy, sortOrder, null);
    }
}