import { FetchNewListings } from "@/services/fetch_new_listings";
import NewListingsCard from "./new_listings_card";
import ShowMoreBtn from "./show_more_btn";
export default async function NewListingsSection() {
  const cards = await FetchNewListings();
  return (
    <div className="py-16 bg-white">
      <div className="flex flex-col items-center gap-4">
        <h2 className="font-bold text-4xl">New Listings</h2>
        <p>Explore Newly Listed Auction Items</p>
      </div>
      <div className="flex gap-9 justify-center pt-6 px-18">
        {cards.map((card) => {
          return <NewListingsCard key={card.id} card={card}/>;
        })}
      </div>
      <div className="flex justify-center pt-18">
        <ShowMoreBtn/>
      </div>
    </div>
  );
}
