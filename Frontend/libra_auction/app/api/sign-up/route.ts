import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
    try {
        const body = await request.json();
        const { fullName, username, email, password } = body;
        const res = await fetch(process.env.BACKEND_SERVER_URL! + '/auth/signup', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'fullName': fullName,
                'username': username,
                'email': email,
                'password': password
            })
        });
        const data = await res.json();
        if (!res.ok) {
            return NextResponse.json({ message: data.message || "Sign up failed" }, { status: res.status })
        }
        else if (res.status === 200 || res.status === 201) {
            return NextResponse.json({ message: "sign up success" }, { status: res.status });
        }
    }
    catch (error) {
        console.log(error);
        return NextResponse.json({ message: "Internal server error" }, { status: 500 })
    }
    return NextResponse.json({ message: "Internal server error" }, { status: 500 })
}