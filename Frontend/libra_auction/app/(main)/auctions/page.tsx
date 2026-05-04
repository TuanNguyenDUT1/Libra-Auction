import Auctions from "@/components/main/auction/auctions";

export default function page(props: { params: Promise<{ category_id: string }> }) {
    return (
        <Auctions />
    );
}