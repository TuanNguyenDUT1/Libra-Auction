package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GiaoDichThanhToan extends GiaoDich {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_gui_id")
    private NguoiDung nguoiGui;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_nhan_id")
    private NguoiDung nguoiNhan;

    protected GiaoDichThanhToan() {
        // Constructor mặc định cho JPA
    }

    // Getters and setters
    public NguoiDung getNguoiGui() { return nguoiGui; }
    public NguoiDung getNguoiNhan() { return nguoiNhan; }
    public void setNguoiGui(NguoiDung nguoiGui) { this.nguoiGui = nguoiGui; }
    public void setNguoiNhan(NguoiDung nguoiNhan) { this.nguoiNhan = nguoiNhan; }
    
    // Other methods related to payment transactions can be added here
    //chức năng thanh toán sau khi đấu giá thành công
    public void taoGiaoDichThanhToan(String id, long soTien, NguoiDung nguoiGui, NguoiDung nguoiNhan) {
        this.setId(id);
        this.setLoaiGiaoDich(io.github.guennhatking.libra_auction.enums.Enums.LoaiGiaoDich.THANH_TOAN);
        this.setSoTien(soTien);
        this.setNgayTao(java.time.LocalDateTime.now());
        this.nguoiGui = nguoiGui;
        this.nguoiNhan = nguoiNhan;
    }
}