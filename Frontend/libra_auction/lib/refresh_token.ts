'use server';
import { getJWTPublicKey } from "./get_cert";
import * as jose from "jose";
import { getJWTTokenInfo } from "./get_jwt_token_info";

export async function refreshToken() {
    const jwtTokenInfo = await getJWTTokenInfo();
    const alg = 'RS256';
    const spki = await getJWTPublicKey();
    if (spki && jwtTokenInfo.refresh) {
        const publicKey = await jose.importSPKI(spki, alg);
        try {
            await jose.jwtVerify(jwtTokenInfo.refresh, publicKey);
            await fetch('api/refresh', {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    'refreshToken': jwtTokenInfo.refresh
                })
            });
            return true;
        } catch (error) {
            console.error("Can't refresh token: " + error)
            return false;
        }
    }
    return false;
}