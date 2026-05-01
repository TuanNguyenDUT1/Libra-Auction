import Image from "next/image";
import HomeIcon from "@/public/home.png";
import ChevronIcon from "@/public/chevron.png";
import { BreadcrumbItem } from "@/types/breadcrumb_item";
import Link from "next/link";
export default function BreadCrumb({
  breadcrumbItems,
}: {
  breadcrumbItems: BreadcrumbItem[];
}) {
  return (
    <div className="flex w-full items-center bg-(--secondary-color)/88 px-16 py-4">
      <Link href="/" className="grid grid-cols-1 grid-rows-1 size-8">
        <div className="col-start-1 row-start-1 bg-(--primary-color) rounded-full size-full"></div>
        <div className="col-start-1 row-start-1 flex items-center justify-center">
          <Image src={HomeIcon} alt="" className="size-4" />
        </div>
      </Link>
      {breadcrumbItems.map((item) => {
        return (
          <div key={item.id} className="flex items-center">
            <Image src={ChevronIcon} alt="" className="size-8" />
            <Link href={item.href} className="text-white text-lg font-bold truncate max-w-80">
              {item.value}
            </Link>
          </div>
        );
      })}
    </div>
  );
}
