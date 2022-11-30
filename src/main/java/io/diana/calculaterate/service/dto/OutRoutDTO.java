package io.diana.calculaterate.service.dto;

import io.diana.calculaterate.domain.setting.BeginExceptionSetting;
import io.diana.calculaterate.domain.setting.ReturnExceptionSetting;
import io.diana.calculaterate.enums.CargoFlightType;

public class OutRoutDTO {
    private Long num;
    private String stationFromName;
    private String roadFromNameShort;
    private String stationToName;
    private String roadToNameShort;
    private String cargoFlightTypeName;
    private String cargoName;
    private Long distance;
    private Double travelTime;
    private Double loadUnload;
    private Double fullCountDays;
    private Double rate;
    private Double tariff;
    private boolean requestRout;

    public OutRoutDTO(BeginExceptionSetting beginExceptionSetting) {
        this.num = beginExceptionSetting.getNum();
        this.stationFromName = beginExceptionSetting.getStationFrom().getName();
        this.roadFromNameShort = beginExceptionSetting.getStationFrom().getRoad().getShortName();
        this.stationToName = beginExceptionSetting.getStationTo().getName();
        this.roadToNameShort = beginExceptionSetting.getStationTo().getRoad().getShortName();
        this.cargoFlightTypeName = beginExceptionSetting.getCargoFlightType().getValue();
        this.cargoName = CargoFlightType.EMPTY.equals(beginExceptionSetting.getCargoFlightType()) ? "Порожний" : beginExceptionSetting.getCargo().getName();
        this.distance = beginExceptionSetting.getDistance();
        this.travelTime = beginExceptionSetting.getTravelTime();
        this.loadUnload = beginExceptionSetting.getLoadUnload();
        this.fullCountDays = beginExceptionSetting.getLoadUnload() + beginExceptionSetting.getTravelTime();
        this.rate = beginExceptionSetting.getRate();
        this.tariff = beginExceptionSetting.getTariff();
    }

    public OutRoutDTO(ReturnExceptionSetting returnExceptionSetting) {
        this.num = returnExceptionSetting.getNum();
        this.stationFromName = returnExceptionSetting.getStationFrom().getName();
        this.roadFromNameShort = returnExceptionSetting.getStationFrom().getRoad().getShortName();
        this.stationToName = returnExceptionSetting.getStationTo().getName();
        this.roadToNameShort = returnExceptionSetting.getStationTo().getRoad().getShortName();
        this.cargoFlightTypeName = returnExceptionSetting.getCargoFlightType().getValue();
        this.cargoName = CargoFlightType.EMPTY.equals(returnExceptionSetting.getCargoFlightType()) ? "Порожний" : returnExceptionSetting.getCargo().getName();
        this.distance = returnExceptionSetting.getDistance();
        this.travelTime = returnExceptionSetting.getTravelTime();
        this.loadUnload = returnExceptionSetting.getLoadUnload();
        this.fullCountDays = returnExceptionSetting.getLoadUnload() + returnExceptionSetting.getTravelTime();
        this.rate = returnExceptionSetting.getRate();
        this.tariff = returnExceptionSetting.getTariff();
    }

    public OutRoutDTO() {
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getStationFromName() {
        return stationFromName;
    }

    public void setStationFromName(String stationFromName) {
        this.stationFromName = stationFromName;
    }

    public String getRoadFromNameShort() {
        return roadFromNameShort;
    }

    public void setRoadFromNameShort(String roadFromNameShort) {
        this.roadFromNameShort = roadFromNameShort;
    }

    public String getStationToName() {
        return stationToName;
    }

    public void setStationToName(String stationToName) {
        this.stationToName = stationToName;
    }

    public String getRoadToNameShort() {
        return roadToNameShort;
    }

    public void setRoadToNameShort(String roadToNameShort) {
        this.roadToNameShort = roadToNameShort;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Double travelTime) {
        this.travelTime = travelTime;
    }

    public Double getLoadUnload() {
        return loadUnload;
    }

    public void setLoadUnload(Double loadUnload) {
        this.loadUnload = loadUnload;
    }

    public Double getFullCountDays() {
        return fullCountDays;
    }

    public void setFullCountDays(Double fullCountDays) {
        this.fullCountDays = fullCountDays;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getTariff() {
        return tariff;
    }

    public void setTariff(Double tariff) {
        this.tariff = tariff;
    }

    public String getCargoFlightTypeName() {
        return cargoFlightTypeName;
    }

    public void setCargoFlightTypeName(String cargoFlightTypeName) {
        this.cargoFlightTypeName = cargoFlightTypeName;
    }

    public boolean isRequestRout() {
        return requestRout;
    }

    public void setRequestRout(boolean requestRout) {
        this.requestRout = requestRout;
    }

    @Override
    public String toString() {
        return "OutRoutDTO{" +
            "num=" + num +
            ", stationFromName='" + stationFromName + '\'' +
            ", roadFromNameShort='" + roadFromNameShort + '\'' +
            ", stationToName='" + stationToName + '\'' +
            ", roadToNameShort='" + roadToNameShort + '\'' +
            ", cargoFlightTypeName='" + cargoFlightTypeName + '\'' +
            ", cargoName='" + cargoName + '\'' +
            ", distance=" + distance +
            ", countDays=" + travelTime +
            ", countDaysLoadUnload=" + loadUnload +
            ", fullCountDays=" + fullCountDays +
            ", rate=" + rate +
            ", tariff=" + tariff +
            ", requestRout=" + requestRout +
            '}';
    }
}
