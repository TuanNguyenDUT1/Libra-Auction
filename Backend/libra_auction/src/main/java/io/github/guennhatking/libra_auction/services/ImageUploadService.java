package io.github.guennhatking.libra_auction.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import io.github.guennhatking.libra_auction.viewmodels.request.ImageRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.ImageUploadResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.ImageUploadedResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ImageUploadService {
    private final Cloudinary cloudinary;

    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public ImageUploadedResponse uploadImage(MultipartFile file, String folder) throws Exception {
        validateImage(file);

        Map<String, Object> uploadOptions = new LinkedHashMap<>();
        uploadOptions.put("resource_type", "image");
        uploadOptions.put("use_filename", false);
        uploadOptions.put("unique_filename", false);
        uploadOptions.put("overwrite", true);

        if (folder != null && !folder.isBlank()) {
            uploadOptions.put("folder", normalizeFolder(folder));
        }

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOptions);
        String publicId = String.valueOf(uploadResult.get("public_id"));

        Map<?, ?> assetDetails = cloudinary.api().resource(publicId, ObjectUtils.asMap(
                "quality_analysis", true));

        Transformation<?> transformation = new Transformation<>()
                .crop("pad")
                .width(300)
                .height(400)
                .background("auto:predominant");

        String transformedUrl = cloudinary.url()
                .transformation(transformation)
                .generate(publicId);

        String transformedImageTag = cloudinary.url()
                .transformation(transformation)
                .imageTag(publicId);

        return new ImageUploadedResponse(
                publicId,
                file.getOriginalFilename(),
                stringValue(uploadResult.get("format")),
                stringValue(uploadResult.get("resource_type")),
                stringValue(uploadResult.get("secure_url")),
                integerValue(uploadResult.get("width")),
                integerValue(uploadResult.get("height")),
                longValue(uploadResult.get("bytes")),
                transformedUrl,
                transformedImageTag,
                toStringObjectMap(assetDetails));
    }

    public ImageUploadedResponse uploadImageFromUrl(String imageUrl, String folder) throws Exception {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("Image URL is required");
        }

        Map<String, Object> uploadOptions = new LinkedHashMap<>();
        uploadOptions.put("resource_type", "image");
        uploadOptions.put("use_filename", false);
        uploadOptions.put("unique_filename", false);
        uploadOptions.put("overwrite", true);

        if (folder != null && !folder.isBlank()) {
            uploadOptions.put("folder", normalizeFolder(folder));
        }

        // Upload trực tiếp từ URL
        Map<?, ?> uploadResult = cloudinary.uploader().upload(imageUrl, uploadOptions);

        String publicId = String.valueOf(uploadResult.get("public_id"));

        Map<?, ?> assetDetails = cloudinary.api().resource(publicId, ObjectUtils.asMap(
                "quality_analysis", true));

        Transformation<?> transformation = new Transformation<>()
                .crop("pad")
                .width(300)
                .height(400)
                .background("auto:predominant");

        String transformedUrl = cloudinary.url()
                .transformation(transformation)
                .generate(publicId);

        String transformedImageTag = cloudinary.url()
                .transformation(transformation)
                .imageTag(publicId);

        return new ImageUploadedResponse(
                publicId,
                imageUrl,
                stringValue(uploadResult.get("format")),
                stringValue(uploadResult.get("resource_type")),
                stringValue(uploadResult.get("secure_url")),
                integerValue(uploadResult.get("width")),
                integerValue(uploadResult.get("height")),
                longValue(uploadResult.get("bytes")),
                transformedUrl,
                transformedImageTag,
                toStringObjectMap(assetDetails));
    }

    public ImageUploadResponse createSignedUploadUrl(ImageRequest request) {
        // 1. Chuẩn bị timestamp
        long timestamp = System.currentTimeMillis() / 1000L;

        // 2. Tạo tập hợp các tham số cần ký
        Map<String, Object> paramsToSign = new LinkedHashMap<>();

        // Xử lý folder
        String normalizedFolder = null;
        if (request.folder() != null && !request.folder().isBlank()) {
            normalizedFolder = normalizeFolder(request.folder());
            paramsToSign.put("folder", normalizedFolder);
        }

        // Xử lý public_id (fileName)
        if (request.fileName() != null && !request.fileName().isBlank()) {
            paramsToSign.put("public_id", request.fileName());
        }

        paramsToSign.put("timestamp", timestamp);

        // 3. Tạo chữ ký bảo mật từ API Secret
        String signature = cloudinary.apiSignRequest(paramsToSign, cloudinary.config.apiSecret);

        // 4. Chuẩn bị URL upload
        String uploadUrl = cloudinary.cloudinaryApiUrl("upload", ObjectUtils.asMap("resource_type", "image"));
        
        // 5. Trả về Response cho Client
        return new ImageUploadResponse(
                uploadUrl,
                request.fileName(), // publicId dự kiến
                cloudinary.config.apiKey,
                timestamp,
                signature,
                paramsToSign // Gửi kèm các params để Client đóng gói vào FormData
        );
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String contentType = file.getContentType();
        if (contentType != null && contentType.startsWith("image/")) {
            return;
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String normalizedFilename = originalFilename.trim().toLowerCase();
            if (normalizedFilename.endsWith(".png")
                    || normalizedFilename.endsWith(".jpg")
                    || normalizedFilename.endsWith(".jpeg")
                    || normalizedFilename.endsWith(".gif")
                    || normalizedFilename.endsWith(".webp")
                    || normalizedFilename.endsWith(".bmp")) {
                return;
            }
        }

        if ("application/octet-stream".equalsIgnoreCase(contentType)) {
            throw new IllegalArgumentException("Unsupported image file extension");
        }

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are supported");
        }
    }

    private String normalizeFolder(String folder) {
        return folder.trim().replace('\\', '/');
    }

    private String stringValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private Integer integerValue(Object value) {
        return value instanceof Number number ? number.intValue() : null;
    }

    private Long longValue(Object value) {
        return value instanceof Number number ? number.longValue() : null;
    }

    private Map<String, Object> toStringObjectMap(Map<?, ?> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<?, ?> entry : source.entrySet()) {
            result.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        return result;
    }
}