"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { AuctionDetail } from "@/components/seller/auction/auction_detail";
import { Auction } from "@/types/auction/auction";
import { fetchPublicAuction } from "@/services/fetch_public_auction";

export default function Page() {
    const params = useParams();
    const [data, setData] = useState<Auction | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (!params.auction_id || Array.isArray(params.auction_id)) {
                    return;
                }
                const data = await fetchPublicAuction(params.auction_id);
                if (data) {
                    setData(data);
                }
            } catch (err) {
                console.error("Fetch error:", err);
                setData(null);
            } finally {
                setLoading(false);
            }
        };

        if (params.auction_id) {
            fetchData();
        }
    }, [params.auction_id]);

    if (loading) return <p>Loading...</p>;

    return (
        <>
            {data ? (
                <AuctionDetail data={data} />
            ) : (
                <p>Auction not found</p>
            )}
        </>
    );
}