package io.github.guennhatking.libra_auction.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;


@Entity
public class KetHopThuocTinh {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tai_san_id")
    private TaiSan taiSan;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuoc_tinh_chuan_hoa_id")
    private ThuocTinhChuanHoa thuocTinhChuanHoa;

    protected KetHopThuocTinh() {
        // Constructor mặc định cho JPA
    }

    public TaiSan getTaiSan() { return null; }
    public ThuocTinhChuanHoa getThuocTinhChuanHoa() { return null; }

    // Setters
    public void setTaiSan(TaiSan taiSan) { this.taiSan = taiSan; }
    public void setThuocTinhChuanHoa(ThuocTinhChuanHoa thuocTinhChuanHoa) { this.thuocTinhChuanHoa = thuocTinhChuanHoa; }
}
