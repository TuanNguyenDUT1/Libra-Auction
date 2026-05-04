'use server';
import { getJWTTokenInfo } from "@/lib/get_jwt_token_info";
import { ServerAPICall } from "@/lib/server_API_call";
import { Auction } from "@/types/auction/auction";
import { NewAuction } from "@/types/auction/new-auction";

export async function createAuction(auction: NewAuction): Promise<Auction> {
    const jwtTokenInfo = await getJWTTokenInfo();
    if (!jwtTokenInfo.token) {
        throw new Error("User's credentials not found");
    }

    const request: RequestInit = {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + jwtTokenInfo.token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(
            auction
        )
    }
    const res = await ServerAPICall<Auction>("/api/auctions", request);
    if (res.isSuccess && res.data) {
        console.log(res.data);
        return res.data;
    }
    throw new Error(res.errorMessage || "Failed to create auctions");
}