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
public class BanGhiPhienDauGia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phien_dau_gia_id", referencedColumnName = "id")
    private PhienDauGia phienDauGia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dat_gia_id", referencedColumnName = "id")
    private NguoiDung nguoiDatGia;
    
    private LocalDateTime thoiGian;
    private long mucGia;

    protected BanGhiPhienDauGia() {
        // Constructor mặc định cho JPA
}

    public String getId() { return id; }
    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public long getMucGia() { return mucGia; }
    public NguoiDung getNguoiDatGia() { return nguoiDatGia; }
    public LocalDateTime getThoiGian() { return thoiGian; }

    //id
    public void setId(String id) { this.id = id;}
    //phienDauGia
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    //mucGia
    public void setMucGia(long mucGia) { this.mucGia = mucGia; }
    //nguoiDatGia   
    public void setNguoiDatGia(NguoiDung nguoiDatGia) { this.nguoiDatGia = nguoiDatGia; }
    //thoiGian
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }
}
