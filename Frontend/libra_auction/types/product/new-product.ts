import { Attribute } from "./attribute";

export interface NewProduct {
  tenTaiSan: string;
  soLuong: number;
  moTa: string;
  danhMucId: string;
  imageUrls: string[];
  attributes: Attribute[];
}