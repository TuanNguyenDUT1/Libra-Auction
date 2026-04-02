import Link from "next/link";

export default function ShowMoreBtn() {
    return <Link href="/auctions" className="bg-white text-(--primary-color) border border-(--primary-color) text-xl px-6 py-2">
        Show More
    </Link>
}