'use server';
import { getJWTTokenInfo } from "@/lib/get_jwt_token_info";
import { ServerAPICall } from "@/lib/server_API_call";

export async function deleteProduct(product_id: string): Promise<void> {
    const jwtTokenInfo = await getJWTTokenInfo();
    if (!jwtTokenInfo.token) {
        throw new Error("User's credentials not found");
    }

    const request: RequestInit = {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + jwtTokenInfo.token,
            "Content-Type": "application/json"
        }
    }
    const res = await ServerAPICall<null>("/api/products/" + product_id, request);
    if (res.isSuccess) {
        return;
    }
    throw new Error(res.errorMessage || "Failed to delete products");
}