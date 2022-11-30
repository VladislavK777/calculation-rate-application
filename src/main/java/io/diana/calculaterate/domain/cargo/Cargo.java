package io.diana.calculaterate.domain.cargo;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cargo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cargo implements Serializable {
    @Id
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cargo_class_value")
    private CargoClass cargoClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CargoClass getCargoClass() {
        return cargoClass;
    }

    public void setCargoClass(CargoClass cargoClass) {
        this.cargoClass = cargoClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return Objects.equals(id, cargo.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
