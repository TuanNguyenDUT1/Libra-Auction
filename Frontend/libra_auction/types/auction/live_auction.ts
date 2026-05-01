export type LiveAuction = {
    id: string,
    category_id: string,
    thumbnail: string,
    title: string,
    current_bid: number,
    bids: number,
    time_left: number, // miliseconds left
}