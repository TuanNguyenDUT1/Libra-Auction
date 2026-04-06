import { cookies } from "next/headers";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
    try {
        const body = await request.json();
        const { refreshToken } = body;
        const res = await fetch(process.env.BACKEND_SERVER_URL! + '/auth/refresh', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'refreshToken': refreshToken
            })
        });
        const data = await res.json();
        if (!res.ok) {
            return NextResponse.json({ message: data.message || "Refresh failed" }, { status: res.status });
        }
        const jwtToken = data.token;
        const cookieStore = await cookies();
        cookieStore.set({
            name: 'jwtToken',
            value: jwtToken,
            httpOnly: true,
            secure: true,
            maxAge: 60 * 60 * 24 // 1 day
        });
        return NextResponse.json({ message: "Refresh successful" }, { status: 200 })
    }
    catch (error) {
        console.log(error);
        return NextResponse.json({ message: "Internal server error" }, { status: 500 })
    }
    return NextResponse.json({ message: "Internal server error" }, { status: 500 })
}