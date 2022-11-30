package io.diana.calculaterate.domain.setting;

import io.diana.calculaterate.enums.TurnoverDayType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "turnover_day_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TurnoverDaySetting {

    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TurnoverDayType key;
    @NotNull
    private Double value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TurnoverDayType getKey() {
        return key;
    }

    public void setKey(TurnoverDayType key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
