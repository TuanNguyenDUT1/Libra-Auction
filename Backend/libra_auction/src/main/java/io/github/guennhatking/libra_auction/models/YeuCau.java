package io.github.guennhatking.libra_auction.models;

import io.github.guennhatking.libra_auction.enums.Enums;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class YeuCau {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id")
    protected NguoiDung nguoiDung;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thong_bao_id")
    protected ThongBao thongBao;

    protected String token;
    
    @Enumerated(EnumType.STRING)
    protected Enums.LoaiYeuCau loaiYeuCau;

    @Enumerated(EnumType.STRING)
    protected Enums.TrangThaiYeuCau trangThaiYeuCau;

    protected LocalDateTime thoiGianHetHanKichHoat;
    protected LocalDateTime thoiGianHetHanSuDung;

    protected YeuCau() {
        // Constructor mặc định cho JPA
    }

    public YeuCau(NguoiDung nguoiDung, Enums.LoaiYeuCau loaiYeuCau) {
        this.nguoiDung = nguoiDung;
        this.loaiYeuCau = loaiYeuCau;
        this.trangThaiYeuCau = Enums.TrangThaiYeuCau.KHOI_TAO;
    }

    public abstract void kichHoat();

    public abstract void suDung();
}
