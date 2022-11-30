package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.cargo.CargoVolume;
import io.diana.calculaterate.service.cargo.CargoVolumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cargo-volume")
public class CargoVolumeResource {
    private final CargoVolumeService cargoVolumeService;

    public CargoVolumeResource(CargoVolumeService cargoVolumeService) {
        this.cargoVolumeService = cargoVolumeService;
    }

    @GetMapping
    public ResponseEntity<List<CargoVolume>> findAll() {
        return new ResponseEntity<>(cargoVolumeService.findAll(), OK);
    }
}
