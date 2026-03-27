import type { CategoryCardType } from "@/types/category_card_type";
export function FetchCategories(): CategoryCardType[] {
  // TODO: Fetch data from api
  return [
    {
      id: "001",
      image_src: "/category-1.jpg",
      title: "Firearms Auction",
      href: "/auctions/001",
    },
    {
      id: "002",
      image_src: "/category-2.jpg",
      title: "Vehicle Auction",
      href: "/auctions/002",
    },
    {
      id: "003",
      image_src: "/category-3.jpg",
      title: "Collectibles Auction",
      href: "/auctions/003",
    },
    {
      id: "004",
      image_src: "/category-4.jpg",
      title: "Real Estate Auction",
      href: "/auctions/004",
    },
    {
      id: "005",
      image_src: "/category-5.jpg",
      title: "Fine Art Auction",
      href: "/auctions/005",
    },
  ];
}
