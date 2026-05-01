import { AuctionCardType } from "@/types/auction_card_type";
import Image from "next/image";

export default function AuctionCard({ auctionCard }: { auctionCard: AuctionCardType }) {
    return (
        <div className="relative h-[360px] bg-white shadow rounded-xl overflow-hidden">
            <Image
                src={auctionCard.thumbnail}
                alt=""
                fill
                className="object-cover"
            />
        </div>
    );
}