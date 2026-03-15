package io.github.guennhatking.libra_auction.models;
import io.github.guennhatking.libra_auction.enums.Enums;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
@Entity
public class YeuCauTaoPhien {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // string(10)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phien_dau_gia_id")
    private PhienDauGia phienDauGia;

    @Enumerated(EnumType.STRING)
    private Enums.TrangThaiYeuCau trangThai; // "chưa kiểm duyệt" | "đã chấp nhận" | "đã từ chối"

    private String liDoTuChoi;

    public YeuCauTaoPhien() {
        // Constructor mặc định
    }

    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public Enums.TrangThaiYeuCau getTrangThai() { return trangThai; }
    public void chapNhanYeuCau() {}
    public void tuChoiYeuCau(String lyDo) {}

    //setter
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    public void setTrangThai(Enums.TrangThaiYeuCau trangThai) { this.trangThai = trangThai;}
    public void setLiDoTuChoi(String liDoTuChoi) { this.liDoTuChoi = liDoTuChoi; }
}
