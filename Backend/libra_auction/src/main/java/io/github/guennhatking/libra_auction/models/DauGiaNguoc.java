package io.github.guennhatking.libra_auction.models;

/**
 * DAU_GIA_NGUOC (Reverse Dutch Auction)
 * 
 * Rules:
 * - Starting price is LOW (giaKhoiDiem - starting low)
 * - Price increases automatically at regular intervals
 * - Buyers decide when to accept the current price
 * - First buyer to accept wins and pays that price
 * - Commonly used for procurement (suppliers offer services at increasing prices)
 */
public class DauGiaNguoc {
    private String id;
    private String phienDauGiaId;
    
    // Starting price (low starting point)
    private long giaKhoiDiem;
    
    // Current asking price (increases over time)
    private long giaHienTai;
    
    // Price increment per time interval
    private long buocGiaGi;
    
    // Time interval between price increases (in seconds)
    private long khoangThoiGianGi;
    
    // Maximum price limit (reserve/ceiling price)
    private long giaThoiHan;
    
    // Winner information
    private String bidderId;
    private String bidderName;
    private long finalPrice;
    
    // Auction completion flag
    private boolean daKetThuc;
    
    // Number of price increases so far
    private int soLanGiTang;

    // CONSTRUCTOR
    public DauGiaNguoc() {
        this.daKetThuc = false;
        this.soLanGiTang = 0;
    }

    public DauGiaNguoc(long giaKhoiDiem, long giaThoiHan, long buocGiaGi, long khoangThoiGianGi) {
        this.giaKhoiDiem = giaKhoiDiem;
        this.giaHienTai = giaKhoiDiem;
        this.giaThoiHan = giaThoiHan;
        this.buocGiaGi = buocGiaGi;
        this.khoangThoiGianGi = khoangThoiGianGi;
        this.daKetThuc = false;
        this.soLanGiTang = 0;
    }

    // BUSINESS LOGIC METHODS
    
    /**
     * Increase the price by the standard increment
     * @return New current price
     */
    public long increasePrice() {
        if (!daKetThuc && giaHienTai < giaThoiHan) {
            giaHienTai += buocGiaGi;
            if (giaHienTai > giaThoiHan) {
                giaHienTai = giaThoiHan;
            }
            soLanGiTang++;
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
     * Check if price has reached the maximum threshold
     * @return true if at or above ceiling price
     */
    public boolean hasReachedMaxPrice() {
        return giaHienTai >= giaThoiHan;
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
     * Get how many price increase intervals remaining
     * @return Remaining intervals before reaching ceiling
     */
    public long getRemainingIntervals() {
        if (giaHienTai >= giaThoiHan) {
            return 0;
        }
        return (giaThoiHan - giaHienTai) / buocGiaGi;
    }
    
    /**
     * Get the price increase schedule
     * @return Description of price increase
     */
    public String getPriceIncreaseSchedule() {
        return String.format("Price increases by %d every %d seconds, from %d to %d (max)",
            buocGiaGi, khoangThoiGianGi, giaKhoiDiem, giaThoiHan);
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

    public long getBuocGiaGi() {
        return buocGiaGi;
    }

    public long getKhoangThoiGianGi() {
        return khoangThoiGianGi;
    }

    public long getGiaThoiHan() {
        return giaThoiHan;
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

    public int getSoLanGiTang() {
        return soLanGiTang;
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

    public void setBuocGiaGi(long buocGiaGi) {
        this.buocGiaGi = buocGiaGi;
    }

    public void setKhoangThoiGianGi(long khoangThoiGianGi) {
        this.khoangThoiGianGi = khoangThoiGianGi;
    }

    public void setGiaThoiHan(long giaThoiHan) {
        this.giaThoiHan = giaThoiHan;
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

    public void setSoLanGiTang(int soLanGiTang) {
        this.soLanGiTang = soLanGiTang;
    }
}