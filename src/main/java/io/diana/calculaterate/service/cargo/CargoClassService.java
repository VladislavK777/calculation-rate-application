package io.diana.calculaterate.service.cargo;

import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.repository.cargo.CargoClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoClassService {
    private final CargoClassRepository cargoClassRepository;

    public CargoClassService(CargoClassRepository cargoClassRepository) {
        this.cargoClassRepository = cargoClassRepository;
    }

    @Transactional(readOnly = true)
    public List<CargoClass> findAll() {
        return cargoClassRepository.findAll();
    }
}
