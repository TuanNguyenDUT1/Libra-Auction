package io.github.guennhatking.libra_auction.models.account;

import io.github.guennhatking.libra_auction.enums.account.TrangThaiTaiKhoan;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TaiKhoan {
    @Id
    protected String id;

    @Enumerated(EnumType.STRING)
    protected TrangThaiTaiKhoan trangThai;

    @ManyToOne
    protected NguoiDung nguoiDung;

    // CONSTRUCTOR
    protected TaiKhoan() {
    }

    public TaiKhoan(String id, TrangThaiTaiKhoan trangThai) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID không được để trống.");
        }
        this.id = id;
        this.trangThai = trangThai != null ? trangThai : TrangThaiTaiKhoan.CHO_XAC_NHAN;
    }

    // GETTER
    public String getId() {
        return id;
    }

    public TrangThaiTaiKhoan getTrangThai() {
        return trangThai;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    // SETTER
    public void setId(String id) {
        this.id = id;
    }

    public void setTrangThai(TrangThaiTaiKhoan trangThai) {
        this.trangThai = trangThai;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}