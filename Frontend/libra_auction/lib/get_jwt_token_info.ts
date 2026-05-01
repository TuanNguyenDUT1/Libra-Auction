'use server';
import { JWTTokenInfo } from "@/types/jwt_token_info";
import { cookies } from "next/headers";

export async function getJWTTokenInfo(): Promise<JWTTokenInfo> {
    const cookieStore = await cookies();
    const jwtToken = cookieStore.get("jwtToken")?.value;
    const refreshToken = cookieStore.get("refreshToken")?.value;
    return {
        token: jwtToken,
        refresh: refreshToken
    }
}