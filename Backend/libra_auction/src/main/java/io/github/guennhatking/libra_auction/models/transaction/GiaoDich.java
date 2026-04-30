package io.github.guennhatking.libra_auction.models.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import io.github.guennhatking.libra_auction.enums.transaction.LoaiGiaoDich;
import io.github.guennhatking.libra_auction.enums.transaction.TinhTrangGiaoDich;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class GiaoDich {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private LoaiGiaoDich loaiGiaoDich;

    private long soTien;
    private OffsetDateTime ngayTao;

    @Enumerated(EnumType.STRING)
    private TinhTrangGiaoDich tinhTrangGiaoDich = TinhTrangGiaoDich.DANG_XU_LY;

    private String maGiaoDichCuaDoiTac; // Lưu lại mã giao dịch của VNPay

    // CONSTRUCTOR
    protected GiaoDich() {
    }

    public GiaoDich(LoaiGiaoDich loaiGiaoDich, long soTien) {
        this.loaiGiaoDich = loaiGiaoDich;
        this.soTien = soTien;
        this.ngayTao = OffsetDateTime.now(ZoneOffset.ofHours(7));
    }

    // GETTER
    public String getId() {
        return id;
    }

    public LoaiGiaoDich getLoaiGiaoDich() {
        return loaiGiaoDich;
    }

    public long getSoTien() {
        return soTien;
    }

    public OffsetDateTime getNgayTao() {
        return ngayTao;
    }

    public TinhTrangGiaoDich geTinhTrangGiaoDich() {
        return tinhTrangGiaoDich;
    }

    public String getMaGiaoDichCuaDoiTac() {
        return maGiaoDichCuaDoiTac;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setLoaiGiaoDich(LoaiGiaoDich loaiGiaoDich) {
        this.loaiGiaoDich = loaiGiaoDich;
    }

    public void setSoTien(long soTien) {
        this.soTien = soTien;
    }

    public void setNgayTao(OffsetDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setTinhTrangGiaoDich(TinhTrangGiaoDich tinhTrangGiaoDich) {
        this.tinhTrangGiaoDich = tinhTrangGiaoDich;
    }

    public void setMaGiaoDichCuaDoiTac(String maGiaoDichCuaDoiTac) {
        this.maGiaoDichCuaDoiTac = maGiaoDichCuaDoiTac;
    }
}