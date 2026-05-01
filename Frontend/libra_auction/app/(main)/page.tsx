import HomeBanner from "@/components/main/home/home_banner";
import CategoriesSection from "@/components/main/category/categories_section";
import LiveAuctionsSection from "@/components/main/live_auctions/live_auctions_section";
import NewListingsSection from "@/components/main/upcoming_auctions/upcoming_auctions_section";
export default function page() {
    return (
        <>
            <HomeBanner/>
            <CategoriesSection/>
            <LiveAuctionsSection/>
            <NewListingsSection/>
        </>
    );
}