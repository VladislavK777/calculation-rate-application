package io.diana.calculaterate.service.setting;

import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.BeginExceptionSetting;
import io.diana.calculaterate.domain.station.Road;
import io.diana.calculaterate.enums.CargoFlightType;
import io.diana.calculaterate.repository.setting.BeginExceptionSettingRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BeginExceptionSettingService {
    private final BeginExceptionSettingRepository beginExceptionSettingRepository;

    public BeginExceptionSettingService(BeginExceptionSettingRepository beginExceptionSettingRepository) {
        this.beginExceptionSettingRepository = beginExceptionSettingRepository;
    }

    @Transactional(readOnly = true)
    public Set<BeginExceptionSetting> findAll() {
        return new HashSet<>(beginExceptionSettingRepository.findAll(PageRequest.of(0, 300, Sort.Direction.ASC, "stations", "departments", "num", "roads")).getContent());
    }

    @Transactional(readOnly = true)
    public List<BeginExceptionSetting> findAllByParams(Road road, CargoClass cargoClass, CargoVolume cargoVolume) {
        return beginExceptionSettingRepository.findAllByRoadsContainsAndCargoClassesContainsAndCargoVolumesContainsOrderByNumAsc(road, cargoClass, cargoVolume);
    }

    @Transactional(readOnly = true)
    public BeginExceptionSetting getOne(Long id) {
        return beginExceptionSettingRepository.getReferenceById(id);
    }

    @Transactional
    public BeginExceptionSetting createOrUpdate(BeginExceptionSetting beginExceptionSetting) {
        this.validationBeginException(beginExceptionSetting);
        if (Objects.isNull(beginExceptionSetting.getId()))
            beginExceptionSettingRepository.findAll().forEach(o -> {
                if (o.equals(beginExceptionSetting))
                    throw new BadRequestAlertException(null, "маршрут существует", BeginExceptionSetting.class.getSimpleName(), "duplicate");
            });
        return beginExceptionSettingRepository.saveAndFlush(beginExceptionSetting);
    }

    @Transactional
    public Boolean delete(Long id) {
        try {
            beginExceptionSettingRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validationBeginException(BeginExceptionSetting beginExceptionSetting) {
        if (Objects.isNull(beginExceptionSetting.getNum()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'порядок'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (beginExceptionSetting.getRoads().isEmpty())
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'дорога'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getStationFrom()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'станция отправления'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getStationTo()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'станция назначение'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (beginExceptionSetting.getCargoVolumes().isEmpty())
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'объем вагона'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (beginExceptionSetting.getCargoClasses().isEmpty())
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'класс груза'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getCargoFlightType()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'тип рейса'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (CargoFlightType.FULL.equals(beginExceptionSetting.getCargoFlightType()))
            if (Objects.isNull(beginExceptionSetting.getCargo()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'груз'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getDistance()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'расстояние'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getTravelTime()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'время в пути'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getLoadUnload()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'погрузка/разгрузка'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getRate()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'ставка'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(beginExceptionSetting.getTariff()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'тариф'", BeginExceptionSettingService.class.getSimpleName(), "validationError");
    }
}
