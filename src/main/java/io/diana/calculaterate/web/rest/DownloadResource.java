package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.service.dto.ExportModelDTO;
import io.diana.calculaterate.service.export.DownloadFileExcelService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.ContentDisposition.parse;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping("/api/download")
public class DownloadResource {
    private final DownloadFileExcelService downloadFileExcelService;

    public DownloadResource(DownloadFileExcelService downloadFileExcelService) {
        this.downloadFileExcelService = downloadFileExcelService;
    }

    @PostMapping
    public ResponseEntity<ByteArrayResource> downloadRouts(@RequestBody List<ExportModelDTO> exportModelDTOList) throws IOException {
        return new ResponseEntity<>(downloadFileExcelService.downloadFileExcel(exportModelDTOList), getHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/template-group-file")
    public ResponseEntity<ByteArrayResource> downloadTemplateFileGroupRouts() throws IOException {
        return new ResponseEntity<>(downloadFileExcelService.downloadTemplateGroupFileExcel(), getHeaders(), HttpStatus.CREATED);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(parse("attachment; filename=routs_" + LocalDateTime.now() + ".xlsx"));
        headers.setContentType(parseMediaType("application/vnd.ms-excel"));
        return headers;
    }
}
