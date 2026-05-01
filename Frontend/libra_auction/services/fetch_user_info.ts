'use server';
import { getJWTTokenInfo } from "@/lib/get_jwt_token_info";
import { ServerAPICall } from "@/lib/server_API_call";
import { UserInfo } from "@/types/user_info";

export async function fetchUserInfo(user_id: string): Promise<UserInfo> {
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
    const res = await ServerAPICall<UserInfo>("/api/users/" + user_id, request);
    if (res.isSuccess && res.data) return res.data;
    throw new Error(res.errorMessage || "Failed to fetch user info");
}