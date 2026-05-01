import CategoryCard from "@/components/main/category/category_card";
import { fetchCategories } from "@/services/fetch_categories";
import { Category } from "@/types/category";

export default async function CategoriesSection() {
  const cards: Category[] = await fetchCategories();
  
  const displayCards = cards?.slice(0, 5);

  if (!displayCards || displayCards.length === 0) return null;

  return (
    <div className="py-16">
      <div className="flex flex-col items-center gap-4 mb-6">
        <h2 className="font-bold text-4xl">Categories</h2>
        <p>Explore Auctions Across Multiple Categories</p>
      </div>

      <div className="grid grid-cols-5 gap-5 px-12">
        {displayCards.map((card) => (
          <CategoryCard key={card.id} card={card} />
        ))}
      </div>
    </div>
  );
}