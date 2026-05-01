import { Auction } from "@/types/auction/auction";
import { UpcomingAuction } from "@/types/auction/upcoming_auction";

export const mapAuctionToUpcoming = (auction: Auction): UpcomingAuction => {
  return {
    id: auction.auction_id,
    
    // Sử dụng ảnh đầu tiên, nếu không có thì dùng ảnh mặc định
    image_src: auction.images && auction.images.length > 0 
      ? auction.images[0] 
      : "/images/placeholder-upcoming.jpg",
    
    title: auction.auction_name,
    
    // Đối với đấu giá sắp diễn ra, ta hiển thị giá khởi điểm
    starting_bid: auction.starting_price,
    
    // Số lượng người đã đăng ký hoặc quan tâm (dựa trên total_participants)
    bidders: auction.total_participants || 0,
    
    // Chuyển đổi sang đối tượng Date nếu start_time đang là string
    starting_date: new Date(auction.start_time),
    
    category_id: auction.category_id
  };
};