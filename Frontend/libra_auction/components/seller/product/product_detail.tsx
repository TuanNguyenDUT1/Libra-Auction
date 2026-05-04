import { Product } from "@/types/product/product";
import { ImageGallery } from "./image_gallery";
import Link from "next/link";

export const ProductDetail = ({ data }: { data: Product }) => {
    return (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-10 bg-white p-6 md:p-10 rounded-3xl border border-gray-100 shadow-sm">
            {/* Cột trái: Gallery */}
            <ImageGallery images={data.images} />

            {/* Cột phải: Thông tin */}
            <div className="flex flex-col">
                <nav className="text-xs font-bold text-(--primary-color) uppercase tracking-widest mb-2">
                    {data.category_name}
                </nav>

                <h1 className="text-3xl font-bold text-gray-800 mb-4">{data.product_name}</h1>

                <div className="flex items-center gap-6 mb-6 pb-6 border-b border-gray-50">
                    <div>
                        <p className="text-[10px] text-gray-400 uppercase font-bold">Số lượng kho</p>
                        <p className="text-xl font-bold text-gray-800">{data.quantity} chiếc</p>
                    </div>
                    <div>
                        <p className="text-[10px] text-gray-400 uppercase font-bold">Mã tài sản</p>
                        <p className="text-sm font-mono mt-1">#{data.product_id}</p>
                    </div>
                </div>

                <div className="mb-8">
                    <h3 className="text-sm font-bold text-gray-800 mb-2">Mô tả sản phẩm</h3>
                    <p className="text-gray-600 text-sm leading-relaxed">{data.description}</p>
                </div>

                {/* Attributes Section */}
                <div className="mb-8">
                    <h3 className="text-sm font-bold text-gray-800 mb-3">Thông số kỹ thuật</h3>
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                        {data.attributes.map((attr, idx) => (
                            <div key={idx} className="flex justify-between p-3 bg-(--background-color) rounded-xl border border-gray-50">
                                <span className="text-xs text-gray-500">{attr.key}</span>
                                <span className="text-xs font-bold text-gray-700">{attr.value}</span>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Action Buttons */}
                <div className="flex gap-3 mt-auto pt-6">
                    <Link
                        href={`/seller-dashboard/products/${data.product_id}/edit`}
                        className="flex-1 flex justify-center items-center gap-2 bg-(--primary-color) hover:bg-(--secondary-color) text-white font-bold py-3 rounded-xl transition-all active:scale-95"
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z" /><path d="m15 5 4 4" /></svg>
                    Sửa tài sản
                </Link>
                <Link
                    href={`/seller-dashboard/products/${data.product_id}/delete`}
                    className="px-6 flex justify-center items-center bg-red-50 text-red-600 hover:bg-red-100 font-bold rounded-xl transition-all active:scale-95 border border-red-100"
                >
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M3 6h18" /><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" /><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" /></svg>
                </Link>
            </div>
        </div>
    </div >
  );
};