package io.github.guennhatking.libra_auction.models;

/**
 * DAU_GIA_LEN (English Auction - Ascending)
 * 
 * Rules:
 * - Starting price: giaKhoiDiem (minimum acceptable price)
 * - Bidders compete by placing increasingly higher bids
 * - Each new bid must exceed the previous bid by at least giaMoiThapNhat (minimum bidding increment)
 * - Highest bidder at the end of the auction wins
 * - All bids are visible to all participants
 */
public class DauGiaLen {
    private String id;
    private String phienDauGiaId;
    
    // Current highest bid (giaKinTamThoi)
    private long giaKinTamThoi;
    
    // Minimum bidding increment required between consecutive bids
    private long giaMoiThapNhat;
    
    // Current highest bidder information
    private String bidderId;
    private String bidderName;
    
    // Number of bids placed in this auction
    private int soBidDaRa;

    // CONSTRUCTOR
    public DauGiaLen() {
        this.soBidDaRa = 0;
    }

    public DauGiaLen(long giaMoiThapNhat) {
        this.giaMoiThapNhat = giaMoiThapNhat;
        this.giaKinTamThoi = 0;
        this.soBidDaRa = 0;
    }

    // BUSINESS LOGIC METHODS
    
    /**
     * Validate if a new bid is acceptable for ascending auction
     * @param newBidAmount The new bid amount to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidBid(long newBidAmount) {
        return newBidAmount >= giaKinTamThoi + giaMoiThapNhat;
    }
    
    /**
     * Get the minimum acceptable bid for the next round
     * @return Minimum amount for the next bid
     */
    public long getNextMinimumBid() {
        return giaKinTamThoi + giaMoiThapNhat;
    }
    
    /**
     * Record a new winning bid
     * @param bidAmount The bid amount
     * @param bidderId ID of the bidder
     * @param bidderName Name of the bidder
     */
    public void placeBid(long bidAmount, String bidderId, String bidderName) {
        if (isValidBid(bidAmount)) {
            this.giaKinTamThoi = bidAmount;
            this.bidderId = bidderId;
            this.bidderName = bidderName;
            this.soBidDaRa++;
        }
    }

    // GETTER
    public String getId() {
        return id;
    }

    public String getPhienDauGiaId() {
        return phienDauGiaId;
    }

    public long getGiaKinTamThoi() {
        return giaKinTamThoi;
    }

    public long getGiaMoiThapNhat() {
        return giaMoiThapNhat;
    }

    public String getBidderId() {
        return bidderId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public int getSoBidDaRa() {
        return soBidDaRa;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setPhienDauGiaId(String phienDauGiaId) {
        this.phienDauGiaId = phienDauGiaId;
    }

    public void setGiaKinTamThoi(long giaKinTamThoi) {
        this.giaKinTamThoi = giaKinTamThoi;
    }

    public void setGiaMoiThapNhat(long giaMoiThapNhat) {
        this.giaMoiThapNhat = giaMoiThapNhat;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public void setSoBidDaRa(int soBidDaRa) {
        this.soBidDaRa = soBidDaRa;
    }
}
