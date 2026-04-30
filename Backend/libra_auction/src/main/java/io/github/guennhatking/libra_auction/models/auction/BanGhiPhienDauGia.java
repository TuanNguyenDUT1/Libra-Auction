package io.github.guennhatking.libra_auction.models.auction;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BanGhiPhienDauGia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private PhienDauGia phienDauGia;

    @ManyToOne
    private NguoiDung nguoiDatGia;

    private OffsetDateTime thoiGian;
    private long mucGia;

    // CONSTRUCTOR
    public BanGhiPhienDauGia() {
    }

    public BanGhiPhienDauGia(PhienDauGia phienDauGia, NguoiDung nguoiDatGia, long mucGia) {
        this.phienDauGia = phienDauGia;
        this.nguoiDatGia = nguoiDatGia;
        this.mucGia = mucGia;
        this.thoiGian = OffsetDateTime.now(ZoneOffset.ofHours(7));
    }

    // GETTER
    public String getId() {
        return id;
    }

    public PhienDauGia getPhienDauGia() {
        return phienDauGia;
    }

    public NguoiDung getNguoiDatGia() {
        return nguoiDatGia;
    }

    public OffsetDateTime getThoiGian() {
        return thoiGian;
    }

    public long getMucGia() {
        return mucGia;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setPhienDauGia(PhienDauGia phienDauGia) {
        this.phienDauGia = phienDauGia;
    }

    public void setNguoiDatGia(NguoiDung nguoiDatGia) {
        this.nguoiDatGia = nguoiDatGia;
    }

    public void setThoiGian(OffsetDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public void setMucGia(long mucGia) {
        this.mucGia = mucGia;
    }
}