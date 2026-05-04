import { Attribute } from "../product/attribute";
import { LoaiDauGia, TrangThaiPhien } from "../status";

export interface Auction {
        category_id: string,
        category_name: string,
        auction_id: string,
        auction_name: string,
        auction_status: TrangThaiPhien,
        auction_type: LoaiDauGia,
        start_time: Date,
        duration: number,

        starting_price: number,
        current_price: number,
        min_bid_increment: number,

        product_id: string,
        product_name: string,
        quantity: number,
        description: string,

        images: string[],
        attributes: Attribute[],

        total_bids: number,
        total_participants: number
}