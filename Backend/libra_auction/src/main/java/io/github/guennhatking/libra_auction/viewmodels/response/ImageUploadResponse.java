package io.github.guennhatking.libra_auction.viewmodels.response;

import java.util.Map;

public record ImageUploadResponse(
        String uploadUrl,      // URL dùng để upload
        String publicId,       // ID dự kiến của ảnh
        String apiKey,
        long timestamp,        // Thời gian tạo request
        String signature,      
        Map<String, Object> additionalParams
) {}