package com.example.mcp_server.service;

import com.example.mcp_server.dto.CourseDto;
import com.example.mcp_server.entity.Course;
import com.example.mcp_server.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {
    private final CourseRepository repo;

    public CourseService(CourseRepository repo) {
        this.repo = repo;
    }

    public CourseDto create(CourseDto dto) {
        repo.findByTitle(dto.getTitle()).ifPresent(c -> {
            throw new IllegalArgumentException("course title already exists");
        });
        Course c = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        return toDto(repo.save(c));
    }

    public CourseDto get(Long id) {
        return repo.findById(id).map(this::toDto).orElseThrow(() -> new IllegalArgumentException("course not found"));
    }

    @Transactional(readOnly = true)
    public List<CourseDto> list() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CourseDto update(Long id, CourseDto dto) {
        Course c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("course not found"));
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        return toDto(c);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private CourseDto toDto(Course c) {
        return new CourseDto(c.getId(), c.getTitle(), c.getDescription());
    }
}
