'use server';
import { getJWTTokenInfo } from "@/lib/get_jwt_token_info";
import { ServerAPICall } from "@/lib/server_API_call";
import { NewProduct } from "@/types/product/new-product";
import { Product } from "@/types/product/product";

export async function createProduct(product: NewProduct): Promise<Product> {
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
            product
        )
    }
    const res = await ServerAPICall<Product>("/api/products", request);
    if (res.isSuccess && res.data) {
        console.log(res.data);
        return res.data;
    }
    throw new Error(res.errorMessage || "Failed to create products");
}