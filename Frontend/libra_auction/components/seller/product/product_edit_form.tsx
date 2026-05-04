"use client";
import { useState, useRef, useEffect } from "react";
import { Product } from "@/types/product/product";
import { Attribute } from "@/types/product/attribute";
import Image from "next/image";
import { Category } from "@/types/category";
import { fetchCategories } from "@/services/fetch_categories";
import { updateProduct } from "@/services/update_product";
import { NewProduct } from "@/types/product/new-product";
import { fetchImageUploadConfig } from "@/services/fetch_image_upload_config";
import { uploadImageToCloudinary } from "@/services/image_upload_to_cloudinary";

export default function ProductEditForm({ initialData }: { initialData: Product }) {
  // --- State quản lý dữ liệu ---
  const [attributes, setAttributes] = useState<Attribute[]>(initialData.attributes || []);
  const [existingImages, setExistingImages] = useState<string[]>(initialData.images || []);
  const [newImageFiles, setNewImageFiles] = useState<File[]>([]);
  const [newPreviews, setNewPreviews] = useState<string[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState(initialData.category_id || "");

  const fileInputRef = useRef<HTMLInputElement>(null);
  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data: Category[] = await fetchCategories();
        setCategories(data);
      } catch (e) {
        console.error("Fetch categories error:", e);
      }
    };

    loadCategories();
  }, []);

  // --- Logic Thuộc tính ---
  const addAttribute = (isSystem: boolean) => {
    setAttributes([...attributes, { key: "", value: "", isSystem }]);
  };

  const updateAttribute = <K extends keyof Attribute>(index: number, field: K, val: Attribute[K]) => {
    const newAttrs = [...attributes];
    newAttrs[index] = { ...newAttrs[index], [field]: val };
    setAttributes(newAttrs);
  };

  const removeAttribute = (index: number) => {
    setAttributes(attributes.filter((_, i) => i !== index));
  };

  // --- Logic Hình ảnh ---
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const files = Array.from(e.target.files);
      setNewImageFiles((prev) => [...prev, ...files]);
      const previews = files.map((file) => URL.createObjectURL(file));
      setNewPreviews((prev) => [...prev, ...previews]);
    }
  };

  const removeExistingImage = (url: string) => {
    setExistingImages((prev) => prev.filter((img) => img !== url));
  };

  const removeNewImage = (index: number) => {
    setNewImageFiles((prev) => prev.filter((_, i) => i !== index));
    URL.revokeObjectURL(newPreviews[index]);
    setNewPreviews((prev) => prev.filter((_, i) => i !== index));
  };

  const handleSubmit: React.ComponentProps<"form">["onSubmit"] = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const newProduct: NewProduct = {
      tenTaiSan: formData.get("tenTaiSan") as string,
      soLuong: Number(formData.get("soLuong")),
      danhMucId: formData.get("danhMucId") as string,
      moTa: formData.get("moTa") as string,
      attributes: attributes,
      imageUrls: []
    }
    const uploadPromises = newImageFiles.map(async (img) => {
      const imgUploadConfig = await fetchImageUploadConfig("products", img.name);
      return await uploadImageToCloudinary(img, imgUploadConfig);
    });

    const uploadedUrls = await Promise.all(uploadPromises);
    newProduct.imageUrls = existingImages.filter((url): url is string => !!url).concat(uploadedUrls.filter((url): url is string => !!url));

    if (newProduct.imageUrls.length === 0 && newImageFiles.length + existingImages.length > 0) {
      alert("Không thể upload ảnh. Vui lòng thử lại!");
      return;
    }

    const res = await updateProduct(initialData.product_id, newProduct);
    if (res) {
      alert("Chúc mừng! Sản phẩm đã được cập nhật thành công.");
      window.location.replace("/seller-dashboard/products/" + initialData.product_id);
    } else {
      throw new Error("Backend trả về lỗi");
    }
  };
  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white p-8 rounded-2xl border border-[#AFD3E2] space-y-8 shadow-sm"
    >
      <header className="border-b border-[#F6F1F1] pb-4">
        <h2 className="text-2xl font-bold text-[#146C94]">Chỉnh sửa tài sản</h2>
        <p className="text-sm text-gray-400 mt-1">Cập nhật thông tin chi tiết và thuộc tính tài sản.</p>
      </header>

      {/* Thông tin chính */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="flex flex-col gap-2">
          <label className="text-sm font-bold text-[#146C94]">Tên tài sản</label>
          <input
            name="tenTaiSan"
            defaultValue={initialData.product_name}
            required
            className="border border-[#AFD3E2] p-3 rounded-xl focus:ring-2 focus:ring-[#19A7CE] outline-none transition-all"
          />
        </div>
        <div className="flex flex-col gap-2">
          <label className="text-sm font-bold text-[#146C94]">Số lượng</label>
          <input
            type="number"
            name="soLuong"
            defaultValue={initialData.quantity}
            required
            className="border border-[#AFD3E2] p-3 rounded-xl focus:ring-2 focus:ring-[#19A7CE] outline-none transition-all"
          />
        </div>
      </div>
      <div className="flex flex-col gap-2">
        <label className="text-sm font-bold text-[#146C94]">Danh mục</label>
        <select
          name="danhMucId"
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
          required
          className="border border-[#AFD3E2] p-3 rounded-xl focus:ring-2 focus:ring-[#19A7CE]"
        >
          <option value="">-- Chọn danh mục --</option>

          {categories.map((cat) => (
            <option key={cat.id} value={cat.id}>
              {cat.title}
            </option>
          ))}
        </select>
      </div>

      <div className="flex flex-col gap-2">
        <label className="text-sm font-bold text-[#146C94]">Mô tả chi tiết</label>
        <textarea
          name="moTa"
          defaultValue={initialData.description}
          className="border border-[#AFD3E2] p-3 rounded-xl h-32 resize-none outline-none focus:ring-2 focus:ring-[#19A7CE] transition-all"
        />
      </div>

      {/* Quản lý Ảnh */}
      <div className="space-y-4">
        <label className="text-sm font-bold text-[#146C94] block">Hình ảnh tài sản</label>
        <div className="flex flex-wrap gap-4">
          {/* Ảnh cũ */}
          {existingImages.map((url, index) => (
            <div key={`old-${index}`} className="relative w-28 h-28 group animate-in fade-in zoom-in">
              <Image src={url} width={96} height={96} className="w-full h-full object-cover rounded-xl border-2 border-[#19A7CE]" alt="old" />
              <button
                type="button"
                onClick={() => removeExistingImage(url)}
                className="absolute -top-2 -right-2 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center text-[10px] shadow-lg hover:scale-110 transition-transform"
              >✕</button>
            </div>
          ))}

          {/* Ảnh mới chuẩn bị upload */}
          {newPreviews.map((src, index) => (
            <div key={`new-${index}`} className="relative w-28 h-28 group animate-in fade-in slide-in-from-bottom-2">
              <Image src={src} width={96} height={96} className="w-full h-full object-cover rounded-xl border-2 border-dashed border-green-500" alt="new" />
              <button
                type="button"
                onClick={() => removeNewImage(index)}
                className="absolute -top-2 -right-2 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center text-[10px] shadow-lg"
              >✕</button>
              <span className="absolute bottom-1 left-1 bg-green-500 text-white text-[8px] font-bold px-1.5 py-0.5 rounded shadow-sm">ẢNH MỚI</span>
            </div>
          ))}

          {/* Nút thêm ảnh */}
          <button
            type="button"
            onClick={() => fileInputRef.current?.click()}
            className="w-28 h-28 border-2 border-dashed border-[#AFD3E2] rounded-xl flex flex-col items-center justify-center text-[#19A7CE] hover:bg-[#F6F1F1] transition-all"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5"><path d="M12 5v14M5 12h14" /></svg>
            <span className="text-[10px] font-bold mt-1">UPLOAD</span>
          </button>
          <input type="file" ref={fileInputRef} onChange={handleFileChange} multiple className="hidden" accept="image/*" />
        </div>
      </div>

      {/* Quản lý Thuộc tính */}
      <div className="space-y-4 pt-6 border-t border-[#F6F1F1]">
        <div className="flex justify-between items-center">
          <h3 className="font-bold text-[#146C94]">Thông số kỹ thuật</h3>
          <div className="flex gap-2">
            <button type="button" onClick={() => addAttribute(true)} className="text-[10px] font-black bg-[#19A7CE] text-white px-3 py-2 rounded-lg shadow-sm active:scale-95 transition-all">HỆ THỐNG</button>
            <button type="button" onClick={() => addAttribute(false)} className="text-[10px] font-black bg-[#AFD3E2] text-[#146C94] px-3 py-2 rounded-lg shadow-sm active:scale-95 transition-all">TỰ NHẬP</button>
          </div>
        </div>

        <div className="grid grid-cols-1 gap-3">
          {attributes.map((attr, index) => (
            <div key={index} className="flex gap-3 items-center group">
              <input
                placeholder={attr.isSystem ? "Tên thuộc tính (Hệ thống)" : "Tên thuộc tính (Tự nhập)"}
                className={`flex-1 border p-2.5 rounded-xl text-sm outline-none focus:ring-1 focus:ring-[#19A7CE] transition-all ${attr.isSystem ? 'border-[#19A7CE] bg-blue-50/20' : 'border-gray-200'
                  }`}
                value={attr.key}
                onChange={(e) => updateAttribute(index, 'key', e.target.value)}
              />
              <input
                placeholder="Giá trị"
                className="flex-1 border border-gray-200 p-2.5 rounded-xl text-sm outline-none focus:ring-1 focus:ring-[#19A7CE] transition-all"
                value={attr.value}
                onChange={(e) => updateAttribute(index, 'value', e.target.value)}
              />
              <button
                type="button"
                onClick={() => removeAttribute(index)}
                className="p-2 text-red-300 hover:text-red-500 hover:bg-red-50 rounded-full transition-all"
              >
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M18 6 6 18M6 6l12 12" /></svg>
              </button>
            </div>
          ))}
        </div>
      </div>

      <footer className="pt-6">
        <button className="w-full bg-[#146C94] text-white py-4 rounded-xl font-bold text-lg hover:bg-[#19A7CE] shadow-lg shadow-blue-100 transition-all active:scale-[0.99]">
          CẬP NHẬT TÀI SẢN
        </button>
      </footer>
    </form>
  );
}