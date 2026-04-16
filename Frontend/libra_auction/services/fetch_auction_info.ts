import { AuctionInfoType } from "@/types/auction_info_type";
export function FetchAuctionInfo(id: string): AuctionInfoType | null {
    if (id === "003") {
        return {
            category_id: "cat-003",
            category_name: "Collectibles Auction",

            auction_id: "003",
            auction_name: "Star Rail Hotaru Spring Gift Ver. 1/8 Scale Complete Figure",
            auction_status: "UPCOMING",
            auction_type: "INCREMENT",

            start_time: "2026-04-15T20:00:00",
            duration: 7200000, // 2 tiếng

            starting_price: 2000000,
            current_price: 0, // chưa có ai bid
            min_bid_increment: 100000,

            product_id: "prod-003",
            product_name: "Hotaru Spring Gift Figure",
            quantity: 1,
            description: "Figure chính hãng từ Honkai Star Rail, phiên bản giới hạn mùa xuân.",

            images: [
                "https://res.cloudinary.com/dhl7uh3pp/image/upload/v1776054541/live-auction-3_xozt7d.jpg",
                "https://res.cloudinary.com/dhl7uh3pp/image/upload/v1776061451/FIGURE-4_sd4vc4.jpg",
                "https://res.cloudinary.com/dhl7uh3pp/image/upload/v1776061451/FIGURE-5_aeqeav.jpg",
                "https://res.cloudinary.com/dhl7uh3pp/image/upload/v1776061451/FIGURE-3_adnb5h.jpg",
                "https://res.cloudinary.com/dhl7uh3pp/image/upload/v1776061451/FIGURE-1_ircann.jpg",
                "https://res.cloudinary.com/dhl7uh3pp/image/upload/v1776061451/FIGURE-2_nch5gm.jpg"
            ],

            attributes: [
                { name: "Brand", value: "miHoYo" },
                { name: "Scale", value: "1/8" },
                { name: "Material", value: "PVC" },
                { name: "Condition", value: "New" }
            ],

            total_bids: 0,
            total_participants: 0
        }
    }
    if (id === "004") {
        return {
            category_id: "cat-003",
            category_name: "Collectibles Auction",

            auction_id: "004",
            auction_name: "Star Rail Kafka Limited Edition Figure",
            auction_status: "LIVE",
            auction_type: "ENGLISH",

            start_time: "2026-04-13T18:00:00",
            duration: 7200,

            starting_price: 3000000,
            current_price: 4750000, // đang có giá hiện tại
            min_bid_increment: 100000,

            product_id: "prod-004",
            product_name: "Kafka Limited Figure",
            quantity: 1,
            description: "Figure Kafka phiên bản giới hạn, cực hiếm, full box.",

            images: [
                "https://cdn.example.com/images/kafka-1.jpg",
                "https://cdn.example.com/images/kafka-2.jpg",
                "https://cdn.example.com/images/kafka-3.jpg"
            ],

            attributes: [
                { name: "Brand", value: "miHoYo" },
                { name: "Scale", value: "1/7" },
                { name: "Material", value: "ABS & PVC" },
                { name: "Condition", value: "Like New" }
            ],

            total_bids: 18,
            total_participants: 6
        }
    }
    return null;
}