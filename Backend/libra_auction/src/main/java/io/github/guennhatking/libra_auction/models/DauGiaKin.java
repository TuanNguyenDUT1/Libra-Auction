package io.github.guennhatking.libra_auction.models;

import java.util.ArrayList;
import java.util.List;

/**
 * DAU_GIA_KIN (Sealed Bid Auction)
 * 
 * Rules:
 * - All bids are kept confidential during the auction period
 * - Bidders do not know the amount of competing bids
 * - All bids are opened simultaneously after the auction closes
 * - Winner is determined by highest bid (or second-highest price in some variants)
 * - Each bidder can submit only one sealed bid (or multiple bids with modifications)
 */
public class DauGiaKin {
    private String id;
    private String phienDauGiaId;
    
    // List of sealed bids (encrypted/hidden details)
    private List<String> danhSachBidKin;
    
    // Minimum reserve price
    private long giaKhoiDiem;
    
    // Highest sealed bid information (only revealed at end)
    private String highestBidderId;
    private String highestBidderName;
    private long highestBidAmount;
    
    // Second highest bid (for payment if using second-price variant)
    private long secondHighestBidAmount;
    
    // Auction state
    private boolean daBiMoKhoa;
    private int totalBidsReceived;

    // CONSTRUCTOR
    public DauGiaKin() {
        this.danhSachBidKin = new ArrayList<>();
        this.daBiMoKhoa = false;
        this.totalBidsReceived = 0;
    }

    public DauGiaKin(long giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
        this.danhSachBidKin = new ArrayList<>();
        this.daBiMoKhoa = false;
        this.totalBidsReceived = 0;
    }

    // BUSINESS LOGIC METHODS
    
    /**
     * Record a sealed bid (usually stored encrypted)
     * @param sealedBidHash Hash or encrypted bid information
     * @return true if bid accepted, false if invalid
     */
    public boolean recordSealedBid(String sealedBidHash) {
        if (!daBiMoKhoa && sealedBidHash != null && !sealedBidHash.isEmpty()) {
            danhSachBidKin.add(sealedBidHash);
            totalBidsReceived++;
            return true;
        }
        return false;
    }
    
    /**
     * Validate if bid meets minimum reserve
     * @param bidAmount The bid amount
     * @return true if valid
     */
    public boolean isValidBidAmount(long bidAmount) {
        return bidAmount >= giaKhoiDiem;
    }
    
    /**
     * Open bids and determine winner (called when auction ends)
     * @param bidderId ID of highest bidder
     * @param bidderName Name of highest bidder
     * @param highestAmount Highest bid amount
     * @param secondHighestAmount Second highest bid amount
     */
    public void openBidsAndDetermineWinner(String bidderId, String bidderName, 
                                           long highestAmount, long secondHighestAmount) {
        this.highestBidderId = bidderId;
        this.highestBidderName = bidderName;
        this.highestBidAmount = highestAmount;
        this.secondHighestBidAmount = secondHighestAmount;
        this.daBiMoKhoa = true;
    }
    
    /**
     * Get the final price (could be highest bid or second-price)
     * @param useSecondPriceRule If true, winner pays second highest bid
     * @return Final price
     */
    public long getFinalPrice(boolean useSecondPriceRule) {
        if (useSecondPriceRule) {
            return secondHighestBidAmount > 0 ? secondHighestBidAmount : highestBidAmount;
        }
        return highestBidAmount;
    }
    
    /**
     * Get the winner information string
     * @return Winner details
     */
    public String getWinnerInfo() {
        if (highestBidderId == null) {
            return "Bids not yet opened";
        }
        return String.format("Winner: %s (ID: %s), Bid: %d",
            highestBidderName, highestBidderId, highestBidAmount);
    }

    // GETTER
    public String getId() {
        return id;
    }

    public String getPhienDauGiaId() {
        return phienDauGiaId;
    }

    public List<String> getDanhSachBidKin() {
        return danhSachBidKin;
    }

    public long getGiaKhoiDiem() {
        return giaKhoiDiem;
    }

    public String getHighestBidderId() {
        return highestBidderId;
    }

    public String getHighestBidderName() {
        return highestBidderName;
    }

    public long getHighestBidAmount() {
        return highestBidAmount;
    }

    public long getSecondHighestBidAmount() {
        return secondHighestBidAmount;
    }

    public boolean isDaBiMoKhoa() {
        return daBiMoKhoa;
    }

    public int getTotalBidsReceived() {
        return totalBidsReceived;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setPhienDauGiaId(String phienDauGiaId) {
        this.phienDauGiaId = phienDauGiaId;
    }

    public void setDanhSachBidKin(List<String> danhSachBidKin) {
        this.danhSachBidKin = danhSachBidKin;
    }

    public void setGiaKhoiDiem(long giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
    }

    public void setHighestBidderId(String highestBidderId) {
        this.highestBidderId = highestBidderId;
    }

    public void setHighestBidderName(String highestBidderName) {
        this.highestBidderName = highestBidderName;
    }

    public void setHighestBidAmount(long highestBidAmount) {
        this.highestBidAmount = highestBidAmount;
    }

    public void setSecondHighestBidAmount(long secondHighestBidAmount) {
        this.secondHighestBidAmount = secondHighestBidAmount;
    }

    public void setDaBiMoKhoa(boolean daBiMoKhoa) {
        this.daBiMoKhoa = daBiMoKhoa;
    }

    public void setTotalBidsReceived(int totalBidsReceived) {
        this.totalBidsReceived = totalBidsReceived;
    }
}
