'use client';
import { ImageUploadConfig } from "@/types/image_upload_config";

export async function uploadImageToCloudinary(file: File, config: ImageUploadConfig): Promise<string | undefined> {
    const formData = new FormData();

    // 1. Thêm file ảnh thực tế
    formData.append("file", file);

    // 2. Thêm các thông tin bảo mật cơ bản
    formData.append("api_key", config.apiKey); 
    formData.append("signature", config.signature);
    formData.append("timestamp", config.timestamp.toString());

    // 3. Đổ tất cả các params bổ sung từ Backend 
    Object.keys(config.additionalParams).forEach((key) => {
        formData.append(key, String(config.additionalParams[key]));
    });

    try {
        const response = await fetch(config.uploadUrl, {
            method: "POST",
            body: formData,
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error.message || "Upload failed");
        }

        const result = await response.json();
        console.log("Thành công! Link ảnh:", result.secure_url);
        return result.secure_url;
    } catch (error) {
        console.error("Lỗi upload:", error);
    }
}