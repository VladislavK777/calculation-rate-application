package io.diana.calculaterate.service.setting;

import io.diana.calculaterate.domain.setting.BeginExceptionSetting;
import io.diana.calculaterate.domain.setting.TurnoverDaySetting;
import io.diana.calculaterate.enums.TurnoverDayType;
import io.diana.calculaterate.repository.setting.TurnoverDayRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TurnoverDayService {
    private final TurnoverDayRepository turnoverDayRepository;

    public TurnoverDayService(TurnoverDayRepository turnoverDayRepository) {
        this.turnoverDayRepository = turnoverDayRepository;
    }

    @Transactional(readOnly = true)
    public TurnoverDaySetting findByKey(TurnoverDayType key) {
        return turnoverDayRepository.findByKey(key);
    }

    @Transactional(readOnly = true)
    public List<TurnoverDaySetting> findAll() {
        return turnoverDayRepository.findAll(PageRequest.of(0, 20, Sort.Direction.ASC, "id")).getContent();
    }

    @Transactional
    public TurnoverDaySetting update(TurnoverDaySetting turnoverDaySetting) {
        if (Objects.isNull(turnoverDaySetting.getValue()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'значение'", TurnoverDayService.class.getSimpleName(), "validationError");
        return turnoverDayRepository.saveAndFlush(turnoverDaySetting);
    }

    @Transactional(readOnly = true)
    public TurnoverDaySetting findById(Long id) {
        return turnoverDayRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException(null, "Параметр с id: " + id + " не найден", TurnoverDayService.class.getSimpleName(), "notFound"));
    }
}
