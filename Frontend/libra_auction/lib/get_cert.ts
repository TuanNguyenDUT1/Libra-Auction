'use server';
import path from "path";
import { promises as fs } from 'fs';

export async function getCert() {
    const certPath = path.join(process.cwd(), 'certs', 'public.pem');
    try {
        const data = await fs.readFile(certPath, 'utf8');
        return data;
    } catch (err) {
        console.error(err);
        return null;
    }
}