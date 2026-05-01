import { fetchUpcomingAuctions } from "@/services/fetch_upcoming_auctions";
import NewListingsCard from "./upcoming_auciton_card";
import ShowMoreBtn from "../../show_more_btn";
import { UpcomingAuction } from "@/types/auction/upcoming_auction";
export default async function NewListingsSection() {
  const cards: UpcomingAuction[] = await fetchUpcomingAuctions();
  if(cards.length == 0) return null;
  return (
    <div className="py-16 bg-white">
      <div className="flex flex-col items-center gap-4">
        <h2 className="font-bold text-4xl">Upcoming auctions</h2>
        <p>Explore Newly Listed Auction Items</p>
      </div>
      <div className="grid grid-cols-3 gap-9 pt-6 px-18 items-stretch">
        {cards.slice(0, 3).map((card) => (
          <NewListingsCard key={card.id} card={card} />
        ))}
      </div>
      <div className="flex justify-center pt-18">
        <ShowMoreBtn />
      </div>
    </div>
  );
}
