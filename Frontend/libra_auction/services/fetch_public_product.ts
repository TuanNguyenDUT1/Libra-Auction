'use server';
import { ServerAPICall } from "@/lib/server_API_call";
import { Product } from "@/types/product/product";

export async function fetchPublicProduct(product_id: string): Promise<Product> {
    const request: RequestInit = {
        method: "GET",
    }
    const res = await ServerAPICall<Product>("/api/public/products/" + product_id, request);
    if (res.isSuccess && res.data) {
        console.log(res.data);
        return res.data;
    }
    throw new Error(res.errorMessage || "Failed to fetch product");
}