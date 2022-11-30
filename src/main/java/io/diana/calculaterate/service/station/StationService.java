package io.diana.calculaterate.service.station;

import io.diana.calculaterate.domain.station.Road_;
import io.diana.calculaterate.domain.station.Station;
import io.diana.calculaterate.domain.station.Station_;
import io.diana.calculaterate.repository.station.StationRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
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
import java.util.Objects;

import static javax.persistence.criteria.JoinType.INNER;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class StationService extends QueryService<Station> {
    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    public Station createOrUpdate(Station station) {
        this.validationStation(station);
        if (Objects.isNull(station.getId())) {
            station.setId(stationRepository.count() + 1);
        }
        return stationRepository.saveAndFlush(station);
    }

    @Transactional(readOnly = true)
    public Station getStationByCode(String code) {
        return stationRepository.findByCode(code).orElseThrow(() -> new BadRequestAlertException(null, "Станция с кодом " + code + " не найдена", Station.class.getSimpleName(), "notFound"));
    }

    @Transactional(readOnly = true)
    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(null, "Станция с id " + id + " не найдена", Station.class.getSimpleName(), "notFound"));
    }

    @Transactional(readOnly = true)
    public List<Station> findStation(@Nullable String filter, @Nullable List<Long> roadId) {
        StringFilter stringFilter = new StringFilter();
        LongFilter longFilter = new LongFilter();
        if (StringUtils.isNotBlank(filter))
            stringFilter.setContains(filter);
        if (roadId != null && !roadId.isEmpty())
            longFilter.setIn(roadId);
        return stationRepository.findAll(createSpecification(stringFilter, longFilter), PageRequest.of(0, 20, Sort.Direction.ASC, "name")).getContent();
    }

    protected Specification<Station> createSpecification(StringFilter filter, LongFilter roadIds) {
        Specification<Station> specification = where(null);
        if (filter != null) {
            specification = specification.and(buildStringSpecification(filter, Station_.code)).
                or(buildStringSpecification(filter, Station_.name));
        }
        if (roadIds != null) {
            specification = specification.and(buildSpecification(roadIds,
                root -> root.join(Station_.road, INNER).get(Road_.id)));
        }
        return specification;
    }

    private void validationStation(Station station) {
        if (StringUtils.isBlank(station.getCode()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'код'", StationService.class.getSimpleName(), "validationError");
        if (StringUtils.isBlank(station.getName()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'название'", StationService.class.getSimpleName(), "validationError");
        if (Objects.isNull(station.getRoad()))
            throw new BadRequestAlertException(null, "необходимо заполнить поле 'дорога'", StationService.class.getSimpleName(), "validationError");
    }
}
