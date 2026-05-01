'use server';
import { ServerAPICall } from "@/lib/server_API_call";
import { Auction } from "@/types/auction/auction";
import { PageResponse } from "@/types/page_response";

export async function fetchPublicAuctions(): Promise<Auction[]> {
    const request: RequestInit = {
        method: "GET",
    }
    const res = await ServerAPICall<PageResponse<Auction>>("/api/public/auctions", request);
    if (res.isSuccess && res.data) {
        return res.data.content;
    }
    else if(res.isSuccess) return [];
    throw new Error(res.errorMessage || "Failed to fetch live auctions");
}