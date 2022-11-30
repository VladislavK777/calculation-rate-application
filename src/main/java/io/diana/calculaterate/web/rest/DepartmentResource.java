package io.diana.calculaterate.web.rest;

import io.diana.calculaterate.domain.station.Department;
import io.diana.calculaterate.service.station.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/department")
public class DepartmentResource {
    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> searchDepartment(@RequestParam(required = false) String filter,
                                                             @RequestParam(required = false) List<Long> roadId) {
        return new ResponseEntity<>(departmentService.findDepartment(filter, roadId), OK);
    }

    @GetMapping("/{roadId}")
    public ResponseEntity<List<Department>> searchDepartmentByRoadId(@PathVariable Long roadId) {
        return new ResponseEntity<>(departmentService.findDepartmentByRoadId(roadId), OK);
    }
}
