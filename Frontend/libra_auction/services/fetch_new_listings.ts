import type { NewListingsCardType } from "@/types/new_listings_card_type";
export function FetchNewListings(): NewListingsCardType[] {
  // TODO: Fetch data from api
  return [
    {
      id: "001",
      image_src: "/live-auction-1.jpg",
      title: "2015 TOYOTA 4RUNNER SR5/SR5 PREMIUM",
      starting_bid: 1182082000,
      biders: 18,
      starting_date: new Date(2026, 12, 12),
      href: "auctions/002/001",
    },
    {
      id: "002",
      image_src: "/live-auction-2.jpg",
      title: "H14-7. Selflessness, from The Secrets, 2024",
      starting_bid: 141050000,
      biders: 27,
      starting_date: new Date(2026, 12, 12),
      href: "auctions/003/002",
    },
    {
      id: "003",
      image_src: "/live-auction-3.jpg",
      title: "Star Rail Hotaru Spring Gift Ver. 1/8 Scale Complete Figure",
      starting_bid: 1210000,
      biders: 66,
      starting_date: new Date(2026, 12, 12),
      href: "auctions/003/003",
    },
  ];
}
