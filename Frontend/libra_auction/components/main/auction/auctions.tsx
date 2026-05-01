import { FetchAuctionCards } from "@/services/fetch_auction_cards";
import AuctionCard from "./auction_card";
import { AuctionFilterSidebar } from "@/components/auction_filter_sidebar";

export default async function Auctions() {
    const cards = await FetchAuctionCards();

    return (
        <div className="flex min-h-screen bg-[var(--background-color)]">
            {/* Side bar - Chiếm 1/4 và giữ cố định khi scroll */}
            <aside className="w-1/4 sticky top-0 h-screen border-r border-gray-100 bg-white p-6 hidden md:block">
                <AuctionFilterSidebar />
            </aside>

            {/* Main Content - Chiếm 3/4 */}
            <main className="w-full md:w-3/4 p-6 lg:p-8">
                <header className="flex justify-between items-end mb-8">
                    <div>
                        <h1 className="text-2xl font-bold text-gray-800">Sàn đấu giá trực tuyến</h1>
                        <p className="text-sm text-gray-500 mt-1">Tìm thấy {cards.length} phiên đấu giá đang diễn ra</p>
                    </div>
                    
                    {/* Sort Dropdown đơn giản */}
                    <select className="text-sm border border-gray-200 rounded-lg p-2 outline-none focus:ring-1 focus:ring-[var(--primary-color)]">
                        <option>Mới nhất</option>
                        <option>Giá thấp nhất</option>
                        <option>Giá cao nhất</option>
                        <option>Sắp kết thúc</option>
                    </select>
                </header>

                {/* Grid 4 cột hiện đại */}
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                    {cards.map((card) => (
                        <AuctionCard key={card.auction_id} auctionCard={card} />
                    ))}
                </div>

                {/* Empty State nếu không có thẻ */}
                {cards.length === 0 && (
                    <div className="text-center py-20 bg-white rounded-3xl border-2 border-dashed border-gray-100">
                        <p className="text-gray-400">Không tìm thấy phiên đấu giá nào phù hợp.</p>
                    </div>
                )}
            </main>
        </div>
    );
}