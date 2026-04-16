export type AuctionInfoType = {
    category_id: string,
    category_name: string,
    auction_id: string,
    auction_name: string,
    auction_status: "UPCOMING" | "LIVE" | "ENDED"
    auction_type: string,
    start_time: string,
    duration: number,

    starting_price: number,
    current_price: number,
    min_bid_increment: number,

    product_id: string,
    product_name: string,
    quantity: number,
    description: string,

    images: string[],

    attributes: {
        name: string
        value: string
    }[],

    total_bids?: number,
    total_participants?: number,
}