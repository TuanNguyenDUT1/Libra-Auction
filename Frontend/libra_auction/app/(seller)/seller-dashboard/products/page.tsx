import { ProductList } from "@/components/seller/product/product_list";
import { fetchProducts } from "@/services/fetch_products";
export default async function page() {
    const products = await fetchProducts();
    return (
        <ProductList initialData={products}/>
    );
}