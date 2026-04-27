package io.github.guennhatking.libra_auction.repositories.account;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.guennhatking.libra_auction.models.account.TaiKhoan;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {
}
