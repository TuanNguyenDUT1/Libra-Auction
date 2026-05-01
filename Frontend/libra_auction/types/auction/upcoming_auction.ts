export type UpcomingAuction = {
    id: string,
    image_src: string,
    title: string,
    starting_bid: number,
    bidders: number,
    starting_date: Date, 
    category_id: string,
}