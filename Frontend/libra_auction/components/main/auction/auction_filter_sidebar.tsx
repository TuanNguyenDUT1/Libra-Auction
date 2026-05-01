export const AuctionFilterSidebar = () => {
    return (
        <div className="space-y-8">
            <div>
                <h3 className="font-bold text-[#146C94] mb-4 uppercase text-xs tracking-widest">Tìm kiếm</h3>
                <div className="relative">
                    <input 
                        type="text" 
                        placeholder="Tên phiên, tài sản..."
                        className="w-full pl-3 pr-3 py-2 bg-gray-50 border border-gray-100 rounded-xl text-sm outline-none focus:border-[var(--primary-color)] transition-all"
                    />
                </div>
            </div>

            <div>
                <h3 className="font-bold text-[#146C94] mb-4 uppercase text-xs tracking-widest">Danh mục</h3>
                <div className="flex flex-col gap-3">
                    {["Bất động sản", "Xe cộ", "Đồ điện tử", "Nghệ thuật", "Trang sức"].map((cat) => (
                        <label key={cat} className="flex items-center gap-3 cursor-pointer group">
                            <input type="checkbox" className="w-4 h-4 rounded border-gray-300 text-[var(--primary-color)] focus:ring-[var(--primary-color)]" />
                            <span className="text-sm text-gray-600 group-hover:text-[var(--primary-color)] transition-colors">{cat}</span>
                        </label>
                    ))}
                </div>
            </div>

            <div>
                <h3 className="font-bold text-[#146C94] mb-4 uppercase text-xs tracking-widest">Khoảng giá (VND)</h3>
                <div className="grid grid-cols-2 gap-2">
                    <input type="number" placeholder="Từ" className="w-full p-2 bg-gray-50 border border-gray-100 rounded-lg text-xs outline-none" />
                    <input type="number" placeholder="Đến" className="w-full p-2 bg-gray-50 border border-gray-100 rounded-lg text-xs outline-none" />
                </div>
            </div>

            <div>
                <h3 className="font-bold text-[#146C94] mb-4 uppercase text-xs tracking-widest">Trạng thái</h3>
                <div className="flex flex-wrap gap-2">
                    {["Tất cả", "Đang diễn ra", "Sắp tới"].map((status) => (
                        <button key={status} className="px-3 py-1.5 text-[10px] font-bold border border-gray-200 rounded-full hover:bg-[var(--primary-color)] hover:text-white transition-all">
                            {status.toUpperCase()}
                        </button>
                    ))}
                </div>
            </div>

            <button className="w-full py-3 bg-[var(--secondary-color)] text-white font-bold rounded-xl text-sm shadow-lg shadow-blue-100 active:scale-[0.98] transition-all">
                ÁP DỤNG BỘ LỌC
            </button>
        </div>
    );
};