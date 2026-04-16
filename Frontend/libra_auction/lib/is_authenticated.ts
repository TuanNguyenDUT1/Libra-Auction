import { cookies } from "next/headers";
import { promises as fs } from 'fs';
import * as jose from "jose";
import path from "path";
import { JWSSignatureVerificationFailed, JWTExpired } from "jose/errors";

export async function isAuthenticated() {
    const cookieStore = await cookies();
    const jwtToken = cookieStore.get("jwtToken");
    const alg = 'RS256';
    const spki = await getCert();
    if (spki && jwtToken && jwtToken.value) {
        const publicKey = await jose.importSPKI(spki, alg);
        try {
            await jose.jwtVerify(jwtToken.value, publicKey);
        }
        catch (error) {
            if (error instanceof JWTExpired) {
                console.log("Token hết hạn");
                const refreshToken = cookieStore.get("refreshToken");
                if (refreshToken && refreshToken.value) {
                    try {
                        await jose.jwtVerify(refreshToken.value, publicKey);
                        await fetch('api/refresh', {
                            method: "POST",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                'refreshToken': refreshToken.value
                            })
                        });
                    } catch (insideError) {
                        console.log("Can't refresh token")
                    }
                }
            }

            if (error instanceof JWSSignatureVerificationFailed) {
                console.log("Invaild token");
            }
            return false;
        }
        return true;
    }
    return false;
}

async function getCert() {
    const certPath = path.join(process.cwd(), 'certs', 'public.pem');
    try {
        const data = await fs.readFile(certPath, 'utf8');
        return data;
    } catch (err) {
        console.error(err);
        return null;
    }
}