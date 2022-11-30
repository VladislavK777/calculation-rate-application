package io.diana.calculaterate.repository.setting;

import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.ProfitSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfitSettingRepository extends JpaRepository<ProfitSetting, Long> {
    Optional<ProfitSetting> findByCargoVolume(CargoVolume cargoVolume);
}
