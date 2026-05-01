import React from "react";
import { AuctionInfoType } from "@/types/auction_info_type";

export default function AuctionInfoDetailsSection({
  autionInfos,
}: {
  autionInfos: AuctionInfoType;
}) {
  return (
    <div className="w-full bg-gray-50/50 py-10 px-16">
      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-8">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">
          Description
        </h2>
        <div className="text-gray-600 leading-relaxed whitespace-pre-line text-sm md:text-base">
          {autionInfos.description ? (
            autionInfos.description
          ) : (
            <span className="text-gray-400 italic">No description provided.</span>
          )}
        </div>
      </div>
      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-8 mt-10">
        <h2 className="text-2xl font-bold text-gray-900 mb-6">
          Specifications
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {autionInfos.attributes && autionInfos.attributes.length > 0 ? (
            autionInfos.attributes.map((attr, index) => (
              <div
                key={index}
                className="flex justify-between items-center bg-gray-50 border border-gray-100 rounded-xl px-4 py-3"
              >
                <span className="text-gray-500 text-sm">
                  {attr.name}
                </span>
                <span className="font-semibold text-gray-800 text-sm text-right ml-4 wrap-break-word">
                  {attr.value}
                </span>
              </div>
            ))
          ) : (
            <div className="text-gray-400 text-sm col-span-full">
              No specifications available.
            </div>
          )}
        </div>
      </div>

    </div>
  );
}