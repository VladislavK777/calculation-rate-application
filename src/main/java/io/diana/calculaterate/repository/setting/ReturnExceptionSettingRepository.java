package io.diana.calculaterate.repository.setting;

import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.ReturnExceptionSetting;
import io.diana.calculaterate.domain.station.Road;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnExceptionSettingRepository extends JpaRepository<ReturnExceptionSetting, Long> {
    List<ReturnExceptionSetting> findAllByRoadsContainsAndCargoClassesContainsAndCargoVolumesContainsOrderByNumAsc(Road road, CargoClass cargoClass, CargoVolume cargoVolume);
}
