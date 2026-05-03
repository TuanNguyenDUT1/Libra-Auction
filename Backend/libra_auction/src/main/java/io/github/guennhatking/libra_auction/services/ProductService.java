package io.github.guennhatking.libra_auction.services;

import io.github.guennhatking.libra_auction.mappers.ProductResponseMapper;
import io.github.guennhatking.libra_auction.models.product.DanhMuc;
import io.github.guennhatking.libra_auction.models.product.HinhAnhTaiSan;
import io.github.guennhatking.libra_auction.models.product.TaiSan;
import io.github.guennhatking.libra_auction.models.product.ThuocTinhTaiSan;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import io.github.guennhatking.libra_auction.repositories.person.NguoiDungRepository;
import io.github.guennhatking.libra_auction.repositories.auction.PhienDauGiaRepository;
import io.github.guennhatking.libra_auction.repositories.product.DanhMucRepository;
import io.github.guennhatking.libra_auction.repositories.product.HinhAnhTaiSanRepository;
import io.github.guennhatking.libra_auction.repositories.product.TaiSanRepository;
import io.github.guennhatking.libra_auction.repositories.product.ThuocTinhTaiSanRepository;
import io.github.guennhatking.libra_auction.viewmodels.request.AttributeRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.ProductCreateRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.ProductUpdateRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.ImageUploadResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final DanhMucRepository danhMucRepository;
    private final TaiSanRepository taiSanRepository;
    private final HinhAnhTaiSanRepository hinhAnhTaiSanRepository;
    private final ImageUploadService imageUploadService;
    private final ThuocTinhTaiSanRepository thuocTinhTaiSanRepository;
    private final ProductResponseMapper productResponseMapper;
    private final NguoiDungRepository nguoiDungRepository; 

    public ProductService(DanhMucRepository danhMucRepository,
            TaiSanRepository taiSanRepository,
            HinhAnhTaiSanRepository hinhAnhTaiSanRepository,
            PhienDauGiaRepository phienDauGiaRepository,
            ImageUploadService imageUploadService,
            ThuocTinhTaiSanRepository thuocTinhTaiSanRepository,
            ProductResponseMapper productResponseMapper
            , NguoiDungRepository nguoiDungRepository) {
        this.danhMucRepository = danhMucRepository;
        this.taiSanRepository = taiSanRepository;
        this.hinhAnhTaiSanRepository = hinhAnhTaiSanRepository;
        this.imageUploadService = imageUploadService;
        this.thuocTinhTaiSanRepository = thuocTinhTaiSanRepository;
        this.productResponseMapper = productResponseMapper;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        List<TaiSan> taiSanList = taiSanRepository.findAll().stream().toList();
        return productResponseMapper.toProductResponseList(taiSanList);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(String id) {
        TaiSan product = taiSanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return productResponseMapper.toProductResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request, List<MultipartFile> images, String userId) {
        System.out.println("=== SERVICE START ===");

        NguoiDung nguoiTao = nguoiDungRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        System.out.println("Finding category with id: " + request.danhMucId());

        DanhMuc category = danhMucRepository.findById(request.danhMucId())
                .orElseThrow(() -> {
                    System.out.println("Category NOT FOUND");
                    return new IllegalArgumentException("Category not found");
                });

        System.out.println("Category FOUND: " + category.getId());

        TaiSan product = new TaiSan(
                request.tenTaiSan(),
                request.soLuong(),
                request.moTa(),
                category);
                
        product.setNguoiTao(nguoiTao);

        TaiSan savedProduct = taiSanRepository.save(product);
        System.out.println("Saved product ID: " + savedProduct.getId());

        if (request.attributes() != null && !request.attributes().isEmpty()) {

            for (AttributeRequest attr : request.attributes()) {

                System.out.println("Attribute: " + attr.key() + " = " + attr.value());

                // chỉ lưu attribute thường
                if (!attr.isSystem()) {

                    ThuocTinhTaiSan entity = new ThuocTinhTaiSan();

                    entity.setTaiSan(savedProduct);
                    entity.setTenThuocTinh(attr.key());
                    entity.setGiaTri(attr.value());

                    thuocTinhTaiSanRepository.save(entity);
                } else {
                    System.out.println("SYSTEM ATTRIBUTE SKIPPED: " + attr.key());
                }
            }
        }

        for (MultipartFile file : images) {
            System.out.println("=== IMAGE DEBUG ===");
            System.out.println("Name: " + file.getName()); // key trong form-data
            System.out.println("Original filename: " + file.getOriginalFilename());
            System.out.println("Content type: " + file.getContentType());
            System.out.println("Size: " + file.getSize());
        }
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : images) {
            System.out.println("Uploading: " + file.getOriginalFilename());

            ImageUploadResponse res = null;
            try {
                res = imageUploadService.uploadImage(file, "products");
            } catch (Exception e) {
                System.out.println("Image upload failed for: " + file.getOriginalFilename());
                e.printStackTrace();
            }

            if (res != null) {
                System.out.println("Uploaded URL: " + res.secureUrl());
                imageUrls.add(res.secureUrl());
            }
        }
        int order = 0;
        for (String url : imageUrls) {
            HinhAnhTaiSan image = new HinhAnhTaiSan(savedProduct, order++, url);
            hinhAnhTaiSanRepository.save(image);
        }

        System.out.println("=== SERVICE DONE ===");

        return productResponseMapper.toProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(String id, ProductUpdateRequest request, List<MultipartFile> images, String userId) {
        System.out.println("=== UPDATE SERVICE START ===");

        // --- 1. Tìm product ---
        TaiSan product = taiSanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!userId.equals(product.getNguoiTao().getId())) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa tài sản này");
        }

        // --- 2. Tìm category ---
        DanhMuc category = danhMucRepository.findById(request.danhMucId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // --- 3. Update thông tin cơ bản ---
        product.setTenTaiSan(request.tenTaiSan());
        product.setSoLuong(request.soLuong());
        product.setMoTa(request.moTa());
        product.setDanhMuc(category);

        TaiSan updatedProduct = taiSanRepository.save(product);

        // --- 4. XỬ LÝ ATTRIBUTES ---
        System.out.println("=== UPDATE ATTRIBUTES ===");

        // Xóa toàn bộ attribute cũ
        thuocTinhTaiSanRepository.deleteByTaiSanId(id);

        // Thêm lại
        if (request.attributes() != null && !request.attributes().isEmpty()) {
            for (AttributeRequest attr : request.attributes()) {

                if (!attr.isSystem()) {
                    ThuocTinhTaiSan entity = new ThuocTinhTaiSan();
                    entity.setTaiSan(updatedProduct);
                    entity.setTenThuocTinh(attr.key());
                    entity.setGiaTri(attr.value());

                    thuocTinhTaiSanRepository.save(entity);
                }
            }
        }

        // --- 5. XỬ LÝ IMAGES ---
        System.out.println("=== UPDATE IMAGES ===");

        List<String> finalImageUrls = new ArrayList<>();

        // --- 5.1 giữ lại ảnh cũ ---
        if (request.existingImages() != null) {
            System.out.println("Keeping existing images: " + request.existingImages().size());
            finalImageUrls.addAll(request.existingImages());
        }

        // --- 5.2 upload ảnh mới ---
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                try {
                    System.out.println("Uploading new image: " + file.getOriginalFilename());

                    ImageUploadResponse res = imageUploadService.uploadImage(file, "products");

                    if (res != null) {
                        finalImageUrls.add(res.secureUrl());
                    }

                } catch (Exception e) {
                    System.out.println("Upload failed: " + file.getOriginalFilename());
                    e.printStackTrace();
                }
            }
        }

        // --- 5.3 Xóa toàn bộ ảnh cũ DB ---
        hinhAnhTaiSanRepository.findByTaiSanIdOrderByThuTuHienThiAsc(id)
                .forEach(hinhAnhTaiSanRepository::delete);

        // --- 5.4 Lưu lại toàn bộ ảnh mới ---
        int order = 0;
        for (String url : finalImageUrls) {
            HinhAnhTaiSan image = new HinhAnhTaiSan(updatedProduct, order++, url);
            hinhAnhTaiSanRepository.save(image);
        }

        System.out.println("=== UPDATE DONE ===");

        return productResponseMapper.toProductResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(String id, String userId) {
        TaiSan product = taiSanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!userId.equals(product.getNguoiTao().getId())) {
            throw new AccessDeniedException("Bạn không có quyền xóa tài sản này");
        }

        taiSanRepository.delete(product);
    }
}