"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { AuctionEditForm } from "@/components/seller/auction/auction_edit_form";
import { NewAuction } from "@/types/auction/new-auction";
import { fetchPublicAuction } from "@/services/fetch_public_auction";
import { updateAuction } from "@/services/update_auction";

export default function EditAuctionPage() {
    const params = useParams();
    const router = useRouter();

    const [auctionData, setAuctionData] = useState<NewAuction | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchAuction = async () => {
            if (!params.auction_id || Array.isArray(params.auction_id)) {
                setLoading(false);
                return;
            }

            try {
                setLoading(true);

                const data = await fetchPublicAuction(params.auction_id);
                const newAuction: NewAuction = {
                    taiSanId: data.product_id,
                    buocGiaNhoNhat: data.min_bid_increment,
                    giaKhoiDiem: data.starting_price,
                    loaiDauGia: data.auction_type,
                    thoiGianBatDau: data.start_time,
                    thoiLuong: data.duration,
                    tienCoc: 0
                }
                setAuctionData(newAuction);
            } catch (e) {
                console.error("Fetch error:", e);
            } finally {
                setLoading(false);
            }
        };

        fetchAuction();
    }, [params.auction_id]);

    if (loading) {
        return <div className="p-10 text-center">Đang tải dữ liệu...</div>;
    }

    if (!auctionData) {
        return <div className="p-10 text-center text-red-500">Không tìm thấy phiên đấu giá</div>;
    }

    return (
        <div className="p-6 max-w-4xl mx-auto">

            {/* BACK BUTTON */}
            <button
                onClick={() => router.push("/seller-dashboard/auctions")}
                className="mb-6 text-sm text-gray-600 hover:text-(--primary-color) transition-colors"
            >
                ← Quay lại danh sách
            </button>

            <AuctionEditForm
                initialData={auctionData}
                onSubmit={async (formData) => {
                    if (!params.auction_id || Array.isArray(params.auction_id)) {
                        return;
                    }

                    const newAuction: NewAuction = {
                        taiSanId: formData.taiSanId,
                        buocGiaNhoNhat: formData.buocGiaNhoNhat,
                        giaKhoiDiem: formData.giaKhoiDiem,
                        loaiDauGia: formData.loaiDauGia,
                        thoiGianBatDau: formData.thoiGianBatDau,
                        thoiLuong: formData.thoiLuong,
                        tienCoc: formData.tienCoc
                    };
                    const res = await updateAuction(params.auction_id, newAuction);
                    if (res) {
                        alert("Chúc mừng! Phiên đấu giá đã được cập nhật thành công.");
                        window.location.replace("/seller-dashboard/auctions/" + params.auction_id);
                    } else {
                        throw new Error("Backend trả về lỗi");
                    }
                }}
                isUpdating={true}
            />
        </div>
    );
}