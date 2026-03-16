package io.github.guennhatking.libra_auction.models;

import io.github.guennhatking.libra_auction.enums.Enums;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TaiKhoan {
    @Id
    protected String id;

    protected String username;

    @Enumerated(EnumType.STRING)
    protected Enums.TrangThaiTaiKhoan trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id")
    protected NguoiDung nguoiDung;

    protected TaiKhoan() {
        // Constructor mặc định cho JPA
    }

    public TaiKhoan(String id, Enums.TrangThaiTaiKhoan trangThai, String username) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID không được để trống.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username không được để trống.");
        }
        this.id = id;
        this.username = username;
        if (trangThai == null) {
            this.trangThai = Enums.TrangThaiTaiKhoan.CHO_XAC_NHAN;
        }
        else {
            this.trangThai = trangThai;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Enums.TrangThaiTaiKhoan getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Enums.TrangThaiTaiKhoan trangThai) {
        this.trangThai = trangThai;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}