import { CategoryCardType } from "@/types/category_card_type";
export async function FetchCategories(): Promise<CategoryCardType[]> {
  try {
    const res = await fetch(process.env.BACKEND_SERVER_URL! + "/api/categories");

    if (!res.ok) {
      return [];
    }

    const data = (await res.json()) as CategoryCardType[];
    for (const category of data) {
      category.href = "/categories/" + category.id;
    }
    return data;
  } catch (error) {
    console.log("FetchCategories error:", error);
    return [];
  }
}
