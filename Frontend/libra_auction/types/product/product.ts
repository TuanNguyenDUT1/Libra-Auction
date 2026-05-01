import { Attribute } from "./attribute";

export interface Product {
  product_id: string;
  product_name: string;
  category_id: string;
  category_name: string;
  quantity: number;
  description: string;
  images: string[];
  attributes: Attribute[];
}