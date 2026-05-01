import type { UpcomingAuction } from "@/types/auction/upcoming_auction";
import { CurrencyFormat } from "@/utils/currency_format";
import Image from "next/image";
export default function NewListingsCard({
  card,
}: {
  card: UpcomingAuction;
}) {
  return (
    <div className="flex flex-col h-full rounded-2xl overflow-hidden shadow-2xl">
<div className="relative w-full aspect-4/3 shadow-[0_6px_15px_rgba(0,0,0,0.08)]">        <div className="absolute size-full">
          <div className="relative size-full">
            <Image
              src={card.image_src}
              alt=""
              layout="fill"
              className="h-full object-cover"
            />
          </div>
        </div>
        <div className="absolute size-full">
          <div className="size-full flex flex-col justify-around p-4">
            <div className="flex">
              <div className="bg-green-500 p2 rounded-sm px-4 py-1 text-white font-bold uppercase">
                Upcoming
              </div>
            </div>
            <div className="flex-1"></div>
            <div className="flex">
              <p className="bg-(--time-left-box-color) [--time-left-box-color:#AFD3E2] p2 rounded-sm px-4 py-1 text-white font-bold lowercase">
                {card.starting_date.toDateString()}
              </p>
            </div>
          </div>
        </div>
      </div>
      <div className="h-full bg-white p-6 flex flex-col gap-4">
        <p className="font-bold text-2xl uppercase truncate">{card.title}</p>
        <div className="flex border-b border-b-gray-500 pb-3">
          <p className="text-lg">Starting bid</p>
          <div className="flex-1"></div>
          <p className="text-xl font-bold">
            {CurrencyFormat(card.starting_bid)}
          </p>
        </div>
        <div className="flex pt-1">
          <p className="text-lg">{card.bidders} Bidders</p>
          <div className="flex-1"></div>
          <a className="mt-auto bg-(--primary-color) text-white px-6 py-2 font-bold hover:bg-(--primary-color)/90 active:bg-(--primary-color)/80" href={`categories/${card.category_id}/auctions/${card.id}`}>
            Register Now
          </a>
        </div>
      </div>
    </div>
  );
}
