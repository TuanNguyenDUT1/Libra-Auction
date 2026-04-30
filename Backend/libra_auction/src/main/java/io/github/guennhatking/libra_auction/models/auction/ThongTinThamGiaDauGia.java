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
public class ThongTinThamGiaDauGia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private NguoiDung nguoiThamGia;

    @ManyToOne
    private PhienDauGia phienDauGia;

    private OffsetDateTime thoiGianDangKy;

    // CONSTRUCTOR
    public ThongTinThamGiaDauGia() {
    }

    public ThongTinThamGiaDauGia(NguoiDung nguoiThamGia, PhienDauGia phienDauGia) {
        this.nguoiThamGia = nguoiThamGia;
        this.phienDauGia = phienDauGia;
        this.thoiGianDangKy = OffsetDateTime.now(ZoneOffset.ofHours(7));
    }

    // GETTER
    public String getId() {
        return id;
    }

    public NguoiDung getNguoiThamGia() {
        return nguoiThamGia;
    }

    public PhienDauGia getPhienDauGia() {
        return phienDauGia;
    }

    public OffsetDateTime getThoiGianDangKy() {
        return thoiGianDangKy;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setNguoiThamGia(NguoiDung nguoiThamGia) {
        this.nguoiThamGia = nguoiThamGia;
    }

    public void setPhienDauGia(PhienDauGia phienDauGia) {
        this.phienDauGia = phienDauGia;
    }

    public void setThoiGianDangKy(OffsetDateTime thoiGianDangKy) {
        this.thoiGianDangKy = thoiGianDangKy;
    }
}
