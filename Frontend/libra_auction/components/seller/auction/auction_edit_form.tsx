"use client";

import { NewAuction } from "@/types/auction/new-auction";
import { useState, useEffect } from "react";
import { LoaiDauGia } from "@/components/seller/auction/auctionForm";

interface AuctionFormProps {
  initialData?: NewAuction;
  onSubmit: (data: NewAuction) => Promise<void>;
  isUpdating?: boolean;
}

export const AuctionEditForm = ({ initialData, onSubmit, isUpdating }: AuctionFormProps) => {
  const [formData, setFormData] = useState<NewAuction>({
    taiSanId: "",
    thoiGianBatDau: "",
    thoiLuong: 60,
    giaKhoiDiem: 0,
    buocGiaNhoNhat: 0,
    loaiDauGia: LoaiDauGia.DAU_GIA_LEN,
    tienCoc: 0
  });

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    }
  }, [initialData]);

  const handleChange = (name: string, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    try {
      await onSubmit({
        ...formData,
        thoiGianBatDau: new Date(formData.thoiGianBatDau).toISOString()
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-6 bg-white p-8 rounded-2xl border border-gray-100 shadow-sm max-w-2xl">
      
      <h2 className="text-lg md:text-xl font-bold text-gray-800 border-b pb-4">
        {isUpdating ? "Cập nhật phiên đấu giá" : "Tạo phiên đấu giá"}
      </h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">

        {/* Thời gian bắt đầu */}
        <div className="flex flex-col gap-1">
          <label className="text-sm font-medium">Thời gian bắt đầu</label>
          <input
            type="datetime-local"
            value={
              formData.thoiGianBatDau
                ? new Date(formData.thoiGianBatDau).toISOString().slice(0, 16)
                : ""
            }
            onChange={(e) => handleChange("thoiGianBatDau", e.target.value)}
            required
            className="p-2.5 border rounded-xl"
          />
        </div>

        {/* Thời lượng */}
        <div className="flex flex-col gap-1">
          <label className="text-sm font-medium">Thời lượng (phút)</label>
          <input
            type="number"
            value={formData.thoiLuong}
            onChange={(e) => handleChange("thoiLuong", Number(e.target.value))}
            min={1}
            required
            className="p-2.5 border rounded-xl"
          />
        </div>

        {/* Giá khởi điểm */}
        <div className="flex flex-col gap-1">
          <label className="text-sm font-medium">Giá khởi điểm</label>
          <input
            type="number"
            value={formData.giaKhoiDiem}
            onChange={(e) => handleChange("giaKhoiDiem", Number(e.target.value))}
            min={0}
            required
            className="p-2.5 border rounded-xl"
          />
        </div>

        {/* Bước giá */}
        <div className="flex flex-col gap-1">
          <label className="text-sm font-medium">Bước giá tối thiểu</label>
          <input
            type="number"
            value={formData.buocGiaNhoNhat}
            onChange={(e) => handleChange("buocGiaNhoNhat", Number(e.target.value))}
            min={0}
            required
            className="p-2.5 border rounded-xl"
          />
        </div>

        {/* ✅ Tiền cọc */}
        <div className="flex flex-col gap-1">
          <label className="text-sm font-medium">Tiền cọc</label>
          <input
            type="number"
            value={formData.tienCoc}
            onChange={(e) => handleChange("tienCoc", Number(e.target.value))}
            min={0}
            required
            className="p-2.5 border rounded-xl"
          />
        </div>

        {/* Loại đấu giá */}
        <div className="flex flex-col gap-1">
          <label className="text-sm font-medium">Loại đấu giá</label>
          <select
            value={formData.loaiDauGia}
            onChange={(e) => handleChange("loaiDauGia", e.target.value)}
            className="p-2.5 border rounded-xl bg-white"
          >
            <option value={LoaiDauGia.DAU_GIA_LEN}>Đấu giá tăng</option>
            <option value={LoaiDauGia.DAU_GIA_XUONG}>Đấu giá giảm</option>
            <option value={LoaiDauGia.DAU_GIA_KIN}>Đấu giá kín</option>
            <option value={LoaiDauGia.DAU_GIA_NGUOC}>Đấu giá ngược</option>
          </select>
        </div>

      </div>

      <div className="flex gap-3 pt-4 border-t">
        <button
          type="submit"
          disabled={loading}
          className="flex-1 bg-(--primary-color) text-white font-bold py-3 rounded-xl disabled:opacity-50"
        >
          {loading ? "Đang lưu..." : "Lưu thay đổi"}
        </button>
      </div>

    </form>
  );
};