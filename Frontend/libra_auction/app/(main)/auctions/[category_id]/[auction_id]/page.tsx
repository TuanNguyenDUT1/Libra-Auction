import AuctionInfoDetailsSection from "@/components/main/auction/auction_info_details_section";
import BreadCrumb from "@/components/main/auction/breadcrumb";
import { fetchPublicAuction } from "@/services/fetch_public_auction";
import { Auction } from "@/types/auction/auction";
import { notFound } from "next/navigation";
import AuctionInfoSection from "@/components/main/auction/auction_info_section";

export default async function page(props: {
  params: Promise<{ category_id: string; auction_id: string }>;
}) {
  const params = await props.params;
  const id = params.auction_id;
  const auction_info: Auction = await fetchPublicAuction(id);
  if (auction_info == null) {
    notFound();
  }
  const breadcrumb_items = [];
  breadcrumb_items.push({
    id: auction_info.category_id,
    value: auction_info.category_name,
    href: `/auctions/${auction_info.category_id}`,
  });
  breadcrumb_items.push({
    id: auction_info.auction_id,
    value: auction_info.auction_name,
    href: `/auctions/${auction_info.category_id}/${auction_info.auction_id}`,
  });
  return (
    <>
      <BreadCrumb breadcrumbItems={breadcrumb_items} />
      <AuctionInfoSection autionInfos={auction_info} />
      <AuctionInfoDetailsSection autionInfos={auction_info} />
    </>
  );
}
