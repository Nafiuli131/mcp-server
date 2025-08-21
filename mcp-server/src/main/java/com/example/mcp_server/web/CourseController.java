package com.example.mcp_server.web;

import com.example.mcp_server.dto.CourseDto;
import com.example.mcp_server.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService svc;

    public CourseController(CourseService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@Valid @RequestBody CourseDto dto) {
        CourseDto created = svc.create(dto);
        return ResponseEntity.created(URI.create("/api/courses/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public CourseDto get(@PathVariable Long id) {
        return svc.get(id);
    }

    @GetMapping
    public List<CourseDto> list() {
        return svc.list();
    }

    @PutMapping("/{id}")
    public CourseDto update(@PathVariable Long id, @Valid @RequestBody CourseDto dto) {
        return svc.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}
