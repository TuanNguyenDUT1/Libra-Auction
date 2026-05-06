package io.github.guennhatking.libra_auction.services;

import io.github.guennhatking.libra_auction.mappers.ProductResponseMapper;
import io.github.guennhatking.libra_auction.models.product.TaiSan;
import io.github.guennhatking.libra_auction.repositories.product.TaiSanRepository;
import io.github.guennhatking.libra_auction.viewmodels.request.ProductSearchRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.PageResponse;
import io.github.guennhatking.libra_auction.viewmodels.response.ProductResponse;
import io.github.guennhatking.libra_auction.enums.auction.TrangThaiKiemDuyet;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchService {
    private final TaiSanRepository taiSanRepository;
    private final ProductResponseMapper productResponseMapper;

    public ProductSearchService(TaiSanRepository taiSanRepository,
                                ProductResponseMapper productResponseMapper) {
        this.taiSanRepository = taiSanRepository;
        this.productResponseMapper = productResponseMapper;
    }

    public PageResponse<ProductResponse> searchProducts(ProductSearchRequest criteria) {
        // Get all products
        List<TaiSan> allProducts = taiSanRepository.findAll();

        // Apply filters
        List<TaiSan> filtered = applyFilters(allProducts, criteria);

        // Apply sorting
        filtered = applySort(filtered, criteria);

        // Apply pagination
        return applyPagination(filtered, criteria);
    }

    private List<TaiSan> applyFilters(List<TaiSan> products, ProductSearchRequest criteria) {
        return products.stream()
                .filter(product -> filterByName(product, criteria.name()))
                .filter(product -> filterByCategory(product, criteria.categoryId()))
                .filter(product -> filterByAttributes(product, criteria.attributes()))
                .filter(product -> filterByCreator(product, criteria.nguoiTaoId()))
                .filter(product -> filterByApprovalStatus(product, criteria.trangThaiKiemDuyet()))
                .collect(Collectors.toList());
    }

    private boolean filterByName(TaiSan product, String name) {
        if (name == null || name.isBlank()) {
            return true;
        }
        return product.getTenTaiSan() != null &&
                product.getTenTaiSan().toLowerCase().contains(name.toLowerCase());
    }

    private boolean filterByCategory(TaiSan product, String categoryId) {
        if (categoryId == null || categoryId.isBlank()) {
            return true;
        }
        return product.getDanhMuc() != null &&
                product.getDanhMuc().getId().equals(categoryId);
    }

    private boolean filterByAttributes(TaiSan product, List<java.util.Map<String, String>> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return true;
        }

        // Nếu sản phẩm không có thuộc tính thì bỏ qua
        if (product.getThuocTinhTaiSanList() == null || product.getThuocTinhTaiSanList().isEmpty()) {
            return false;
        }

        // Check if all attribute filters match
        return attributes.stream().allMatch(filterAttr -> {
            String attrName = filterAttr.get("attribute_name");
            String attrValue = filterAttr.get("attribute_value");

            return product.getThuocTinhTaiSanList().stream()
                    .anyMatch(taiSanAttr -> taiSanAttr.getTenThuocTinh() != null &&
                            taiSanAttr.getTenThuocTinh().equals(attrName) &&
                            taiSanAttr.getGiaTri() != null &&
                            taiSanAttr.getGiaTri().equals(attrValue));
        });
    }

    private boolean filterByCreator(TaiSan product, String nguoiTaoId) {
        if (nguoiTaoId == null || nguoiTaoId.isBlank()) {
            return true; // no creator filter
        }
        if (product.getNguoiTao() == null) {
            return false;
        }
        return nguoiTaoId.equals(product.getNguoiTao().getId());
    }

    private boolean filterByApprovalStatus(TaiSan product, String trangThaiKiemDuyet) {
        if (trangThaiKiemDuyet == null || trangThaiKiemDuyet.isBlank()) {
            // If no approval filter is specified, only show approved products by default
            return product.getTrangThaiKiemDuyet() != null && 
                   product.getTrangThaiKiemDuyet().equals(TrangThaiKiemDuyet.DA_DUYET);
        }
        if (product.getTrangThaiKiemDuyet() == null) {
            return false;
        }
        return product.getTrangThaiKiemDuyet().toString().equals(trangThaiKiemDuyet);
    }

    private List<TaiSan> applySort(List<TaiSan> products, ProductSearchRequest criteria) {
        String sortBy = criteria.sortBy() != null ? criteria.sortBy() : "tenTaiSan";
        boolean isAsc = "ASC".equalsIgnoreCase(criteria.sortOrder());

        Comparator<TaiSan> comparator = switch (sortBy) {
            case "soLuong" -> Comparator.comparing(TaiSan::getSoLuong);
            // Mặc định sắp xếp theo tên tài sản
            case "tenTaiSan" -> Comparator.comparing(t -> t.getTenTaiSan() != null ? t.getTenTaiSan() : "");
            default -> Comparator.comparing(t -> t.getTenTaiSan() != null ? t.getTenTaiSan() : "");
        };

        if (!isAsc) {
            comparator = comparator.reversed();
        }

        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private PageResponse<ProductResponse> applyPagination(List<TaiSan> products,
                                                          ProductSearchRequest criteria) {
        int page = criteria.page() != null ? criteria.page() : 0;
        int pageSize = criteria.pageSize() != null ? criteria.pageSize() : 20;

        int totalElements = products.size();
        int totalPages = (totalElements + pageSize - 1) / pageSize;

        int startIndex = Math.min(page * pageSize, totalElements);
        int endIndex = Math.min(startIndex + pageSize, totalElements);

        List<TaiSan> pageContent = products.subList(startIndex, endIndex);
        List<ProductResponse> responseContent = productResponseMapper.toProductResponseList(pageContent);

        return new PageResponse<>(
                responseContent,
                totalPages,
                (long) totalElements,
                page,
                pageSize,
                page == 0,
                page == totalPages - 1);
    }
}