// import { stats, recentAuctions } from "@/data/data";

export default function page() {
  return (
    <div className="space-y-8">
      {/* 4 Cards Thống kê */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {/* {stats.map((item) => (
          <div key={item.label} className="p-5 bg-white border border-[#AFD3E2] rounded-lg">
            <p className="text-sm text-gray-500 font-medium">{item.label}</p>
            <div className="flex items-baseline gap-2 mt-2">
              <span className="text-2xl font-bold text-[#146C94]">{item.value}</span>
              <span className="text-xs text-green-500 font-semibold">{item.change}</span>
            </div>
          </div>
        ))} */}
      </div>

      {/* Mock Chart Section */}
      <div className="p-6 bg-white border border-[#AFD3E2] rounded-lg">
        <h2 className="text-lg font-bold text-[#146C94] mb-6">Revenue Growth (Last 7 Days)</h2>
        <div className="flex items-end gap-4 h-40 w-full px-2">
          {[40, 70, 45, 90, 65, 80, 95].map((height, i) => (
            <div key={i} className="flex-1 flex flex-col items-center gap-2">
              <div 
                className="w-full bg-[#19A7CE] rounded-t-sm transition-all hover:bg-[#146C94]" 
                style={{ height: `${height}%` }}
              ></div>
              <span className="text-[10px] text-gray-400">Day {i + 1}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Recent Auctions Table */}
      <div className="bg-white border border-[#AFD3E2] rounded-lg overflow-hidden">
        <div className="p-4 border-b border-[#AFD3E2] bg-gray-50">
          <h2 className="font-bold text-[#146C94]">Recent Auctions</h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="text-sm text-gray-500 uppercase">
                <th className="p-4 font-semibold">Auction Name</th>
                <th className="p-4 font-semibold">Current Price</th>
                <th className="p-4 font-semibold">Status</th>
                <th className="p-4 font-semibold">Participants</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-[#AFD3E2]">
              {/* {recentAuctions.map((auction) => (
                <tr key={auction.id} className="hover:bg-[#F6F1F1] transition-colors">
                  <td className="p-4 font-medium">{auction.name}</td>
                  <td className="p-4 text-[#146C94] font-semibold">{auction.price}</td>
                  <td className="p-4">
                    <span className={`px-2 py-1 text-xs rounded-full ${
                      auction.status === 'Active' ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-600'
                    }`}>
                      {auction.status}
                    </span>
                  </td>
                  <td className="p-4 text-gray-600">{auction.bids} bids</td>
                </tr>
              ))} */}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}