import type { LiveAuctionCardType } from "@/types/live_auction_card_type";

export async function FetchLiveAuctions(): Promise<LiveAuctionCardType[]> {
  try {
    const res = await fetch(process.env.BACKEND_SERVER_URL! + "/api/auction-sessions/");

    if (!res.ok) {
      return [];
    }

    const data = (await res.json()) as LiveAuctionCardType[];

    for (const auction of data) {
      auction.href = "/auctions/" + auction.category_id + "/" + auction.id;
    }

    return data;

  } catch (error) {
    console.log("FetchLiveAuctions error:", error);
    return [];
  }
}