package io.github.guennhatking.libra_auction.models;

public class ThongTinPhienDauGia {
    private PhienDauGia phienDauGia;
    private long tienCoc;
    private long giaKhoiDiem;
    private long khoangGia;
    private String tieuDe;
    private TaiSan taiSan;
    public PhienDauGia getPhienDauGia() { return phienDauGia; }

    public long getTienCoc() { return tienCoc; }
    public long getGiaKhoiDiem() { return giaKhoiDiem; }
    public long getKhoangGia() { return khoangGia; }
    public String getTieuDe() { return tieuDe; }
    public TaiSan getTaiSan() { return taiSan; }

    //phienDauGia
    public void setPhienDauGia(PhienDauGia phienDauGia) { this.phienDauGia = phienDauGia; }
    //tienCoc
    public void setTienCoc(long tienCoc) { this.tienCoc = tienCoc; }
    //giaKhoiDiem
    public void setGiaKhoiDiem(long giaKhoiDiem) { this.giaKhoiDiem = giaKhoiDiem; }
    //khoangGia
    public void setKhoangGia(long khoangGia) { this.khoangGia = khoangGia; }
    //tieuDe
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }
    //taiSan
    public void setTaiSan(TaiSan taiSan) { this.taiSan = taiSan; }
    

}
