import { FetchAuctionInfo } from "@/services/fetch_auction_info";
import BreadCrumb from "@/components/breadcrumb";
import { notFound } from "next/navigation";
import AuctionInfoSection from "@/components/auction_info_section";
export default async function page(props: {
  params: Promise<{ category_id: string; auction_id: string }>;
}) {
  const params = await props.params;
  const id = params.auction_id;
  const auction_info = FetchAuctionInfo(id);
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
    </>
  );
}
