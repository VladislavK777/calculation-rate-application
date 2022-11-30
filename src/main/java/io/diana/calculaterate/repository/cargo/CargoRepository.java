package io.diana.calculaterate.repository.cargo;

import io.diana.calculaterate.domain.cargo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long>, JpaSpecificationExecutor<Cargo> {
    Optional<Cargo> findByCode(String code);
}
