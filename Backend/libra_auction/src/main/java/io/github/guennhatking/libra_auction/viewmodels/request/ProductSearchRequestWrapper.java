package io.github.guennhatking.libra_auction.viewmodels.request;

/**
 * Utility to produce a copy of a {@link ProductSearchRequest} with a creator id (nguoiTaoId).
 * This avoids changing the constructor signature of the original record for every call.
 */
public final class ProductSearchRequestWrapper {
    private ProductSearchRequestWrapper() {}

    public static ProductSearchRequest withCreatorId(ProductSearchRequest original, String creatorId) {
        return new ProductSearchRequest(
                original.name(),
                original.categoryId(),
                original.attributes(),
                original.page(),
                original.pageSize(),
                original.sortBy(),
                original.sortOrder(),
                creatorId);
    }
}