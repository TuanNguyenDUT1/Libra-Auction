import AuctionForm from "@/components/seller/auction/auctionForm";
import { fetchProducts } from "@/services/fetch_products";

export default async function page() {
    const products = await fetchProducts();
    console.log("Fetched products for auction form:", products);
    return (
        <AuctionForm products={products} />
    );
}