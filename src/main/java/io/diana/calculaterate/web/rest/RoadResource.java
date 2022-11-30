package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.station.Road;
import io.diana.calculaterate.service.station.RoadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/road")
public class RoadResource {
    private final RoadService roadService;

    public RoadResource(RoadService roadService) {
        this.roadService = roadService;
    }

    @GetMapping
    public ResponseEntity<List<Road>> findAll() {
        return new ResponseEntity<>(roadService.findAll(), OK);
    }
}
