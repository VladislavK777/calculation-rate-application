package io.diana.calculaterate.repository.setting;

import io.diana.calculaterate.domain.setting.TurnoverDaySetting;
import io.diana.calculaterate.enums.TurnoverDayType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoverDayRepository extends JpaRepository<TurnoverDaySetting, Long> {
    TurnoverDaySetting findByKey(TurnoverDayType key);
}
