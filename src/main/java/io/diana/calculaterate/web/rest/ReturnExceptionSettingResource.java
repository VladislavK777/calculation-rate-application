package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.setting.ReturnExceptionSetting;
import io.diana.calculaterate.service.setting.ReturnExceptionSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/return-exception-setting")
public class ReturnExceptionSettingResource {
    private final ReturnExceptionSettingService returnExceptionSettingService;

    public ReturnExceptionSettingResource(ReturnExceptionSettingService returnExceptionSettingService) {
        this.returnExceptionSettingService = returnExceptionSettingService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReturnExceptionSetting> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(returnExceptionSettingService.getOne(id), OK);
    }

    @GetMapping
    public ResponseEntity<Set<ReturnExceptionSetting>> findAll() {
        return new ResponseEntity<>(returnExceptionSettingService.findAll(), OK);
    }

    @PostMapping
    public ResponseEntity<ReturnExceptionSetting> create(@RequestBody ReturnExceptionSetting returnExceptionSetting) {
        return new ResponseEntity<>(returnExceptionSettingService.createOrUpdate(returnExceptionSetting), OK);
    }

    @PutMapping
    public ResponseEntity<ReturnExceptionSetting> update(@RequestBody ReturnExceptionSetting returnExceptionSetting) {
        return new ResponseEntity<>(returnExceptionSettingService.createOrUpdate(returnExceptionSetting), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(returnExceptionSettingService.delete(id), OK);
    }
}
