package io.github.guennhatking.libra_auction.models;

/**
 * DAU_GIA_XUONG (Dutch Auction - Descending)
 * 
 * Rules:
 * - Starting price: giaKhoiDiem (very high)
 * - Price decreases automatically at regular intervals or manually
 * - First bidder to accept the current price wins immediately
 * - The winner pays the price at which they accepted
 * - Simple and fast - no competing bids
 */
public class DauGiaXuong {
    private String id;
    private String phienDauGiaId;
    
    // Starting price (highest price)
    private long giaKhoiDiem;
    
    // Current asking price (decreases over time)
    private long giaHienTai;
    
    // Price reduction per time interval
    private long buocGiaGiam;
    
    // Time interval between price reductions (in seconds)
    private long khoangThoiGianGiam;
    
    // Winner information
    private String bidderId;
    private String bidderName;
    private long finalPrice;
    
    // Auction completion flag
    private boolean daKetThuc;

    // CONSTRUCTOR
    public DauGiaXuong() {
        this.daKetThuc = false;
    }

    public DauGiaXuong(long giaKhoiDiem, long buocGiaGiam, long khoangThoiGianGiam) {
        this.giaKhoiDiem = giaKhoiDiem;
        this.giaHienTai = giaKhoiDiem;
        this.buocGiaGiam = buocGiaGiam;
        this.khoangThoiGianGiam = khoangThoiGianGiam;
        this.daKetThuc = false;
    }

    // BUSINESS LOGIC METHODS
    
    /**
     * Decrease the price by the standard increment
     * @return New current price
     */
    public long decreasePrice() {
        if (!daKetThuc && giaHienTai > buocGiaGiam) {
            giaHienTai -= buocGiaGiam;
        }
        return giaHienTai;
    }
    
    /**
     * Check if someone accepts the current price and wins
     * @param bidAmount The amount bidder offers
     * @return true if valid (accepted), false if below current price
     */
    public boolean acceptCurrentPrice(long bidAmount) {
        return !daKetThuc && bidAmount >= giaHienTai;
    }
    
    /**
     * Record the winner and finalize the auction
     * @param bidderId ID of winning bidder
     * @param bidderName Name of winning bidder
     * @param finalPrice The price they paid
     */
    public void recordWinner(String bidderId, String bidderName, long finalPrice) {
        this.bidderId = bidderId;
        this.bidderName = bidderName;
        this.finalPrice = finalPrice;
        this.giaHienTai = finalPrice;
        this.daKetThuc = true;
    }
    
    /**
     * Get the price reduction schedule
     * @return Description of price reduction
     */
    public String getPriceReductionSchedule() {
        return String.format("Price reduces by %d every %d seconds, starting from %d",
            buocGiaGiam, khoangThoiGianGiam, giaKhoiDiem);
    }

    // GETTER
    public String getId() {
        return id;
    }

    public String getPhienDauGiaId() {
        return phienDauGiaId;
    }

    public long getGiaKhoiDiem() {
        return giaKhoiDiem;
    }

    public long getGiaHienTai() {
        return giaHienTai;
    }

    public long getBuocGiaGiam() {
        return buocGiaGiam;
    }

    public long getKhoangThoiGianGiam() {
        return khoangThoiGianGiam;
    }

    public String getBidderId() {
        return bidderId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public boolean isDaKetThuc() {
        return daKetThuc;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setPhienDauGiaId(String phienDauGiaId) {
        this.phienDauGiaId = phienDauGiaId;
    }

    public void setGiaKhoiDiem(long giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
    }

    public void setGiaHienTai(long giaHienTai) {
        this.giaHienTai = giaHienTai;
    }

    public void setBuocGiaGiam(long buocGiaGiam) {
        this.buocGiaGiam = buocGiaGiam;
    }

    public void setKhoangThoiGianGiam(long khoangThoiGianGiam) {
        this.khoangThoiGianGiam = khoangThoiGianGiam;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setDaKetThuc(boolean daKetThuc) {
        this.daKetThuc = daKetThuc;
    }
}
