package io.github.guennhatking.libra_auction.viewmodels.request;

/**
 * Utility to produce a copy of an {@link AuctionSearchRequest} with an owner id (chuSoHuuId).
 * This avoids changing the constructor signature of the original record for every call.
 */
public final class AuctionSearchRequestWrapper {
    private AuctionSearchRequestWrapper() {}

    public static AuctionSearchRequest withOwnerId(AuctionSearchRequest original, String ownerId) {
        return new AuctionSearchRequest(
                original.name(),
                original.categoryId(),
                original.priceFrom(),
                original.priceTo(),
                original.startingPrice(),
                original.timeStart(),
                original.timeEnd(),
                original.attributes(),
                original.status(),
                original.trangThaiKiemDuyet(),
                original.page(),
                original.pageSize(),
                original.sortBy(),
                original.sortOrder(),
                ownerId);
    }
}
