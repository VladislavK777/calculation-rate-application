package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.setting.TurnoverDaySetting;
import io.diana.calculaterate.service.setting.TurnoverDayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/turnover-day-setting")
public class TurnoverDayResource {
    private final TurnoverDayService turnoverDayService;

    public TurnoverDayResource(TurnoverDayService turnoverDayService) {
        this.turnoverDayService = turnoverDayService;
    }

    @GetMapping
    public ResponseEntity<List<TurnoverDaySetting>> getAllTurnoverDay() {
        return new ResponseEntity<>(turnoverDayService.findAll(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoverDaySetting> getTurnoverDay(@PathVariable Long id) {
        return new ResponseEntity<>(turnoverDayService.findById(id), OK);
    }

    @PutMapping
    public ResponseEntity<TurnoverDaySetting> updateTurnoverDay(@RequestBody TurnoverDaySetting turnoverDaySetting) {
        return new ResponseEntity<>(turnoverDayService.update(turnoverDaySetting), OK);
    }
}
