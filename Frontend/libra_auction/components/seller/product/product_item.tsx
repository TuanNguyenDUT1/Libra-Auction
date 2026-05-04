import { Product } from "@/types/product/product";

interface ProductItemProps {
  product: Product;
  onView: (id: string) => void;
  onEdit: (id: string) => void;
  onDelete: (id: string) => void;
}

export const ProductItem = ({ product, onView, onEdit, onDelete }: ProductItemProps) => {
  return (
    <div className="bg-white p-4 rounded-2xl border border-gray-100 hover:border-(--accent-color) hover:shadow-sm transition-all flex items-center justify-between group">
      <div className="flex items-center gap-4">
        {/* Placeholder Icon cho Product */}
        <div className="h-12 w-12 bg-(--background-color) rounded-xl flex items-center justify-center text-(--secondary-color)">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="m7.5 4.27 9 5.15"/><path d="M21 8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16Z"/><path d="m3.3 7 8.7 5 8.7-5"/><path d="M12 22V12"/></svg>
        </div>
        
        <div className="flex flex-col">
          <h3 className="font-bold text-gray-800 text-base">{product.product_name}</h3>
          <div className="flex flex-wrap items-center gap-x-4 gap-y-1 mt-1">
            <span className="text-xs font-medium text-(--secondary-color) bg-(--accent-color) bg-opacity-30 px-2 py-0.5 rounded-lg">
              {product.category_name}
            </span>
            <span className="text-sm text-gray-500">
              Số lượng: <b className="text-gray-900">{product.quantity}</b>
            </span>
          </div>
        </div>
      </div>

      <div className="flex items-center gap-1">
        {/* View Action */}
        <button 
          onClick={() => onView(product.product_id)}
          className="p-2 text-gray-400 hover:text-(--primary-color) hover:bg-blue-50 rounded-lg transition-colors"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
        </button>
        
        {/* Edit Action */}
        <button 
          onClick={() => onEdit(product.product_id)}
          className="p-2 text-gray-400 hover:text-amber-600 hover:bg-amber-50 rounded-lg transition-colors"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"/><path d="m15 5 4 4"/></svg>
        </button>

        {/* Delete Action */}
        <button 
          onClick={() => onDelete(product.product_id)}
          className="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/><line x1="10" x2="10" y1="11" y2="17"/><line x1="14" x2="14" y1="11" y2="17"/></svg>
        </button>
      </div>
    </div>
  );
};