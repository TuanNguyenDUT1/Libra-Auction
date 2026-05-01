'use server';
import type { ServerAPIResponse } from "@/types/serser_API_response";

export async function ServerAPICall<T>(path: string, request: RequestInit): Promise<ServerAPIResponse<T>> {
    let res: Response;
    try {
        res = await fetch(process.env.BACKEND_SERVER_URL + path, request);
    } catch (networkError) {
        console.error("Network error:", networkError);
        return {
            status: 503,
            isSuccess: false,
            errorMessage: "Cannot connect to server",
        } as ServerAPIResponse<T>;
    }
    console.log(res);
    try {
        if(res.status == 204) {
            return {
                status: res.status,
                isSuccess: true,
                errorMessage: undefined,
                data: undefined
            }
        }
        const data = await res.json();
        return {
            ...data,
            status: res.status
        } as ServerAPIResponse<T>;
    }
    catch (parseError) {
        console.error("Parse JSON failed:", parseError);
        return {
            status: res.status,
            isSuccess: false,
            errorMessage: "Invalid response format from server",
            data: undefined
        } as ServerAPIResponse<T>;
    }
}