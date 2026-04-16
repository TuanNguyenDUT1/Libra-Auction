import type { NewListingsCardType } from "@/types/new_listings_card_type";

export async function FetchNewListings(): Promise<NewListingsCardType[]> {
  try {
    const res = await fetch(
      process.env.BACKEND_SERVER_URL! + "/api/auction-sessions/new-listings"
    );

    if (!res.ok) {
      return [];
    }

    const data = (await res.json()) as NewListingsCardType[];

    for (const item of data) {
      item.starting_date = new Date(item.starting_date);
      item.href = "/auctions/" + item.category_id + "/" + item.id;
    }

    return data;

  } catch (error) {
    console.log("FetchNewListings error:", error);
    return [];
  }
}