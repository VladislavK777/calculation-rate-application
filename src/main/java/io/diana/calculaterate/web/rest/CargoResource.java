package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.cargo.Cargo;
import io.diana.calculaterate.service.cargo.CargoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cargo")
public class CargoResource {
    private final CargoService cargoService;

    public CargoResource(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<Cargo>> searchCargo(@RequestParam(required = false) String filter) {
        return new ResponseEntity<>(cargoService.findCargo(filter), OK);
    }
}
