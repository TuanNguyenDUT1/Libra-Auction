"use client";

import Link from "next/link";
import { Auction } from "@/types/auction/auction";
import { QuestionList } from "./question_list";

interface AuctionDetailProps {
  data: Auction
}

export const AuctionDetail = ({ data }: AuctionDetailProps) => {
  const isLive = data.auction_status === "DANG_DIEN_RA"

  return (
    <div className="max-w-5xl mx-auto space-y-6">

      <button
        onClick={() => window.location.replace("/seller-dashboard/auctions")}
        className="mb-6 flex items-center gap-2 text-sm font-bold text-gray-500 hover:text-(--primary-color) hover:cursor-pointer transition-colors"
      >
{/* <svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="512" height="512" x="0" y="0" viewBox="0 0 512.001 512.001" className="h-10"><g><path d="M165.005 421.995H45.001C20.188 421.995 0 442.183 0 466.996v30.001c0 8.285 6.716 15 15 15h180.006c8.284 0 15-6.716 15-15v-30.001c.001-24.813-20.187-45.001-45.001-45.001zm15.001 60.002H30.001v-15c0-8.271 6.729-15 15-15h120.004c8.271 0 15 6.729 15 15v15zM486.285 389.685 309.633 245.97l-31.277-31.277 23.039-23.039c16.737 7.972 37.376 5.052 51.212-8.783 17.545-17.546 17.545-46.095-.001-63.641L246.533 13.162c-17.546-17.546-46.095-17.545-63.642 0-13.826 13.827-16.748 34.486-8.781 51.211L64.391 174.091c-16.724-7.967-37.383-5.046-51.211 8.782-17.546 17.546-17.546 46.095 0 63.642l106.07 106.07c17.586 17.586 46.053 17.59 63.642 0 13.827-13.827 16.75-34.486 8.782-51.21l23.039-23.039 31.275 31.274 143.714 176.653c25.624 31.965 73.298 34.504 102.191 5.61 28.906-28.906 26.308-76.602-5.608-102.188zM204.105 34.376c5.85-5.849 15.366-5.848 21.214 0l106.069 106.069c5.848 5.848 5.848 15.365-.002 21.215-5.86 5.861-15.349 5.862-21.213 0L204.105 55.591c-5.849-5.849-5.849-15.366 0-21.215zM161.677 331.37c-5.862 5.861-15.351 5.863-21.214 0L34.395 225.302c-5.849-5.849-5.849-15.365-.001-21.215 5.847-5.847 15.361-5.85 21.212-.003l.002.003 106.069 106.069c5.848 5.85 5.848 15.366 0 21.214zm10.608-53.033-84.856-84.856 106.07-106.069 84.856 84.856c-38.69 38.688-81.335 81.333-106.07 106.069zm63.641-21.215 21.214-21.214 21.215 21.214-21.215 21.214-21.214-21.214zm234.753 213.539c-16.167 16.167-43.024 15.019-57.591-3.189l-.077-.096L277.32 300.585l23.283-23.284 166.791 135.692.096.077c17.893 14.316 19.644 41.136 3.189 57.591z" fill="#000000" opacity="1" data-original="#000000"></path></g></svg>        Danh sách tài sản */}
      </button>

      {/* Thông tin phiên đấu giá */}
      <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100">
        <div className="flex flex-col md:flex-row justify-between gap-6">

          {/* LEFT */}
          <div className="space-y-4 flex-1">
            <div>
              <span className="text-xs font-semibold text-(--primary-color) uppercase tracking-wide">
                Chi tiết phiên
              </span>

              <h1 className="text-xl md:text-2xl font-bold text-gray-800 mt-1">
                {data.auction_name}
              </h1>

              {/* trạng thái */}
              <p className="text-sm mt-1 font-medium">
                Trạng thái:{" "}
                <span
                  className={`${isLive
                    ? "text-green-600"
                    : data.auction_status === "CHUA_BAT_DAU"
                      ? "text-blue-500"
                      : data.auction_status === "DA_KET_THUC"
                        ? "text-gray-500"
                        : "text-red-500"
                    }`}
                >
                  {data.auction_status}
                </span>
              </p>
            </div>

            <p className="text-gray-600 text-sm leading-relaxed max-w-2xl">
              {data.description}
            </p>

            <div className="flex gap-8 border-t border-gray-50 pt-4">
              <div>
                <p className="text-xs text-gray-400 uppercase font-semibold">
                  Giá khởi điểm
                </p>
                <p className="text-lg font-bold text-(--secondary-color)">
                  {data.starting_price.toLocaleString()} VND
                </p>
              </div>

              <div>
                <p className="text-xs text-gray-400 uppercase font-semibold">
                  Mã phiên
                </p>
                <p className="text-sm font-mono mt-1 font-medium">
                  #{data.auction_id}
                </p>
              </div>
            </div>
          </div>

          {/* RIGHT */}
          <div className="md:w-64 space-y-3">
            {isLive ? (
              <Link
                href={`/seller-dashboard/auctions/${data.auction_id}/live`}
                className="block text-center w-full bg-green-600 text-white font-semibold py-2.5 rounded-xl hover:bg-green-700 transition-all active:scale-95 shadow"
              >
                Xem trực tiếp
              </Link>
            ) : (
              <>
                <Link
                  href={`/seller-dashboard/auctions/${data.auction_id}/edit`}
                  className="block text-center w-full bg-(--primary-color) text-white font-semibold py-2.5 rounded-xl hover:bg-(--secondary-color) transition-all active:scale-95 shadow"
                >
                  Chỉnh sửa
                </Link>

                <Link
                  href={`/seller-dashboard/auctions/${data.auction_id}/delete`}
                  className="block text-center w-full bg-red-50 text-red-600 font-semibold py-2.5 rounded-xl border border-red-200 hover:bg-red-100 transition-all"
                >
                  Xoá phiên
                </Link>
              </>
            )}
          </div>
        </div>
      </div>

      {/* Question & Answer */}
      {/* TODO: fix question list */}
      {/* <div className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100">
        <QuestionList questions={data.questions} />
      </div> */}
    </div>
  );
};