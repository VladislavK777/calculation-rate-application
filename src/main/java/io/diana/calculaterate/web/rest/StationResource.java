package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.station.Station;
import io.diana.calculaterate.service.station.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/station")
public class StationResource {
    private final StationService stationService;

    public StationResource(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<Station> create(@RequestBody Station station) {
        return new ResponseEntity<>(stationService.createOrUpdate(station), OK);
    }

    @PutMapping
    public ResponseEntity<Station> update(@RequestBody Station station) {
        return new ResponseEntity<>(stationService.createOrUpdate(station), OK);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<Station> searchStationById(@PathVariable Long id) {
        return new ResponseEntity<>(stationService.getStationById(id), OK);
    }

    @GetMapping("byCode/{code}")
    public ResponseEntity<Station> searchStationByCode(@PathVariable String code) {
        return new ResponseEntity<>(stationService.getStationByCode(code), OK);
    }

    @GetMapping
    public ResponseEntity<List<Station>> searchStation(@RequestParam(required = false) String filter,
                                                       @RequestParam(required = false) List<Long> roadId) {
        return new ResponseEntity<>(stationService.findStation(filter, roadId), OK);
    }
}
