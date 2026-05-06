package io.github.guennhatking.libra_auction.services;

import io.github.guennhatking.libra_auction.enums.auction.TrangThaiKiemDuyet;
import io.github.guennhatking.libra_auction.enums.auction.TrangThaiPhien;
import io.github.guennhatking.libra_auction.mappers.AuctionMapper;
import io.github.guennhatking.libra_auction.models.auction.PhienDauGia;
import io.github.guennhatking.libra_auction.models.auction.ThongTinPhienDauGia;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import io.github.guennhatking.libra_auction.models.product.TaiSan;
import io.github.guennhatking.libra_auction.repositories.auction.PhienDauGiaRepository;
import io.github.guennhatking.libra_auction.repositories.person.NguoiDungRepository;
import io.github.guennhatking.libra_auction.repositories.product.TaiSanRepository;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionCreateRequest;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionUpdateRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.AuctionResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
public class AuctionService {
        private final PhienDauGiaRepository phienDauGiaRepository;
        private final AuctionMapper auctionMapper;
        private final TaiSanRepository taiSanRepository;
        private final NguoiDungRepository nguoiDungRepository;

        public AuctionService(PhienDauGiaRepository phienDauGiaRepository,
                        AuctionMapper auctionMapper,
                        TaiSanRepository taiSanRepository,
                        NguoiDungRepository nguoiDungRepository) {
                this.phienDauGiaRepository = phienDauGiaRepository;
                this.auctionMapper = auctionMapper;
                this.taiSanRepository = taiSanRepository;
                this.nguoiDungRepository = nguoiDungRepository;
        }

        @Transactional(readOnly = true)
        public List<AuctionResponse> getAuctions() {
                List<PhienDauGia> phienDauGiaList = phienDauGiaRepository.findAll().stream()
                                .sorted(Comparator.comparing(PhienDauGia::getThoiGianTao,
                                                Comparator.nullsLast(Comparator.reverseOrder())))
                                .toList();
                return auctionMapper.toAuctionResponseList(phienDauGiaList);
        }

        @Transactional(readOnly = true)
        public AuctionResponse getAuctionById(String id) {
                PhienDauGia session = phienDauGiaRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));
                return auctionMapper.toAuctionResponse(session);
        }

        @Transactional(readOnly = true)
        public AuctionResponse getAuctionByIdAndCategory(String id, String categoryId) {
                PhienDauGia session = phienDauGiaRepository
                                .findByIdAndTaiSan_DanhMuc_Id(id, categoryId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Auction not found in this category"));

                return auctionMapper.toAuctionResponse(session);
        }

        @Transactional
        public AuctionResponse createAuction(AuctionCreateRequest request, String userId) {
                TaiSan product = taiSanRepository.findById(request.taiSanId())
                                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                if (product.getThongTinPhienDauGia() != null) {
                        throw new IllegalArgumentException("Product already has an auction session");
                }

                NguoiDung nguoiTao = nguoiDungRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                ThongTinPhienDauGia auctionInfo = new ThongTinPhienDauGia(
                                request.tienCoc(),
                                request.giaKhoiDiem(),
                                request.buocGiaNhoNhat(),
                                product.getTenTaiSan(),
                                product);

                PhienDauGia session = new PhienDauGia(
                                nguoiTao,
                                auctionInfo,
                                request.thoiGianBatDau(),
                                request.giaKhoiDiem(),
                                request.buocGiaNhoNhat());
                session.setTaiSan(product);
                session.setThoiLuong(request.thoiLuong());
                session.setLoaiDauGia(request.loaiDauGia());
                session.setTrangThaiKiemDuyet(TrangThaiKiemDuyet.CHUA_DUYET);
                session.setTrangThaiPhien(TrangThaiPhien.CHUA_BAT_DAU);

                PhienDauGia savedSession = phienDauGiaRepository.save(session);
                product.setThongTinPhienDauGia(savedSession.getThongTinPhienDauGia());

                return auctionMapper.toAuctionResponse(savedSession);
        }

        @Transactional
        public AuctionResponse updateAuction(String id, AuctionUpdateRequest request, String userId) {
                NguoiDung nguoiTao = nguoiDungRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                PhienDauGia session = phienDauGiaRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));

                if (!nguoiTao.getId().equals(session.getNguoiTao().getId())) {
                        throw new AccessDeniedException("You do not have permission to edit this auction.");
                }

                session.setThoiGianBatDau(request.thoiGianBatDau());
                session.setThoiLuong(request.thoiLuong());
                session.setGiaKhoiDiem(request.giaKhoiDiem());
                session.setBuocGiaNhoNhat(request.buocGiaNhoNhat());
                session.setLoaiDauGia(request.loaiDauGia());

                PhienDauGia updatedSession = phienDauGiaRepository.save(session);
                return auctionMapper.toAuctionResponse(updatedSession);
        }

        @Transactional
        public void deleteAuction(String id, String userId) {
                NguoiDung nguoiTao = nguoiDungRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                PhienDauGia session = phienDauGiaRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));

                if (!nguoiTao.getId().equals(session.getNguoiTao().getId())) {
                        throw new AccessDeniedException("Bạn không có quyền xóa phiên đấu giá này");
                }

                phienDauGiaRepository.delete(session);
        }

        // ========== ADMIN APPROVAL METHODS ==========

        @Transactional
        public AuctionResponse approveAuction(String id, String adminId) {
                PhienDauGia session = phienDauGiaRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));

                session.setTrangThaiKiemDuyet(TrangThaiKiemDuyet.DA_DUYET);
                PhienDauGia saved = phienDauGiaRepository.save(session);

                return auctionMapper.toAuctionResponse(saved);
        }

        @Transactional
        public AuctionResponse rejectAuction(String id, String adminId, String reason) {
                PhienDauGia session = phienDauGiaRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));

                session.setTrangThaiKiemDuyet(TrangThaiKiemDuyet.BI_TU_CHOI);
                PhienDauGia saved = phienDauGiaRepository.save(session);

                return auctionMapper.toAuctionResponse(saved);
        }
}
