package com.example.mcp_server.web;

import com.example.mcp_server.dto.StudentDto;
import com.example.mcp_server.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService svc;

    public StudentController(StudentService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@Valid @RequestBody StudentDto dto) {
        StudentDto created = svc.create(dto);
        return ResponseEntity.created(URI.create("/api/students/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public StudentDto get(@PathVariable Long id) {
        return svc.get(id);
    }

    @GetMapping
    public List<StudentDto> list() {
        return svc.list();
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable Long id, @Valid @RequestBody StudentDto dto) {
        return svc.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public StudentDto enroll(@PathVariable Long studentId, @PathVariable Long courseId) {
        return svc.enroll(studentId, courseId);
    }
}
