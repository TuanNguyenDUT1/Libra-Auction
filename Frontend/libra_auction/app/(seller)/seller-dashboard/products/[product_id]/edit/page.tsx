"use client";
import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import ProductEditForm from "@/components/seller/product/product_edit_form";
import { Product } from "@/types/product/product";
import { fetchPublicProduct } from "@/services/fetch_public_product";
import { useRouter } from "next/navigation";

export default function EditProductPage() {
    const params = useParams();
    const router = useRouter();
    const [product, setProduct] = useState<Product | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const timer = setTimeout(async () => {
            if (!params.product_id || Array.isArray(params.product_id)) {
                setLoading(false);
                return;
            }

            try {
                setLoading(true);

                const data = await fetchPublicProduct(params.product_id);
                console.log("Fetched product detail for editing:", data);
                setProduct(data);
            } catch (e) {
                console.error("Fetch error:", e);
            } finally {
                setLoading(false);
            }
        }, 1000);
        return () => clearTimeout(timer);
    }, [params.product_id]);

    // loading UI
    if (loading) {
        return <div className="p-10 text-center">Đang tải dữ liệu...</div>;
    }

    // not found
    if (!product) {
        return <div className="p-10 text-center text-red-500">Không tìm thấy sản phẩm</div>;
    }

    return (
        <main className="p-6 md:p-10 bg-(--background-color) min-h-screen">
            <button
                onClick={() => router.back()}
                className="mb-6 flex items-center gap-2 text-sm font-bold text-gray-500 hover:text-(--primary-color) hover:cursor-pointer transition-colors"
            >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="m15 18-6-6 6-6" /></svg>
                Quay lại
            </button>
            <div className="p-6">
                <ProductEditForm
                    initialData={product}
                />
            </div>
        </main>
    );
}