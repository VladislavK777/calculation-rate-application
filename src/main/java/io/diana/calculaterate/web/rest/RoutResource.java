package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.service.RoutService;
import io.diana.calculaterate.service.dto.ExportModelDTO;
import io.diana.calculaterate.service.dto.InRoutDTO;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/rout")
public class RoutResource {
    private final RoutService routService;

    public RoutResource(RoutService routService) {
        this.routService = routService;
    }

    @GetMapping
    public ResponseEntity<ExportModelDTO> getRout(@ParameterObject InRoutDTO inRoutDTO) {
        return new ResponseEntity<>(routService.getRout(inRoutDTO), OK);
    }

    @PostMapping("/group")
    public ResponseEntity<List<ExportModelDTO>> getGroupRouts(@RequestParam("fileRouts") MultipartFile file) {
        return new ResponseEntity<>(routService.getGroupRouts(file), OK);
    }
}
