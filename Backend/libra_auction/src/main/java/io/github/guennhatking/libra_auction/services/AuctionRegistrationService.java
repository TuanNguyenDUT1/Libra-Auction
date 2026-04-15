package io.github.guennhatking.libra_auction.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.guennhatking.libra_auction.models.NguoiDung;
import io.github.guennhatking.libra_auction.models.PhienDauGia;
import io.github.guennhatking.libra_auction.models.ThongTinThamGiaDauGia;
import io.github.guennhatking.libra_auction.repositories.NguoiDungRepository;
import io.github.guennhatking.libra_auction.repositories.PhienDauGiaRepository;
import io.github.guennhatking.libra_auction.repositories.ThongTinThamGiaDauGiaRepository;
import io.github.guennhatking.libra_auction.viewmodels.request.AuctionRegistrationCreateRequest;
import io.github.guennhatking.libra_auction.viewmodels.response.AuctionRegistrationResponse;

@Service
public class AuctionRegistrationService {
    private final ThongTinThamGiaDauGiaRepository thongTinThamGiaDauGiaRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final PhienDauGiaRepository phienDauGiaRepository;

    public AuctionRegistrationService(ThongTinThamGiaDauGiaRepository thongTinThamGiaDauGiaRepository,
                                       NguoiDungRepository nguoiDungRepository,
                                       PhienDauGiaRepository phienDauGiaRepository) {
        this.thongTinThamGiaDauGiaRepository = thongTinThamGiaDauGiaRepository;
        this.nguoiDungRepository = nguoiDungRepository;
        this.phienDauGiaRepository = phienDauGiaRepository;
    }

    @Transactional(readOnly = true)
    public List<AuctionRegistrationResponse> getAllRegistrations() {
        return thongTinThamGiaDauGiaRepository.findAll().stream()
            .map(this::toAuctionRegistrationResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public AuctionRegistrationResponse getRegistrationById(String id) {
        ThongTinThamGiaDauGia registration = thongTinThamGiaDauGiaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
        return toAuctionRegistrationResponse(registration);
    }

    @Transactional
    public AuctionRegistrationResponse registerForAuction(AuctionRegistrationCreateRequest request) {
        // Check if user exists
        NguoiDung user = nguoiDungRepository.findById(request.userId())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if auction session exists
        PhienDauGia auctionSession = phienDauGiaRepository.findById(request.auctionSessionId())
            .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));

        // Check if user is already registered for this auction
        boolean alreadyRegistered = Optional.ofNullable(auctionSession.getDanhSachThamGia())
            .orElse(Collections.emptyList())
            .stream()
            .anyMatch(reg -> reg.getNguoiThamGia().getId().equals(request.userId()));
        
        if (alreadyRegistered) {
            throw new IllegalArgumentException("User is already registered for this auction");
        }

        // Create new registration
        ThongTinThamGiaDauGia registration = new ThongTinThamGiaDauGia(user, auctionSession);
        ThongTinThamGiaDauGia savedRegistration = thongTinThamGiaDauGiaRepository.save(registration);

        return toAuctionRegistrationResponse(savedRegistration);
    }

    @Transactional
    public void deleteRegistration(String id) {
        ThongTinThamGiaDauGia registration = thongTinThamGiaDauGiaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Registration not found"));
        thongTinThamGiaDauGiaRepository.delete(registration);
    }

    @Transactional(readOnly = true)
    public List<AuctionRegistrationResponse> getRegistrationsByUserId(String userId) {
        // Verify user exists
        nguoiDungRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return thongTinThamGiaDauGiaRepository.findAll().stream()
            .filter(reg -> reg.getNguoiThamGia().getId().equals(userId))
            .map(this::toAuctionRegistrationResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<AuctionRegistrationResponse> getRegistrationsByAuctionSessionId(String auctionSessionId) {
        // Verify auction exists
        phienDauGiaRepository.findById(auctionSessionId)
            .orElseThrow(() -> new IllegalArgumentException("Auction session not found"));

        return thongTinThamGiaDauGiaRepository.findAll().stream()
            .filter(reg -> reg.getPhienDauGia().getId().equals(auctionSessionId))
            .map(this::toAuctionRegistrationResponse)
            .toList();
    }

    private AuctionRegistrationResponse toAuctionRegistrationResponse(ThongTinThamGiaDauGia registration) {
        NguoiDung user = registration.getNguoiThamGia();
        PhienDauGia auctionSession = registration.getPhienDauGia();
        
        String auctionTitle = auctionSession.getThongTinPhienDauGia() != null ?
            auctionSession.getThongTinPhienDauGia().getTieuDe() : "Unknown";

        return new AuctionRegistrationResponse(
            registration.getId(),
            user.getId(),
            user.getHoVaTen(),
            auctionSession.getId(),
            auctionTitle,
            registration.getThoiGianDangKy()
        );
    }
}
