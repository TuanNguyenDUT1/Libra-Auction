"use client";

import { useState } from "react";
import { Auction } from "@/types/auction/auction";
import { AuctionSearchBar } from "./auction_search_bar";
import { AuctionItem } from "./auction_item";
import { useRouter } from "next/navigation";

interface AuctionListProps {
  auctions: Auction[];
}

export const AuctionList = ({ auctions }: AuctionListProps) => {
  const [searchTerm, setSearchTerm] = useState("");

  const filteredAuctions = auctions.filter((a) =>
    a.auction_name.toLowerCase().includes(searchTerm.toLowerCase())
  );
  const router = useRouter();

  return (
    <div className="w-full">
      <AuctionSearchBar 
        onSearch={setSearchTerm} 
        onAddClick={() => router.push("/seller-dashboard/auctions/new-auction")} 
      />

      <div className="flex flex-col gap-3">
        {filteredAuctions.length > 0 ? (
          filteredAuctions.map((auction) => (
            <AuctionItem
              key={auction.auction_id}
              auction={auction}
              onView={(id) => router.push(`/seller-dashboard/auctions/${id}`)}
              onEdit={(id) => router.push(`/seller-dashboard/auctions/${id}/edit`)}
              onDelete={(id) => router.push(`/seller-dashboard/auctions/${id}/delete`)}
            />
          ))
        ) : (
          <div className="flex flex-col items-center justify-center py-16 bg-white rounded-2xl border-2 border-dashed border-gray-100">
            <svg className="w-12 h-12 text-gray-300 mb-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
            </svg>
            <p className="text-gray-400 font-medium">Không tìm thấy dữ liệu phù hợp</p>
          </div>
        )}
      </div>
    </div>
  );
};