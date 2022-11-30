package io.diana.calculaterate.repository.station;

import io.diana.calculaterate.domain.station.Department;
import io.diana.calculaterate.domain.station.Road;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {

    @Query(value = "from Department d where d.road.id = :roadId")
    Page<Department> findAllByRoadId(@Param(value = "roadId") Long roadId, Pageable pageable);
}
