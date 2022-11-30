package io.diana.calculaterate.repository.cargo;

import io.diana.calculaterate.domain.cargo.CargoClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoClassRepository extends JpaRepository<CargoClass, Long> {
}
