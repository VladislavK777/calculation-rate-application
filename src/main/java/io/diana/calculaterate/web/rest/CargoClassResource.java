package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.cargo.CargoClass;
import io.diana.calculaterate.service.cargo.CargoClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cargo-class")
public class CargoClassResource {
    private final CargoClassService cargoClassService;

    public CargoClassResource(CargoClassService cargoClassService) {
        this.cargoClassService = cargoClassService;
    }

    @GetMapping
    public ResponseEntity<List<CargoClass>> findAll() {
        return new ResponseEntity<>(cargoClassService.findAll(), OK);
    }
}
