package io.diana.calculaterate.service.setting;

import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.ReturnExceptionSetting;
import io.diana.calculaterate.domain.station.Road;
import io.diana.calculaterate.enums.CargoFlightType;
import io.diana.calculaterate.repository.setting.ReturnExceptionSettingRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReturnExceptionSettingService {
    private final ReturnExceptionSettingRepository returnExceptionSettingRepository;

    public ReturnExceptionSettingService(ReturnExceptionSettingRepository returnExceptionSettingRepository) {
        this.returnExceptionSettingRepository = returnExceptionSettingRepository;
    }

    @Transactional(readOnly = true)
    public List<ReturnExceptionSetting> findAll() {
        return returnExceptionSettingRepository.findAll(PageRequest.of(0, 300, Sort.Direction.ASC, "roads.name", "num", "departments.name", "stations.name")).getContent();
    }

    @Transactional(readOnly = true)
    public List<ReturnExceptionSetting> findAllByParams(Road road, CargoClass cargoClass, CargoVolume cargoVolume) {
        return returnExceptionSettingRepository.findAllByRoadsContainsAndCargoClassesContainsAndCargoVolumesContainsOrderByNumAsc(road, cargoClass, cargoVolume);
    }

    @Transactional(readOnly = true)
    public ReturnExceptionSetting getOne(Long id) {
        return returnExceptionSettingRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException(null, "возратное исключение с id: " + id + " не найдено", ReturnExceptionSetting.class.getSimpleName(), "notFound"));
    }

    @Transactional
    public ReturnExceptionSetting createOrUpdate(ReturnExceptionSetting returnExceptionSetting) {
        this.validationReturnException(returnExceptionSetting);
        if (Objects.isNull(returnExceptionSetting.getId()))
            returnExceptionSettingRepository.findAll().forEach(o -> {
                if (o.equals(returnExceptionSetting))
                    throw new BadRequestAlertException(null, "маршрут существует", ReturnExceptionSetting.class.getSimpleName(), "duplicate");
            });
        return returnExceptionSettingRepository.saveAndFlush(returnExceptionSetting);
    }

    @Transactional
    public Boolean delete(Long id) {
        try {
            returnExceptionSettingRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validationReturnException(ReturnExceptionSetting returnExceptionSetting) {
        if (Objects.isNull(returnExceptionSetting.getNum()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'порядок'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
        if (returnExceptionSetting.getRoads().isEmpty())
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'дорога'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(returnExceptionSetting.getStationReturn()) && Objects.isNull(returnExceptionSetting.getStationFrom()) && Objects.isNull(returnExceptionSetting.getStationTo()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'станция возврата' или поля 'станция отправления' и 'станция назначение'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
        if (Objects.isNull(returnExceptionSetting.getStationReturn())) {
            if (Objects.isNull(returnExceptionSetting.getStationFrom()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'станция отправления'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getStationTo()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'станция назначение'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (returnExceptionSetting.getCargoVolumes().isEmpty())
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'объем вагона'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (returnExceptionSetting.getCargoClasses().isEmpty())
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'класс груза'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getCargoFlightType()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'тип рейса'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (CargoFlightType.FULL.equals(returnExceptionSetting.getCargoFlightType()))
                if (Objects.isNull(returnExceptionSetting.getCargo()))
                    throw new BadRequestAlertException(null, "необходимо заполнить поле 'груз'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getDistance()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'расстояние'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getTravelTime()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'время в пути'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getLoadUnload()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'погрузка/разгрузка'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getRate()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'ставка'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
            if (Objects.isNull(returnExceptionSetting.getTariff()))
                throw new BadRequestAlertException(null, "необходимо заполнить поле 'тариф'", ReturnExceptionSettingService.class.getSimpleName(), "validationError");
        }
    }
}
