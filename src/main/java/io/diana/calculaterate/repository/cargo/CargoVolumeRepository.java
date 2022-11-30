package io.diana.calculaterate.repository.cargo;

import io.diana.calculaterate.domain.cargo.CargoVolume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoVolumeRepository extends JpaRepository<CargoVolume, Long> {
}
