package io.diana.calculaterate.domain.setting;

import io.diana.calculaterate.domain.cargo.CargoVolume;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profit_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfitSetting {

    @Id
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cargo_volume_value")
    private CargoVolume cargoVolume;
    @NotNull
    private Long value;

    public ProfitSetting() {
    }

    public ProfitSetting(Long value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CargoVolume getCargoVolume() {
        return cargoVolume;
    }

    public void setCargoVolume(CargoVolume cargoVolume) {
        this.cargoVolume = cargoVolume;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
