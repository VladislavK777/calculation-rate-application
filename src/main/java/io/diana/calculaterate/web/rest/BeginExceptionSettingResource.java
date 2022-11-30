package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.setting.BeginExceptionSetting;
import io.diana.calculaterate.security.AuthoritiesConstants;
import io.diana.calculaterate.service.setting.BeginExceptionSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/begin-exception-setting")
public class BeginExceptionSettingResource {
    private final BeginExceptionSettingService beginExceptionSettingService;

    public BeginExceptionSettingResource(BeginExceptionSettingService beginExceptionSettingService) {
        this.beginExceptionSettingService = beginExceptionSettingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeginExceptionSetting> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(beginExceptionSettingService.getOne(id), OK);
    }

    @GetMapping
    public ResponseEntity<Set<BeginExceptionSetting>> findAll() {
        return new ResponseEntity<>(beginExceptionSettingService.findAll(), OK);
    }

    @PostMapping
    public ResponseEntity<BeginExceptionSetting> create(@RequestBody BeginExceptionSetting beginExceptionSetting) {
        return new ResponseEntity<>(beginExceptionSettingService.createOrUpdate(beginExceptionSetting), OK);
    }

    @PutMapping
    public ResponseEntity<BeginExceptionSetting> update(@RequestBody BeginExceptionSetting beginExceptionSetting) {
        return new ResponseEntity<>(beginExceptionSettingService.createOrUpdate(beginExceptionSetting), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(beginExceptionSettingService.delete(id), OK);
    }
}
