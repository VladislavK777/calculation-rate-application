package io.diana.calculaterate.service.station;

import io.diana.calculaterate.domain.station.Department;
import io.diana.calculaterate.domain.station.Department_;
import io.diana.calculaterate.domain.station.Road_;
import io.diana.calculaterate.repository.station.DepartmentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import javax.annotation.Nullable;
import java.util.List;

import static javax.persistence.criteria.JoinType.INNER;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class DepartmentService extends QueryService<Department> {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Department> findDepartment(@Nullable String filter, @Nullable List<Long> roadId) {
        StringFilter stringFilter = new StringFilter();
        LongFilter longFilter = new LongFilter();
        if (StringUtils.isNotBlank(filter))
            stringFilter.setContains(filter);
        if (roadId != null && !roadId.isEmpty())
            longFilter.setIn(roadId);
        return departmentRepository.findAll(createSpecification(stringFilter, longFilter), PageRequest.of(0, 20, Sort.Direction.ASC, "name")).getContent();
    }

    @Transactional(readOnly = true)
    public List<Department> findDepartmentByRoadId(Long roadId) {
        return departmentRepository.findAllByRoadId(roadId, PageRequest.of(0, 20, Sort.Direction.ASC, "name")).getContent();
    }

    protected Specification<Department> createSpecification(StringFilter filter, LongFilter roadIds) {
        Specification<Department> specification = where(null);
        if (filter != null) {
            specification = specification.and(buildStringSpecification(filter, Department_.code)).
                or(buildStringSpecification(filter, Department_.name));
        }
        if (roadIds != null) {
            specification = specification.and(buildSpecification(roadIds,
                root -> root.join(Department_.road, INNER).get(Road_.id)));
        }
        return specification;
    }
}
