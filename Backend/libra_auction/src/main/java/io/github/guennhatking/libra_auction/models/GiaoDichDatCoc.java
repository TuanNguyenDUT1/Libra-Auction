package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GiaoDichDatCoc extends GiaoDich {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dat_coc_id")
    private NguoiDung nguoiDatCoc;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thong_tin_tham_gia_id")
    private ThongTinThamGiaDauGia thongTinThamGia;

    private java.time.LocalDateTime thoiGianTraCoc;

    protected GiaoDichDatCoc() {
        // Constructor mặc định cho JPA
    }

    // Getters and setters can be added here
    public NguoiDung getNguoiDatCoc() { return nguoiDatCoc; }
    public ThongTinThamGiaDauGia getThongTinThamGia() { return thongTinThamGia; }
    public java.time.LocalDateTime getThoiGianTraCoc() { return thoiGianTraCoc; }
    public void setNguoiDatCoc(NguoiDung nguoiDatCoc) { this.nguoiDatCoc = nguoiDatCoc; }
    public void setThongTinThamGia(ThongTinThamGiaDauGia thongTinThamGia) { this.thongTinThamGia = thongTinThamGia; }
    public void setThoiGianTraCoc(java.time.LocalDateTime thoiGianTraCoc) { this.thoiGianTraCoc = thoiGianTraCoc; }
    
    //tạo chức năng đặt cọc ở đây
    public void taoGiaoDichDatCoc(String id, long soTien, NguoiDung nguoiDatCoc, ThongTinThamGiaDauGia thongTinThamGia) {
        this.setId(id);
        this.setSoTien(soTien);
        this.setNgayTao(java.time.LocalDateTime.now());
        this.nguoiDatCoc = nguoiDatCoc;
        this.thongTinThamGia = thongTinThamGia;
        this.thoiGianTraCoc = null; // Chưa trả cọc
    }
}