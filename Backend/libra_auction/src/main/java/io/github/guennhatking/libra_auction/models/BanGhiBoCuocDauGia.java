package io.github.guennhatking.libra_auction.models;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
public class BanGhiBoCuocDauGia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_bo_cuoc_id", referencedColumnName = "id")
    private NguoiDung nguoiBoCuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phien_dau_gia_id", referencedColumnName = "id")
    private PhienDauGia phienDauGia;

    private LocalDateTime thoiGian;
    private String lyDo;

    protected BanGhiBoCuocDauGia() {
        // Constructor mặc định cho JPA
    }
    
    public NguoiDung getNguoiBoCuoc() { return nguoiBoCuoc; }
    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public LocalDateTime getThoiGian() { return thoiGian; }
    public String getLyDo() { return lyDo; }

    //setter
    public void setNguoiBoCuoc(NguoiDung nguoiBoCuoc) { this.nguoiBoCuoc = nguoiBoCuoc; }
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    //tạo chức năng NguoiDung bo cuoc dau gia
    public void boCuocDauGia(NguoiDung nguoiBoCuoc, PhienDauGia phienDauGia, String lyDo) {
        this.nguoiBoCuoc = nguoiBoCuoc;
        this.phienDauGia = phienDauGia;
        this.thoiGian = LocalDateTime.now();
        this.lyDo = lyDo;
    }

    // tao ban ghi bo cuoc dau gia
    public BanGhiBoCuocDauGia(NguoiDung nguoiBoCuoc, PhienDauGia phienDauGia, String lyDo) {
        this.nguoiBoCuoc = nguoiBoCuoc;
        this.phienDauGia = phienDauGia;
        this.thoiGian = LocalDateTime.now();
        this.lyDo = lyDo;
    }
}

