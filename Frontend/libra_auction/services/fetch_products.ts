'use server';
import { getJWTTokenInfo } from "@/lib/get_jwt_token_info";
import { ServerAPICall } from "@/lib/server_API_call";
import { PageResponse } from "@/types/page_response";
import { Product } from "@/types/product/product";

export async function fetchProducts(): Promise<Product[]> {
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
    const res = await ServerAPICall<PageResponse<Product>>("/api/products", request);
    if (res.isSuccess && res.data) {
        console.log(res.data);
        return res.data.content;
    }
    else if (res.isSuccess) return [];
    throw new Error(res.errorMessage || "Failed to fetch products");
}