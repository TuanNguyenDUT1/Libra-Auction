"use client";
import { useState } from "react";
import { Product } from "@/types/product/product";

interface ProductDeleteConfirmProps {
  product: Product;
  onDelete: () => Promise<void>;
  onCancel: () => void;
}

export default function ProductDeleteConfirm({ product, onDelete, onCancel }: ProductDeleteConfirmProps) {
  const [isDeleting, setIsDeleting] = useState(false);

  const handleDelete = async () => {
    setIsDeleting(true);
    await onDelete();
    setIsDeleting(false);
  };

  return (
    <div className="bg-white p-8 rounded-2xl border border-red-100 shadow-xl max-w-md w-full animate-in fade-in zoom-in duration-300">
      <div className="flex flex-col items-center text-center">
        {/* Icon cảnh báo */}
        <div className="w-16 h-16 bg-red-50 rounded-full flex items-center justify-center text-red-500 mb-4">
          <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M3 6h18m-2 0v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6m3 0V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2m-6 5v6m4-6v6"/>
          </svg>
        </div>

        <h2 className="text-xl font-bold text-gray-800">Xác nhận xóa tài sản</h2>
        <p className="text-sm text-gray-500 mt-2">
          Bạn có chắc chắn muốn xóa tài sản này? Dữ liệu liên quan sẽ bị loại bỏ khỏi hệ thống.
        </p>

        {/* Box thông tin tài sản */}
        <div className="w-full bg-[#F6F1F1] p-4 rounded-xl mt-6 text-left border border-gray-100">
          <p className="text-[10px] font-bold text-[#146C94] uppercase tracking-widest">Tên tài sản</p>
          <p className="text-sm font-bold text-gray-800 truncate">{product.product_name}</p>
          
          <p className="text-[10px] font-bold text-[#146C94] uppercase tracking-widest mt-3">Mô tả ngắn</p>
          <p className="text-xs text-gray-600 line-clamp-2 italic">
            {product.description || "Không có mô tả cho tài sản này."}
          </p>
        </div>

        {/* Nhóm nút hành động */}
        <div className="flex gap-3 w-full mt-8">
          <button
            onClick={onCancel}
            disabled={isDeleting}
            className="flex-1 py-3 px-4 border border-gray-200 text-gray-600 font-bold rounded-xl hover:bg-gray-50 transition-all active:scale-95 disabled:opacity-50"
          >
            HỦY
          </button>
          <button
            onClick={handleDelete}
            disabled={isDeleting}
            className="flex-1 py-3 px-4 bg-red-500 hover:bg-red-600 text-white font-bold rounded-xl transition-all shadow-lg shadow-red-100 active:scale-95 disabled:opacity-50"
          >
            {isDeleting ? "ĐANG XÓA..." : "XÁC NHẬN XÓA"}
          </button>
        </div>
      </div>
    </div>
  );
}