package io.diana.calculaterate.service.dto;

public class InRoutDTO {
    private String stationFromCode;
    private String stationToCode;
    private String cargoCode;
    private Long cargoVolumeValue;

    public InRoutDTO() {
    }

    public InRoutDTO(String stationFromCode, String stationToCode, String cargoCode, Long cargoVolumeValue) {
        this.stationFromCode = stationFromCode;
        this.stationToCode = stationToCode;
        this.cargoCode = cargoCode;
        this.cargoVolumeValue = cargoVolumeValue;
    }

    public String getStationFromCode() {
        return stationFromCode;
    }

    public void setStationFromCode(String stationFromCode) {
        this.stationFromCode = stationFromCode;
    }

    public String getStationToCode() {
        return stationToCode;
    }

    public void setStationToCode(String stationToCode) {
        this.stationToCode = stationToCode;
    }

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public Long getCargoVolumeValue() {
        return cargoVolumeValue;
    }

    public void setCargoVolumeValue(Long cargoVolumeValue) {
        this.cargoVolumeValue = cargoVolumeValue;
    }
}
