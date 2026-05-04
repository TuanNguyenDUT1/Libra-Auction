"use client";

import { useState } from "react";
import { Product } from "@/types/product/product";
import { ProductSearchBar } from "./product_search_bar";
import { ProductItem } from "./product_item";
import { useRouter } from "next/navigation";

interface ProductListProps {
  initialData: Product[];
}

export const ProductList = ({ initialData }: ProductListProps) => {
  const [searchTerm, setSearchTerm] = useState("");

  const filteredProducts = initialData.filter((p) =>
    p.product_name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    p.category_name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const router = useRouter();

  return (
    <div className="space-y-4">
      <ProductSearchBar 
        onSearch={setSearchTerm} 
        onAddClick={() => router.push("/seller-dashboard/products/new-product")} 
      />

      <div className="grid grid-cols-1 gap-3">
        {filteredProducts.length > 0 ? (
          filteredProducts.map((product) => (
            <ProductItem
              key={product.product_id}
              product={product}
              onView={(id) => router.push(`/seller-dashboard/products/${id}`)}
              onEdit={(id) => router.push(`/seller-dashboard/products/${id}/edit`)}
              onDelete={(id) => router.push(`/seller-dashboard/products/${id}/delete`)}
            />
          ))
        ) : (
          <div className="flex flex-col items-center justify-center py-16 bg-white rounded-2xl border-2 border-dashed border-gray-100">
            <svg className="w-12 h-12 text-gray-300 mb-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
            </svg>
            <p className="text-gray-400 font-medium">Không tìm thấy dữ liệu phù hợp</p>
          </div>
        )}
      </div>
    </div>
  );
};