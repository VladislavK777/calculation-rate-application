package io.diana.calculaterate.domain.setting;

import io.diana.calculaterate.domain.cargo.Cargo;
import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.domain.station.Department;
import io.diana.calculaterate.domain.station.Road;
import io.diana.calculaterate.domain.station.Station;
import io.diana.calculaterate.enums.CargoFlightType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "begin_exception_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BeginExceptionSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "begin_exception_setting_generator")
    @SequenceGenerator(name = "begin_exception_setting_generator", sequenceName = "begin_exception_setting_generator", allocationSize = 1)
    private Long id;

    @NotNull
    private Long num;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "begin_exceptions_roads",
        joinColumns = {@JoinColumn(name = "begin_exception_setting_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "road_id", referencedColumnName = "id")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Road> roads = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "begin_exceptions_stations",
        joinColumns = {@JoinColumn(name = "begin_exception_setting_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "station_id", referencedColumnName = "id")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Station> stations = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "begin_exceptions_departments",
        joinColumns = {@JoinColumn(name = "begin_exception_setting_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "id")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Department> departments = new HashSet<>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "begin_exceptions_cargo_volumes",
        joinColumns = {@JoinColumn(name = "begin_exception_setting_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "cargo_volume_value", referencedColumnName = "value")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<CargoVolume> cargoVolumes = new HashSet<>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "begin_exceptions_cargo_classes",
        joinColumns = {@JoinColumn(name = "begin_exception_setting_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "cargo_class_value", referencedColumnName = "value")}
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<CargoClass> cargoClasses = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "station_from_id")
    private Station stationFrom;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "station_to_id")
    private Station stationTo;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CargoFlightType cargoFlightType;

    @NotNull
    private Double travelTime;
    @NotNull
    private Double loadUnload;
    @NotNull
    private Long distance;
    @NotNull
    private Double rate;
    @NotNull
    private Double tariff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Set<Road> getRoads() {
        return roads;
    }

    public void setRoads(Set<Road> roads) {
        this.roads = roads;
    }

    public Set<Station> getStations() {
        return stations;
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<CargoVolume> getCargoVolumes() {
        return cargoVolumes;
    }

    public void setCargoVolumes(Set<CargoVolume> cargoVolumes) {
        this.cargoVolumes = cargoVolumes;
    }

    public Set<CargoClass> getCargoClasses() {
        return cargoClasses;
    }

    public void setCargoClasses(Set<CargoClass> cargoClasses) {
        this.cargoClasses = cargoClasses;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(Station stationFrom) {
        this.stationFrom = stationFrom;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public void setStationTo(Station stationTo) {
        this.stationTo = stationTo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public CargoFlightType getCargoFlightType() {
        return cargoFlightType;
    }

    public void setCargoFlightType(CargoFlightType cargoFlightType) {
        this.cargoFlightType = cargoFlightType;
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

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeginExceptionSetting that = (BeginExceptionSetting) o;
        return
            containsElements(roads, that.roads) &&
                containsElements(stations, that.stations) &&
                containsElements(departments, that.departments) &&
                containsElements(cargoVolumes, that.cargoVolumes) &&
                containsElements(cargoClasses, that.cargoClasses) &&
                Objects.equals(stationFrom, that.stationFrom) &&
                Objects.equals(stationTo, that.stationTo) &&
                Objects.equals(cargo, that.cargo);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    private boolean containsElements(Collection<?> o, Collection<?> that) {
        Iterator<?> var2 = that.iterator();

        Object e;
        if (!var2.hasNext()) {
            return true;
        }
        e = var2.next();
        return o.contains(e);
    }
}
