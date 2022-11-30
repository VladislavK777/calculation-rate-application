package io.diana.calculaterate.service.cargo;

import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.repository.cargo.CargoVolumeRepository;
import io.diana.calculaterate.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoVolumeService {
    private final CargoVolumeRepository cargoVolumeRepository;

    public CargoVolumeService(CargoVolumeRepository cargoVolumeRepository) {
        this.cargoVolumeRepository = cargoVolumeRepository;
    }

    @Transactional(readOnly = true)
    public CargoVolume getCargoVolume(Long value) {
        return cargoVolumeRepository.findById(value).orElseThrow(() -> new BadRequestAlertException(null, "объем вагона " + value + " не найден", CargoVolume.class.getSimpleName(), "notFound"));
    }

    @Transactional(readOnly = true)
    public List<CargoVolume> findAll() {
        return cargoVolumeRepository.findAll();
    }
}
