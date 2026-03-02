package io.github.guennhatking.libra_auction.models;

public class HinhAnhTaiSan {
    private TaiSan taiSan;
    private int thuTuHienThi;
    private String hinhAnh; // url

    public TaiSan getTaiSan() { return null; }
    public int getThuTuHienThi() { return 0; }
    public String getHinhAnh() { return null; }

    //setter
    public void setTaiSan(TaiSan taiSan) { this.taiSan = taiSan; }
    public void setThuTuHienThi(int thuTuHienThi) { this.thuTuHienThi = thuTuHienThi; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}
