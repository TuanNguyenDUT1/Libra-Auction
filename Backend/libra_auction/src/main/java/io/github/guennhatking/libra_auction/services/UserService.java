package io.github.guennhatking.libra_auction.services;

import io.github.guennhatking.libra_auction.enums.account.TrangThaiEmail;
import io.github.guennhatking.libra_auction.enums.account.TrangThaiTaiKhoan;
import io.github.guennhatking.libra_auction.models.account.Role;
import io.github.guennhatking.libra_auction.models.account.TaiKhoanOAuth;
import io.github.guennhatking.libra_auction.models.account.TaiKhoanPassword;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import io.github.guennhatking.libra_auction.repositories.account.RoleRepository;
import io.github.guennhatking.libra_auction.repositories.account.TaiKhoanOAuthRepository;
import io.github.guennhatking.libra_auction.repositories.account.TaiKhoanPasswordRepository;
import io.github.guennhatking.libra_auction.repositories.person.NguoiDungRepository;
import io.github.guennhatking.libra_auction.viewmodels.response.ImageUploadedResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final NguoiDungRepository nguoiDungRepository;
    private final TaiKhoanPasswordRepository taiKhoanPasswordRepository;
    private final TaiKhoanOAuthRepository taiKhoanOAuthRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final ImageUploadService imageUploadService;    

    public UserService(NguoiDungRepository nguoiDungRepository,
            TaiKhoanPasswordRepository taiKhoanPasswordRepository,
            TaiKhoanOAuthRepository taiKhoanOAuthRepository,
            RoleRepository roleRepository,
            PasswordService passwordService,
            ImageUploadService imageUploadService) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.taiKhoanPasswordRepository = taiKhoanPasswordRepository;
        this.taiKhoanOAuthRepository = taiKhoanOAuthRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.imageUploadService = imageUploadService;
    }

    @Transactional
    public NguoiDung createPasswordUser(String email, String username, String password, String hoVaTen) {
        return createPasswordUser(email, username, password, hoVaTen, null, null, null);
    }

    @Transactional
    public NguoiDung createPasswordUser(String email, String username, String password, String hoVaTen,
            String soDienThoai, String cccd, String anhDaiDien) {
        if (taiKhoanPasswordRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("The username already exists");
        }

        NguoiDung user = new NguoiDung(hoVaTen, email);
        user.setSoDienThoai(soDienThoai);
        user.setCccd(cccd);
        user.setAnhDaiDien(anhDaiDien);
        user.setTrangThaiEmail(TrangThaiEmail.CHUA_XAC_THUC);
        user.setTrangThaiTaiKhoan(TrangThaiTaiKhoan.CHO_XAC_NHAN);

        NguoiDung savedUser = nguoiDungRepository.save(user);

        String encodedPassword = passwordService.encodePassword(password);
        TaiKhoanPassword taiKhoan = new TaiKhoanPassword(
                UUID.randomUUID().toString(),
                username,
                encodedPassword,
                new byte[0]);
        taiKhoan.setNguoiDung(savedUser);
        taiKhoan.setTrangThai(TrangThaiTaiKhoan.CHO_XAC_NHAN);
        taiKhoanPasswordRepository.save(taiKhoan);

        assignDefaultRole(savedUser);

        return savedUser;
    }

    public NguoiDung createOAuthUser(String email, String googleId, String displayName, String pictureUrl) {
        Optional<NguoiDung> existingUser = nguoiDungRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        NguoiDung user = new NguoiDung(displayName, email);
        try {
            ImageUploadedResponse newUrl = imageUploadService.uploadImageFromUrl(pictureUrl, "avatars");
            user.setAnhDaiDien(newUrl.secureUrl());
        } catch (Exception e) {
            System.out.println("Failed to upload avatar for user " + email + ": " + e.getMessage());
        }
        user.setTrangThaiEmail(TrangThaiEmail.DA_XAC_THUC);
        user.setTrangThaiTaiKhoan(TrangThaiTaiKhoan.HOAT_DONG);

        NguoiDung savedUser = nguoiDungRepository.save(user);

        TaiKhoanOAuth oauthAccount = new TaiKhoanOAuth(
                UUID.randomUUID().toString(),
                "google",
                googleId);
        oauthAccount.setNguoiDung(savedUser);
        oauthAccount.setTrangThai(TrangThaiTaiKhoan.HOAT_DONG);
        taiKhoanOAuthRepository.save(oauthAccount);

        assignDefaultRole(savedUser);

        return savedUser;
    }

    public Optional<NguoiDung> findByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }

    public Optional<NguoiDung> findById(String userId) {
        return nguoiDungRepository.findById(userId);
    }

    public Optional<TaiKhoanPassword> findPasswordAccountByUsername(String username) {
        return taiKhoanPasswordRepository.findByUsername(username);
    }

    public Optional<TaiKhoanOAuth> findOAuthAccountByProviderId(String providerId) {
        return taiKhoanOAuthRepository.findByProviderId(providerId);
    }

    private void assignDefaultRole(NguoiDung user) {
        Optional<Role> defaultRole = roleRepository.findById("USER");
        if (defaultRole.isPresent()) {
            user.setRoles(new ArrayList<>(List.of(defaultRole.get())));
            nguoiDungRepository.save(user);
        }
    }
}
