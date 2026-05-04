package io.github.guennhatking.libra_auction.controllers;

import io.github.guennhatking.libra_auction.services.ImageUploadService;
import io.github.guennhatking.libra_auction.viewmodels.request.ImageRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.ImageUploadResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.ServerAPIResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/images")
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/generate-upload-url")
    public ResponseEntity<ServerAPIResponse<ImageUploadResponse>> getUploadUrl(@RequestBody ImageRequest request) {
        ImageUploadResponse response = imageUploadService.createSignedUploadUrl(request);
        return ResponseEntity.ok(ServerAPIResponse.success(response));
    }
}