package io.github.guennhatking.libra_auction.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
@Entity
public class ThongTinThamGiaDauGia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_tham_gia_id")
    private NguoiDung nguoiThamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phien_dau_gia_id")
    private PhienDauGia phienDauGia;

    private LocalDateTime thoiGianDangKy;
    
    public ThongTinThamGiaDauGia() {
        // Constructor mặc định
    }
    //getter setter
    public NguoiDung getNguoiThamGia() { return nguoiThamGia; }
    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public LocalDateTime getThoiGianDangKy() { return thoiGianDangKy; }
    
    public void setNguoiThamGia(NguoiDung nguoiThamGia) { this.nguoiThamGia = nguoiThamGia; }
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    public void setThoiGianDangKy(LocalDateTime thoiGianDangKy) { this.thoiGianDangKy = thoiGianDangKy; }
    
}
