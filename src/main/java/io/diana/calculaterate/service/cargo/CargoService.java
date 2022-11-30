package io.diana.calculaterate.service.cargo;

import io.diana.calculaterate.domain.cargo.Cargo;
import io.diana.calculaterate.domain.cargo.Cargo_;
import io.diana.calculaterate.repository.cargo.CargoRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.StringFilter;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CargoService extends QueryService<Cargo> {
    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Transactional(readOnly = true)
    public Cargo getCargoByCode(String code) {
        return cargoRepository.findByCode(code).orElseThrow(() -> new BadRequestAlertException(null, "груз с кодом " + code + " не найден", Cargo.class.getSimpleName(), "notFound"));
    }

    @Transactional(readOnly = true)
    public List<Cargo> findCargo(String filter) {
        StringFilter stringFilter = new StringFilter();
        if (StringUtils.isNotBlank(filter))
            stringFilter.setContains(filter);
        return cargoRepository.findAll(createSpecification(stringFilter), PageRequest.of(0, 20, Sort.Direction.ASC, "name")).getContent();
    }

    protected Specification<Cargo> createSpecification(StringFilter filter) {
        Specification<Cargo> specification = where(null);
        if (filter != null) {
            specification = specification.and(buildStringSpecification(filter, Cargo_.code)).
                or(buildStringSpecification(filter, Cargo_.name));
        }
        return specification;
    }
}
