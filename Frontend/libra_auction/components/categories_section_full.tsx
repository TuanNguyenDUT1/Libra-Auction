import { CategoryCardType } from "@/types/category_card_type";
import CategoryCard from "./category_card";
import { FetchCategories } from "@/services/fetch_categories";

export default async function CategoriesSectionFull() {
  const cards: CategoryCardType[] = await FetchCategories();
  return (
    cards?.length > 0 &&
    (<div className="py-16">
      <div className="flex flex-col items-center gap-4">
        <h2 className="font-bold text-4xl">Categories</h2>
        <p>Explore Auctions Across Multiple Categories</p>
      </div>
      <div className="grid grid-cols-5 gap-5 pt-6 px-12">
        {cards.map((card) => {
          return <CategoryCard key={card.id} card={card} />;
        })}
      </div>
    </div>)
  );
}