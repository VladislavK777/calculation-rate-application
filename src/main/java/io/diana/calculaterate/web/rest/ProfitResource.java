package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.setting.ProfitSetting;
import io.diana.calculaterate.service.setting.ProfitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/profit-setting")
public class ProfitResource {
    private final ProfitService profitService;

    public ProfitResource(ProfitService profitService) {
        this.profitService = profitService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfitSetting> getProfitById(@PathVariable Long id) {
        return new ResponseEntity<>(profitService.findById(id), OK);
    }

    @GetMapping
    public ResponseEntity<List<ProfitSetting>> getAllProfit() {
        return new ResponseEntity<>(profitService.findAll(), OK);
    }

    @PutMapping
    public ResponseEntity<ProfitSetting> updateProfit(@RequestBody ProfitSetting profitSetting) {
        return new ResponseEntity<>(profitService.update(profitSetting), OK);
    }
}
