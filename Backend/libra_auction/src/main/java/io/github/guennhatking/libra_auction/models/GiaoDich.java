package io.github.guennhatking.libra_auction.models;

import io.github.guennhatking.libra_auction.enums.Enums;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class GiaoDich {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)
    @Enumerated(EnumType.STRING)
    private Enums.LoaiGiaoDich LoaiGiaoDich; // "thanh toán" | "đặt cọc"

    private long soTien;
    private java.time.LocalDateTime ngayTao;

    protected GiaoDich() {
        // Constructor mặc định cho JPA
    }
    
    //getter setter    
    public String getId() { return id; }
    public Enums.LoaiGiaoDich getLoaiGiaoDich() { return LoaiGiaoDich; }
    public long getSoTien() { return soTien; }
    public java.time.LocalDateTime getNgayTao() { return ngayTao; }

    public void setId(String id) { this.id = id; }
    public void setLoaiGiaoDich(Enums.LoaiGiaoDich loaiGiaoDich) { this.LoaiGiaoDich = loaiGiaoDich; }
    public void setSoTien(long soTien) { this.soTien = soTien; }
    public void setNgayTao(java.time.LocalDateTime ngayTao) { this.ngayTao = ngayTao; }

    //tạo chức năng giao dịch ở đây
    public void taoGiaoDich(String id, Enums.LoaiGiaoDich loaiGiaoDich, long soTien) {
        this.id = id;
        this.LoaiGiaoDich = loaiGiaoDich;
        this.soTien = soTien;
        this.ngayTao = java.time.LocalDateTime.now();
    }


}