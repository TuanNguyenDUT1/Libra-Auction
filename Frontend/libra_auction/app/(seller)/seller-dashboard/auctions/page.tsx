import { AuctionList } from "@/components/seller/auction/auction_list";
import { fetchAuctions } from "@/services/fetch_auctions";

export default async function page() {
    const auctions = await fetchAuctions();
    return (
        <AuctionList auctions={auctions} />
    );
}