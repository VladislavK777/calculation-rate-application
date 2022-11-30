package io.diana.calculaterate.domain.cargo;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cargo_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CargoClass implements Serializable {

    @NotNull
    @Size(max = 3)
    @Id
    @Column(length = 3)
    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoClass that = (CargoClass) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
