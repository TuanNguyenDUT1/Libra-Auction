import { Attribute } from "../product/attribute";

export interface Auction {
        category_id: string,
        category_name: string,
        auction_id: string,
        auction_name: string,
        auction_status: string,
        auction_type: string,
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