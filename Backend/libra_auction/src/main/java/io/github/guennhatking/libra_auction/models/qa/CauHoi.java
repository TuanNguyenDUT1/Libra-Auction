package io.github.guennhatking.libra_auction.models.qa;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import io.github.guennhatking.libra_auction.enums.qa.TinhTrangCauHoi;
import io.github.guennhatking.libra_auction.models.auction.PhienDauGia;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CauHoi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private PhienDauGia phienDauGia;

    @ManyToOne
    private NguoiDung nguoiHoi;

    @ManyToOne
    private NguoiDung nguoiTraLoi;

    private String noiDungHoi;
    private String noiDungTraLoi;
    private OffsetDateTime thoiGianHoi;
    private OffsetDateTime thoiGianTraLoi;

    @Enumerated(EnumType.STRING)
    private TinhTrangCauHoi tinhTrangCauHoi;

    // CONSTRUCTOR
    public CauHoi() {
    }

    public CauHoi(PhienDauGia phienDauGia, NguoiDung nguoiHoi, String noiDungHoi) {
        this.phienDauGia = phienDauGia;
        this.nguoiHoi = nguoiHoi;
        this.noiDungHoi = noiDungHoi;
        this.thoiGianHoi = OffsetDateTime.now(ZoneOffset.ofHours(7));
    }

    // GETTER
    public String getId() {
        return id;
    }

    public PhienDauGia getPhienDauGia() {
        return phienDauGia;
    }

    public NguoiDung getNguoiHoi() {
        return nguoiHoi;
    }

    public NguoiDung getNguoiTraLoi() {
        return nguoiTraLoi;
    }

    public String getNoiDungHoi() {
        return noiDungHoi;
    }

    public String getNoiDungTraLoi() {
        return noiDungTraLoi;
    }

    public OffsetDateTime getThoiGianHoi() {
        return thoiGianHoi;
    }

    public OffsetDateTime getThoiGianTraLoi() {
        return thoiGianTraLoi;
    }

    public TinhTrangCauHoi getTinhTrangCauHoi() {
        return tinhTrangCauHoi;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setPhienDauGia(PhienDauGia phienDauGia) {
        this.phienDauGia = phienDauGia;
    }

    public void setNguoiHoi(NguoiDung nguoiHoi) {
        this.nguoiHoi = nguoiHoi;
    }

    public void setNguoiTraLoi(NguoiDung nguoiTraLoi) {
        this.nguoiTraLoi = nguoiTraLoi;
    }

    public void setNoiDungHoi(String noiDungHoi) {
        this.noiDungHoi = noiDungHoi;
    }

    public void setNoiDungTraLoi(String noiDungTraLoi) {
        this.noiDungTraLoi = noiDungTraLoi;
    }

    public void setThoiGianHoi(OffsetDateTime thoiGianHoi) {
        this.thoiGianHoi = thoiGianHoi;
    }

    public void setThoiGianTraLoi(OffsetDateTime thoiGianTraLoi) {
        this.thoiGianTraLoi = thoiGianTraLoi;
    }

    public void setTinhTrangCauHoi(TinhTrangCauHoi tinhTrangCauHoi) {
        this.tinhTrangCauHoi = tinhTrangCauHoi;
    }
}