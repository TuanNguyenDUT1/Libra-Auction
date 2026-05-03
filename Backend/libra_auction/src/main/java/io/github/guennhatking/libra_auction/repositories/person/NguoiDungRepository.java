package io.github.guennhatking.libra_auction.repositories.person;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.guennhatking.libra_auction.models.person.NguoiDung;
import java.util.Optional;


public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    Optional<NguoiDung> findById(String id);
    Optional<NguoiDung> findByEmail(String email);
}
