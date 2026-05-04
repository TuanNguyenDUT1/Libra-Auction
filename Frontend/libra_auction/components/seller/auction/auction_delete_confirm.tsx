"use client";

import { Auction } from "@/types/auction/auction";
import { useState } from "react";

interface AuctionDeleteConfirmProps {
  auction: Auction;
  onDelete: () => Promise<void>;
  onCancel: () => void;
}

export const AuctionDeleteConfirm = ({ auction, onDelete, onCancel }: AuctionDeleteConfirmProps) => {
  const [isDeleting, setIsDeleting] = useState(false);

  const handleDelete = async () => {
    setIsDeleting(true);
    await onDelete();
    setIsDeleting(false);
  };

  return (
    <div className="bg-white p-8 rounded-2xl border border-gray-100 shadow-sm max-w-lg w-full">
      <div className="flex flex-col items-center text-center gap-4">
        {/* Warning Icon (SVG thuần) */}
        <div className="h-16 w-16 bg-red-50 rounded-full flex items-center justify-center text-red-500">
          <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="m12 13 2-2"/><path d="M12 9i1 1"/><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/><line x1="10" x2="10" y1="11" y2="17"/><line x1="14" x2="14" y1="11" y2="17"/></svg>
        </div>

        <div>
          <h2 className="text-xl font-bold text-gray-800">Xác nhận xóa phiên</h2>
          <p className="text-sm text-gray-500 mt-2">
            Bạn có chắc chắn muốn xóa phiên đấu giá này không? Hành động này không thể hoàn tác.
          </p>
        </div>

        {/* Thông tin phiên đấu giá để kiểm tra lần cuối */}
        <div className="w-full bg-[var(--background-color)] p-4 rounded-xl text-left border border-gray-100 my-2">
          <p className="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Tên phiên</p>
          <p className="text-base font-semibold text-gray-800">{auction.auction_name}</p>
          
          <div className="flex justify-between mt-3 pt-3 border-t border-gray-200">
            <div>
              <p className="text-[10px] text-gray-400 uppercase font-bold">Giá khởi điểm</p>
              <p className="text-sm font-bold text-[var(--secondary-color)]">{auction.starting_price.toLocaleString()} VND</p>
            </div>
            <div>
              <p className="text-[10px] text-gray-400 uppercase font-bold text-right">Trạng thái</p>
              <p className="text-sm font-medium text-gray-700">{auction.auction_status}</p>
            </div>
          </div>
        </div>

        <div className="flex gap-3 w-full mt-2">
          <button
            onClick={onCancel}
            disabled={isDeleting}
            className="flex-1 px-4 py-3 border border-gray-200 text-gray-600 font-bold rounded-xl hover:bg-gray-50 transition-all active:scale-95 disabled:opacity-50"
          >
            Hủy bỏ
          </button>
          <button
            onClick={handleDelete}
            disabled={isDeleting}
            className="flex-1 px-4 py-3 bg-red-500 hover:bg-red-600 text-white font-bold rounded-xl transition-all active:scale-95 shadow-lg shadow-red-100 disabled:opacity-50"
          >
            {isDeleting ? "Đang xóa..." : "Xác nhận xóa"}
          </button>
        </div>
      </div>
    </div>
  );
};