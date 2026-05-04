"use client";
import { createAuction } from "@/services/create_auction";
import { NewAuction } from "@/types/auction/new-auction";
import { Product } from "@/types/product/product";
import { useState } from "react";

export enum LoaiDauGia {
  DAU_GIA_LEN = "DAU_GIA_LEN",
  DAU_GIA_XUONG = "DAU_GIA_XUONG",
  DAU_GIA_KIN = "DAU_GIA_KIN",
  DAU_GIA_NGUOC = "DAU_GIA_NGUOC"
}

export default function AuctionForm({ products }: { products: Product[] }) {
  const [step, setStep] = useState(1);

  const [formData, setFormData] = useState({
    productId: "",
    thoiGianBatDau: "",
    thoiLuong: 60,
    giaKhoiDiem: 0,
    buocGiaNhoNhat: 0,
    tienCoc: 0, // State cho tiền cọc
    loaiDauGia: LoaiDauGia.DAU_GIA_LEN
  });

  const handleChange = (field: string, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async () => {
    try {
      const auction: NewAuction = {
        taiSanId: formData.productId,
        thoiGianBatDau: new Date(formData.thoiGianBatDau).toISOString(),
        thoiLuong: Number(formData.thoiLuong),
        giaKhoiDiem: Number(formData.giaKhoiDiem),
        buocGiaNhoNhat: Number(formData.buocGiaNhoNhat),
        loaiDauGia: formData.loaiDauGia,
        tienCoc: Number(formData.tienCoc)
      }

      const res = await createAuction(auction);
      if (res) {
        alert("Tạo phiên đấu giá thành công!");
        window.location.href = "/seller-dashboard/auctions";
      }
    } catch (err) {
      console.error(err);
      alert("Tạo phiên đấu giá thất bại!");
    }
  };

  return (
    <div className="bg-white p-8 rounded-2xl border border-[#AFD3E2] space-y-8 shadow-sm">
      {/* STEP HEADER */}
      <div className="flex border-b border-[#AFD3E2]">
        <div className={`flex-1 p-4 text-center font-bold ${step === 1 ? 'bg-[#19A7CE] text-white' : 'bg-gray-50'}`}>
          1. Chọn Sản Phẩm
        </div>
        <div className={`flex-1 p-4 text-center font-bold ${step === 2 ? 'bg-[#19A7CE] text-white' : 'bg-gray-50'}`}>
          2. Thiết Lập Đấu Giá
        </div>
      </div>

      <div className="p-6">
        {/* STEP 1 */}
        {step === 1 && (
          <div className="space-y-4">
            {products.map((p) => (
              <div
                key={p.product_id}
                onClick={() => handleChange("productId", p.product_id)}
                className={`p-4 border rounded cursor-pointer ${formData.productId === p.product_id
                  ? "border-[#146C94] bg-[#F6F1F1]"
                  : "border-gray-200"
                  }`}
              >
                <div className="flex flex-col">
                  <p className="font-bold text-[#146C94]">{p.product_name}</p>
                  <p className="text-xs text-gray-400">ID: {p.product_id}</p>
                  {"quantity" in p && (
                    <p className="text-xs text-gray-500">Số lượng: {p.quantity}</p>
                  )}
                </div>
              </div>
            ))}
            <button
              disabled={!formData.productId}
              onClick={() => setStep(2)}
              className="w-full bg-[#146C94] text-white py-3 rounded disabled:opacity-50"
            >
              Tiếp tục
            </button>
          </div>
        )}

        {/* STEP 2 */}
        {step === 2 && (
          <div className="space-y-5">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Thời gian bắt đầu</label>
                <input
                  type="datetime-local"
                  onChange={(e) => handleChange("thoiGianBatDau", e.target.value)}
                  className="border p-2 rounded"
                />
              </div>

              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Thời lượng (phút)</label>
                <input
                  type="number"
                  value={formData.thoiLuong}
                  onChange={(e) => handleChange("thoiLuong", Number(e.target.value))}
                  className="border p-2 rounded"
                />
              </div>

              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Giá khởi điểm</label>
                <input
                  type="number"
                  onChange={(e) => handleChange("giaKhoiDiem", Number(e.target.value))}
                  className="border p-2 rounded"
                />
              </div>

              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Bước giá tối thiểu</label>
                <input
                  type="number"
                  onChange={(e) => handleChange("buocGiaNhoNhat", Number(e.target.value))}
                  className="border p-2 rounded"
                />
              </div>

              {/* TRƯỜNG TIỀN CỌC - CSS ĐỒNG NHẤT */}
              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Tiền đặt cọc</label>
                <input
                  type="number"
                  placeholder="0"
                  onChange={(e) => handleChange("tienCoc", Number(e.target.value))}
                  className="border p-2 rounded"
                />
              </div>

              <div className="flex flex-col gap-1">
                <label className="text-sm font-medium">Loại đấu giá</label>
                <select
                  value={formData.loaiDauGia}
                  onChange={(e) => handleChange("loaiDauGia", e.target.value)}
                  className="border p-2 rounded"
                >
                  <option value={LoaiDauGia.DAU_GIA_LEN}>Đấu giá tăng</option>
                  <option value={LoaiDauGia.DAU_GIA_XUONG}>Đấu giá giảm</option>
                  <option value={LoaiDauGia.DAU_GIA_KIN}>Đấu giá kín</option>
                  <option value={LoaiDauGia.DAU_GIA_NGUOC}>Đấu giá ngược</option>
                </select>
              </div>
            </div>

            <div className="flex gap-3">
              <button
                onClick={() => setStep(1)}
                className="flex-1 border py-3 rounded"
              >
                Quay lại
              </button>
              <button
                onClick={handleSubmit}
                className="flex-1 bg-[#146C94] text-white py-3 rounded"
              >
                Tạo phiên đấu giá
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}