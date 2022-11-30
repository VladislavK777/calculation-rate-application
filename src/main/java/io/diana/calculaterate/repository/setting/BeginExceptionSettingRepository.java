package io.diana.calculaterate.repository.setting;

import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.BeginExceptionSetting;
import io.diana.calculaterate.domain.station.Road;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeginExceptionSettingRepository extends JpaRepository<BeginExceptionSetting, Long> {
    List<BeginExceptionSetting> findAllByRoadsContainsAndCargoClassesContainsAndCargoVolumesContainsOrderByNumAsc(Road road, CargoClass cargoClass, CargoVolume cargoVolume);
}
