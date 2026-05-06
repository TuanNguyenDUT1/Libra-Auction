package io.github.guennhatking.libra_auction.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.guennhatking.libra_auction.enums.auction.TrangThaiKiemDuyet;
import io.github.guennhatking.libra_auction.mappers.ProductResponseMapper;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import io.github.guennhatking.libra_auction.models.product.DanhMuc;
import io.github.guennhatking.libra_auction.models.product.HinhAnhTaiSan;
import io.github.guennhatking.libra_auction.models.product.TaiSan;
import io.github.guennhatking.libra_auction.models.product.ThuocTinhTaiSan;
import io.github.guennhatking.libra_auction.repositories.auction.PhienDauGiaRepository;
import io.github.guennhatking.libra_auction.repositories.person.NguoiDungRepository;
import io.github.guennhatking.libra_auction.repositories.product.DanhMucRepository;
import io.github.guennhatking.libra_auction.repositories.product.HinhAnhTaiSanRepository;
import io.github.guennhatking.libra_auction.repositories.product.TaiSanRepository;
import io.github.guennhatking.libra_auction.repositories.product.ThuocTinhTaiSanRepository;
import io.github.guennhatking.libra_auction.viewmodels.request.AttributeRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.ProductCreateRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.ProductUpdateRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.ProductResponse;

@Service
public class ProductService {
    private final DanhMucRepository danhMucRepository;
    private final TaiSanRepository taiSanRepository;
    private final HinhAnhTaiSanRepository hinhAnhTaiSanRepository;
    private final ThuocTinhTaiSanRepository thuocTinhTaiSanRepository;
    private final ProductResponseMapper productResponseMapper;
    private final NguoiDungRepository nguoiDungRepository;

    public ProductService(DanhMucRepository danhMucRepository,
            TaiSanRepository taiSanRepository,
            HinhAnhTaiSanRepository hinhAnhTaiSanRepository,
            PhienDauGiaRepository phienDauGiaRepository,
            ThuocTinhTaiSanRepository thuocTinhTaiSanRepository,
            ProductResponseMapper productResponseMapper,
            NguoiDungRepository nguoiDungRepository) {
        this.danhMucRepository = danhMucRepository;
        this.taiSanRepository = taiSanRepository;
        this.hinhAnhTaiSanRepository = hinhAnhTaiSanRepository;
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
    public ProductResponse createProduct(ProductCreateRequest request, String userId) {
        System.out.println("=== SERVICE START ===");

        NguoiDung nguoiTao = nguoiDungRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        DanhMuc category = danhMucRepository.findById(request.danhMucId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        TaiSan product = new TaiSan(
                request.tenTaiSan(),
                request.soLuong(),
                request.moTa(),
                category);

        product.setNguoiTao(nguoiTao);
        product.setTrangThaiKiemDuyet(TrangThaiKiemDuyet.CHUA_DUYET);
        TaiSan savedProduct = taiSanRepository.save(product);

        // 1. Lưu Attributes
        if (request.attributes() != null) {
            for (AttributeRequest attr : request.attributes()) {
                if (!attr.isSystem()) {
                    ThuocTinhTaiSan entity = new ThuocTinhTaiSan();
                    entity.setTaiSan(savedProduct);
                    entity.setTenThuocTinh(attr.key());
                    entity.setGiaTri(attr.value());
                    thuocTinhTaiSanRepository.save(entity);
                }
            }
        }

        // 2. Lưu Hình ảnh
        if (request.imageUrls() != null && !request.imageUrls().isEmpty()) {
            int order = 0;
            for (String url : request.imageUrls()) {
                HinhAnhTaiSan image = new HinhAnhTaiSan(savedProduct, order++, url);
                hinhAnhTaiSanRepository.save(image);
                System.out.println("Saved Image URL: " + url);
            }
        }

        System.out.println("=== SERVICE DONE ===");
        return productResponseMapper.toProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(String id, ProductUpdateRequest request, String userId) {
        System.out.println("=== UPDATE SERVICE START (URL MODE) ===");

        // 1. Tìm và kiểm tra quyền sở hữu
        TaiSan product = taiSanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!userId.equals(product.getNguoiTao().getId())) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa tài sản này");
        }

        // 2. Tìm category mới
        DanhMuc category = danhMucRepository.findById(request.danhMucId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // 3. Update thông tin cơ bản
        product.setTenTaiSan(request.tenTaiSan());
        product.setSoLuong(request.soLuong());
        product.setMoTa(request.moTa());
        product.setDanhMuc(category);
        TaiSan updatedProduct = taiSanRepository.save(product);

        // 4. XỬ LÝ ATTRIBUTES: Xóa cũ, thêm mới
        thuocTinhTaiSanRepository.deleteByTaiSanId(id);
        if (request.attributes() != null) {
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

        // 5. XỬ LÝ HÌNH ẢNH
        System.out.println("=== UPDATE IMAGES ===");

        hinhAnhTaiSanRepository.deleteByTaiSanId(id);

        List<String> allImagesToSave = new ArrayList<>();
        if (request.imageUrls() != null)
            allImagesToSave.addAll(request.imageUrls());

        int order = 0;
        for (String url : allImagesToSave) {
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
        hinhAnhTaiSanRepository.deleteByTaiSanId(id);
        taiSanRepository.delete(product);
    }

    // ========== ADMIN APPROVAL METHODS ==========

    @Transactional
    public ProductResponse approveProduct(String id, String adminId) {
        TaiSan product = taiSanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setTrangThaiKiemDuyet(TrangThaiKiemDuyet.DA_DUYET);
        TaiSan saved = taiSanRepository.save(product);

        return productResponseMapper.toProductResponse(saved);
    }

    @Transactional
    public ProductResponse rejectProduct(String id, String adminId, String reason) {
        TaiSan product = taiSanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setTrangThaiKiemDuyet(TrangThaiKiemDuyet.BI_TU_CHOI);
        TaiSan saved = taiSanRepository.save(product);

        return productResponseMapper.toProductResponse(saved);
    }
}