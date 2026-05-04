import { LoaiDauGia } from "../status";

export interface NewAuction {
  taiSanId: string;
  thoiGianBatDau: string | Date;
  thoiLuong: number;
  tienCoc: number;
  giaKhoiDiem: number;
  buocGiaNhoNhat: number;
  loaiDauGia: LoaiDauGia;
}