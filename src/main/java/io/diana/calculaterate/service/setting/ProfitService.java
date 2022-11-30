package io.diana.calculaterate.service.setting;

import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.ProfitSetting;
import io.diana.calculaterate.repository.setting.ProfitSettingRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProfitService {
    private final ProfitSettingRepository profitSettingRepository;

    public ProfitService(ProfitSettingRepository profitSettingRepository) {
        this.profitSettingRepository = profitSettingRepository;
    }

    @Transactional(readOnly = true)
    public List<ProfitSetting> findAll() {
        return profitSettingRepository.findAll(PageRequest.of(0, 20, Sort.Direction.ASC, "id")).getContent();
    }

    @Transactional(readOnly = true)
    public ProfitSetting getProfitByCargoVolume(CargoVolume cargoVolume) {
        return profitSettingRepository.findByCargoVolume(cargoVolume).orElse(new ProfitSetting(0L));
    }

    @Transactional
    public ProfitSetting update(ProfitSetting profitSetting) {
        if (Objects.isNull(profitSetting.getValue()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'значение'", ProfitService.class.getSimpleName(), "validationError");
        return profitSettingRepository.saveAndFlush(profitSetting);
    }


    @Transactional(readOnly = true)
    public ProfitSetting findById(Long id) {
        return profitSettingRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException(null, "Параметр с id: " + id + " не найден", ProfitService.class.getSimpleName(), "notFound"));
    }
}
