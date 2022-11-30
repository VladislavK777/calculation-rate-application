package io.diana.calculaterate.repository.station;

import io.diana.calculaterate.domain.station.Road;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadRepository extends JpaRepository<Road, Long> {
}
