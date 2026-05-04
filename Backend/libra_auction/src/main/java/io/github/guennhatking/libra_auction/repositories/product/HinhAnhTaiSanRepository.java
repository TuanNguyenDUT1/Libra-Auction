package io.github.guennhatking.libra_auction.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.guennhatking.libra_auction.models.product.HinhAnhTaiSan;

import java.util.List;

public interface HinhAnhTaiSanRepository extends JpaRepository<HinhAnhTaiSan, String> {
	List<HinhAnhTaiSan> findByTaiSanIdOrderByThuTuHienThiAsc(String taiSanId);

	void deleteByTaiSanId(String taiSanId);
}
