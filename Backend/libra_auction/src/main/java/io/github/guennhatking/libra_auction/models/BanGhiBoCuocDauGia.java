package io.github.guennhatking.libra_auction.models;
import java.time.LocalDateTime;

public class BanGhiBoCuocDauGia {
    private NguoiDung nguoiBoCuoc;
    private PhienDauGia phienDauGia;
    private LocalDateTime thoiGian;
    private String lyDo;

    public NguoiDung getNguoiBoCuoc() { return nguoiBoCuoc; }
    public PhienDauGia getPhienDauGia() { return phienDauGia; }
    public LocalDateTime getThoiGian() { return thoiGian; }
    public String getLyDo() { return lyDo; }

    //setter
    public void setNguoiBoCuoc(NguoiDung nguoiBoCuoc) { this.nguoiBoCuoc = nguoiBoCuoc; }
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    //tạo chức năng NguoiDung bo cuoc dau gia
    public void boCuocDauGia(NguoiDung nguoiBoCuoc, PhienDauGia phienDauGia, String lyDo) {
        this.nguoiBoCuoc = nguoiBoCuoc;
        this.phienDauGia = phienDauGia;
        this.thoiGian = LocalDateTime.now();
        this.lyDo = lyDo;
    }

    // tao ban ghi bo cuoc dau gia
    public BanGhiBoCuocDauGia(NguoiDung nguoiBoCuoc, PhienDauGia phienDauGia, String lyDo) {
        this.nguoiBoCuoc = nguoiBoCuoc;
        this.phienDauGia = phienDauGia;
        this.thoiGian = LocalDateTime.now();
        this.lyDo = lyDo;
    }
}

