package io.diana.calculaterate.service.dto;

import java.util.List;

public class ExportModelDTO {
    private List<OutRoutDTO> rout;
    private Long profit;
    private Long cargoVolume;

    public ExportModelDTO() {
    }

    public ExportModelDTO(List<OutRoutDTO> rout, Long profit, Long cargoVolume) {
        this.rout = rout;
        this.profit = profit;
        this.cargoVolume = cargoVolume;
    }

    public List<OutRoutDTO> getRout() {
        return rout;
    }

    public void setRout(List<OutRoutDTO> rout) {
        this.rout = rout;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public Long getCargoVolume() {
        return cargoVolume;
    }

    public void setCargoVolume(Long cargoVolume) {
        this.cargoVolume = cargoVolume;
    }
}
