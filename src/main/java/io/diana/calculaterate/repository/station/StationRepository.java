package io.diana.calculaterate.repository.station;

import io.diana.calculaterate.domain.station.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long>, JpaSpecificationExecutor<Station> {
    Optional<Station> findByCode(String code);
}
