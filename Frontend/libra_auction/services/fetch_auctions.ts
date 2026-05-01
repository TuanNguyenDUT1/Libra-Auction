'use server';
import { getJWTTokenInfo } from "@/lib/get_jwt_token_info";
import { ServerAPICall } from "@/lib/server_API_call";
import { Auction } from "@/types/auction/auction";
import { PageResponse } from "@/types/page_response";

export async function fetchAuctions(): Promise<Auction[]> {
    const jwtTokenInfo = await getJWTTokenInfo();
    if (!jwtTokenInfo.token) {
        throw new Error("User's credentials not found");
    }

    const request: RequestInit = {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + jwtTokenInfo.token
        }
    }
    const res = await ServerAPICall<PageResponse<Auction>>("/api/auctions", request);
    if (res.isSuccess && res.data) {
        console.log(res.data);
        return res.data.content;
    }
    else if (res.isSuccess) return [];
    throw new Error(res.errorMessage || "Failed to fetch live auctions");
}