package io.github.guennhatking.libra_auction.models.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.OffsetDateTime;

import io.github.guennhatking.libra_auction.enums.transaction.LoaiGiaoDich;
import io.github.guennhatking.libra_auction.models.auction.ThongTinThamGiaDauGia;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;

@Entity
public class GiaoDichDatCoc extends GiaoDich {
    @ManyToOne
    private NguoiDung nguoiDatCoc;

    @ManyToOne
    private ThongTinThamGiaDauGia thongTinThamGia;

    private OffsetDateTime thoiGianTraCoc;

    // CONSTRUCTOR
    protected GiaoDichDatCoc() {
    }

    public GiaoDichDatCoc(long soTien, NguoiDung nguoiDatCoc, ThongTinThamGiaDauGia thongTinThamGia) {
        super(LoaiGiaoDich.DAT_COC, soTien);
        this.nguoiDatCoc = nguoiDatCoc;
        this.thongTinThamGia = thongTinThamGia;
        this.thoiGianTraCoc = null;
    }

    // GETTER
    public NguoiDung getNguoiDatCoc() {
        return nguoiDatCoc;
    }

    public ThongTinThamGiaDauGia getThongTinThamGia() {
        return thongTinThamGia;
    }

    public OffsetDateTime getThoiGianTraCoc() {
        return thoiGianTraCoc;
    }

    // SETTER
    public void setNguoiDatCoc(NguoiDung nguoiDatCoc) {
        this.nguoiDatCoc = nguoiDatCoc;
    }

    public void setThongTinThamGia(ThongTinThamGiaDauGia thongTinThamGia) {
        this.thongTinThamGia = thongTinThamGia;
    }

    public void setThoiGianTraCoc(OffsetDateTime thoiGianTraCoc) {
        this.thoiGianTraCoc = thoiGianTraCoc;
    }
}