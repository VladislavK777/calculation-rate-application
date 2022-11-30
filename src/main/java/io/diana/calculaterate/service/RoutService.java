package io.diana.calculaterate.service;

import io.diana.calculaterate.domain.cargo.Cargo;
import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.setting.BeginExceptionSetting;
import io.diana.calculaterate.domain.setting.ReturnExceptionSetting;
import io.diana.calculaterate.domain.station.Station;
import io.diana.calculaterate.enums.CargoFlightType;
import io.diana.calculaterate.enums.TurnoverDayType;
import io.diana.calculaterate.service.cargo.CargoService;
import io.diana.calculaterate.service.cargo.CargoVolumeService;
import io.diana.calculaterate.service.dto.ExportModelDTO;
import io.diana.calculaterate.service.dto.InRoutDTO;
import io.diana.calculaterate.service.dto.OutRoutDTO;
import io.diana.calculaterate.service.setting.BeginExceptionSettingService;
import io.diana.calculaterate.service.setting.ProfitService;
import io.diana.calculaterate.service.setting.ReturnExceptionSettingService;
import io.diana.calculaterate.service.setting.TurnoverDayService;
import io.diana.calculaterate.service.station.StationService;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RoutService {
    private final Logger log = LoggerFactory.getLogger(RoutService.class);
    private final BeginExceptionSettingService beginExceptionSettingService;
    private final ReturnExceptionSettingService returnExceptionSettingService;
    private final ProfitService profitService;
    private final TurnoverDayService turnoverDayService;
    private final CargoVolumeService cargoVolumeService;
    private final StationService stationService;
    private final CargoService cargoService;
    private final ParserExcelService parserExcelService;

    public RoutService(BeginExceptionSettingService beginExceptionSettingService, ReturnExceptionSettingService returnExceptionSettingService, ProfitService profitService, TurnoverDayService turnoverDayService, CargoVolumeService cargoVolumeService, StationService stationService, CargoService cargoService, ParserExcelService parserExcelService) {
        this.beginExceptionSettingService = beginExceptionSettingService;
        this.returnExceptionSettingService = returnExceptionSettingService;
        this.profitService = profitService;
        this.turnoverDayService = turnoverDayService;
        this.cargoVolumeService = cargoVolumeService;
        this.stationService = stationService;
        this.cargoService = cargoService;
        this.parserExcelService = parserExcelService;
    }

    public List<ExportModelDTO> getGroupRouts(MultipartFile file) {
        List<InRoutDTO> inRoutDTOS = parserExcelService.prepareRoutsFromFile(file);
        List<ExportModelDTO> exportModelDTOS = new ArrayList<>();
        inRoutDTOS.forEach(inRoutDTO -> {
            try {
                exportModelDTOS.add(getRout(inRoutDTO));
            } catch (Exception e) {
                log.error("Ошбика получения маршрута: Станция отправления - {}, Станция назначения - {}, Груз - {}, Объем - {}",
                    inRoutDTO.getStationFromCode(), inRoutDTO.getStationToCode(), inRoutDTO.getCargoCode(), inRoutDTO.getCargoVolumeValue());
            }
        });
        return exportModelDTOS;
    }

    public ExportModelDTO getRout(InRoutDTO inRoutDTO) {
        this.validation(inRoutDTO.getStationFromCode(), inRoutDTO.getStationToCode(), inRoutDTO.getCargoCode(), inRoutDTO.getCargoVolumeValue());
        Station stationFrom = stationService.getStationByCode(inRoutDTO.getStationFromCode());
        Station stationTo = stationService.getStationByCode(inRoutDTO.getStationToCode());
        Cargo cargo = cargoService.getCargoByCode(inRoutDTO.getCargoCode());
        CargoVolume cargoVolume = cargoVolumeService.getCargoVolume(inRoutDTO.getCargoVolumeValue());
        log.info("Расчет маршрута: Станция отправления - {}, Станция назначения - {}, Груз - {}, Объем - {}",
            stationFrom.getCode(), stationTo.getCode(), cargo.getCode(), cargoVolume.getValue());
        List<OutRoutDTO> outRoutDTOList = new ArrayList<>();
        List<BeginExceptionSetting> beginExceptionSettingList = beginExceptionSettingService.findAllByParams(stationFrom.getRoad(), cargo.getCargoClass(), cargoVolume);
        AtomicReference<Long> num = new AtomicReference<>(0L);
        beginExceptionSettingList.forEach(beginExceptionSetting -> {
            if (beginExceptionSetting.getStations().contains(stationFrom)) {
                outRoutDTOList.add(new OutRoutDTO(beginExceptionSetting));
            }
        });
        if (outRoutDTOList.isEmpty()) {
            beginExceptionSettingList.forEach(beginExceptionSetting -> {
                if (beginExceptionSetting.getDepartments().contains(stationFrom.getDepartment())) {
                    outRoutDTOList.add(new OutRoutDTO(beginExceptionSetting));
                }
            });
        }
        if (outRoutDTOList.isEmpty()) {
            beginExceptionSettingList.forEach(beginExceptionSetting -> {
                if (beginExceptionSetting.getStations().isEmpty() && beginExceptionSetting.getDepartments().isEmpty())
                    outRoutDTOList.add(new OutRoutDTO(beginExceptionSetting));
            });
        }

        if (!outRoutDTOList.isEmpty()) {
            outRoutDTOList.sort(Comparator.comparing(OutRoutDTO::getNum));
            OutRoutDTO lastOutRout = outRoutDTOList.get(outRoutDTOList.size() - 1);
            OutRoutDTO emptyOutRoutDto = new OutRoutDTO();
            emptyOutRoutDto.setNum(outRoutDTOList.size() + 1L);
            emptyOutRoutDto.setStationFromName(lastOutRout.getStationToName());
            emptyOutRoutDto.setRoadFromNameShort(lastOutRout.getRoadToNameShort());
            emptyOutRoutDto.setStationToName(stationFrom.getName());
            emptyOutRoutDto.setRoadToNameShort(stationFrom.getRoad().getShortName());
            emptyOutRoutDto.setCargoName("Порожний");
            emptyOutRoutDto.setCargoFlightTypeName(CargoFlightType.EMPTY.getValue());
            emptyOutRoutDto.setRate(0.00);
            emptyOutRoutDto.setDistance(0L);
            emptyOutRoutDto.setTariff(0.00);
            emptyOutRoutDto.setLoadUnload(turnoverDayService.findByKey(TurnoverDayType.UNLOAD).getValue());
            emptyOutRoutDto.setTravelTime(0.00);
            emptyOutRoutDto.setFullCountDays(turnoverDayService.findByKey(TurnoverDayType.UNLOAD).getValue());
            outRoutDTOList.add(emptyOutRoutDto);
        }
        OutRoutDTO requestRout = new OutRoutDTO();
        requestRout.setNum(outRoutDTOList.size() + 1L);
        requestRout.setStationFromName(stationFrom.getName());
        requestRout.setRoadFromNameShort(stationFrom.getRoad().getShortName());
        requestRout.setStationToName(stationTo.getName());
        requestRout.setRoadToNameShort(stationTo.getRoad().getShortName());
        requestRout.setCargoName(cargo.getName());
        requestRout.setCargoFlightTypeName(CargoFlightType.FULL.getValue());
        requestRout.setRate(0.00);
        requestRout.setDistance(0L);
        requestRout.setTariff(0.00);
        requestRout.setLoadUnload(turnoverDayService.findByKey(TurnoverDayType.LOAD).getValue());
        requestRout.setTravelTime(0.00);
        requestRout.setFullCountDays(turnoverDayService.findByKey(TurnoverDayType.LOAD).getValue());
        requestRout.setRequestRout(true);
        outRoutDTOList.add(requestRout);
        num.set(requestRout.getNum());

        //Далее возвратные исключения
        AtomicBoolean firstRout = new AtomicBoolean(true);
        List<OutRoutDTO> outRoutReturnDTOList = new ArrayList<>();
        List<ReturnExceptionSetting> returnExceptionSettingList = returnExceptionSettingService.findAllByParams(stationTo.getRoad(), cargo.getCargoClass(), cargoVolume);
        returnExceptionSettingList.forEach(returnExceptionSetting -> {
            if (returnExceptionSetting.getStations().contains(stationTo)) {
                num.getAndSet(num.get() + 1);
                if (Objects.nonNull(returnExceptionSetting.getStationReturn())) {
                    OutRoutDTO outReturnRoutDto = createRoutDtoWithReturnException(stationTo, returnExceptionSetting, true);
                    outReturnRoutDto.setNum(num.get());
                    outRoutReturnDTOList.add(outReturnRoutDto);
                } else {
                    if (firstRout.get()) {
                        OutRoutDTO outReturnRoutDto = createRoutDtoWithReturnException(stationTo, returnExceptionSetting, false);
                        outReturnRoutDto.setNum(num.get());
                        outRoutReturnDTOList.add(outReturnRoutDto);
                        firstRout.set(false);
                    }
                    OutRoutDTO outReturnRoutDto = new OutRoutDTO(returnExceptionSetting);
                    outReturnRoutDto.setNum(num.get());
                    outRoutReturnDTOList.add(outReturnRoutDto);
                }
            }
        });
        if (outRoutReturnDTOList.isEmpty()) {
            returnExceptionSettingList.forEach(returnExceptionSetting -> {
                if (returnExceptionSetting.getDepartments().contains(stationTo.getDepartment())) {
                    num.getAndSet(num.get() + 1);
                    if (Objects.nonNull(returnExceptionSetting.getStationReturn())) {
                        OutRoutDTO outReturnRoutDto = createRoutDtoWithReturnException(stationTo, returnExceptionSetting, true);
                        outReturnRoutDto.setNum(num.get());
                        outRoutReturnDTOList.add(outReturnRoutDto);
                    } else {
                        if (firstRout.get()) {
                            OutRoutDTO outReturnRoutDto = createRoutDtoWithReturnException(stationTo, returnExceptionSetting, false);
                            outReturnRoutDto.setNum(num.get());
                            outRoutReturnDTOList.add(outReturnRoutDto);
                            firstRout.set(false);
                        }
                        OutRoutDTO outReturnRoutDto = new OutRoutDTO(returnExceptionSetting);
                        outReturnRoutDto.setNum(num.get());
                        outRoutReturnDTOList.add(outReturnRoutDto);
                    }
                }
            });
        }
        if (outRoutReturnDTOList.isEmpty()) {
            returnExceptionSettingList.forEach(returnExceptionSetting -> {
                if (returnExceptionSetting.getStations().isEmpty() && returnExceptionSetting.getDepartments().isEmpty()) {
                    num.getAndSet(num.get() + 1);
                    if (Objects.nonNull(returnExceptionSetting.getStationReturn())) {
                        OutRoutDTO outReturnRoutDto = createRoutDtoWithReturnException(stationTo, returnExceptionSetting, true);
                        outReturnRoutDto.setNum(num.get());
                        outRoutReturnDTOList.add(outReturnRoutDto);
                    } else {
                        if (firstRout.get()) {
                            OutRoutDTO outReturnRoutDto = createRoutDtoWithReturnException(stationTo, returnExceptionSetting, false);
                            outReturnRoutDto.setNum(num.get());
                            outRoutReturnDTOList.add(outReturnRoutDto);
                            firstRout.set(false);
                        }
                        OutRoutDTO outReturnRoutDto = new OutRoutDTO(returnExceptionSetting);
                        outReturnRoutDto.setNum(num.get());
                        outRoutReturnDTOList.add(outReturnRoutDto);
                    }
                }
            });
        }

        outRoutReturnDTOList.sort(Comparator.comparing(OutRoutDTO::getNum));
        outRoutDTOList.addAll(outRoutReturnDTOList);
        return new ExportModelDTO(outRoutDTOList, profitService.getProfitByCargoVolume(cargoVolume).getValue(), inRoutDTO.getCargoVolumeValue());
    }

    private OutRoutDTO createRoutDtoWithReturnException(Station stationTo, ReturnExceptionSetting returnExceptionSetting, boolean isReturnStation) {
        OutRoutDTO outReturnRoutDto = new OutRoutDTO();
        outReturnRoutDto.setStationFromName(stationTo.getName());
        outReturnRoutDto.setRoadFromNameShort(stationTo.getRoad().getShortName());
        outReturnRoutDto.setStationToName(isReturnStation ? returnExceptionSetting.getStationReturn().getName() : returnExceptionSetting.getStationFrom().getName());
        outReturnRoutDto.setRoadToNameShort(isReturnStation ? returnExceptionSetting.getStationReturn().getRoad().getShortName() : returnExceptionSetting.getStationFrom().getRoad().getShortName());
        outReturnRoutDto.setCargoFlightTypeName(CargoFlightType.EMPTY.getValue());
        outReturnRoutDto.setCargoName("Порожний");
        outReturnRoutDto.setRate(0.00);
        outReturnRoutDto.setDistance(0L);
        outReturnRoutDto.setTariff(0.00);
        outReturnRoutDto.setLoadUnload(turnoverDayService.findByKey(TurnoverDayType.UNLOAD).getValue());
        outReturnRoutDto.setTravelTime(0.00);
        outReturnRoutDto.setFullCountDays(turnoverDayService.findByKey(TurnoverDayType.UNLOAD).getValue());
        return outReturnRoutDto;
    }

    private void validation(String stationFromCode, String stationToCode, String cargoCode, Long cargoVolumeValue) {
        if (StringUtils.isBlank(stationFromCode) && StringUtils.isBlank(stationFromCode)
            && StringUtils.isBlank(stationFromCode) && Objects.isNull(cargoVolumeValue))
            throw new BadRequestAlertException(null, "необходимо заполнить все поля", RoutService.class.getSimpleName(), "validationError");
        if (StringUtils.isBlank(stationFromCode))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'станция отправления'", RoutService.class.getSimpleName(), "validationError");
        if (StringUtils.isBlank(stationToCode))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'станцию назначения'", RoutService.class.getSimpleName(), "validationError");
        if (StringUtils.isBlank(cargoCode))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'груз'", RoutService.class.getSimpleName(), "validationError");
        if (Objects.isNull(cargoVolumeValue))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'объем вагона'", RoutService.class.getSimpleName(), "validationError");
    }
}
