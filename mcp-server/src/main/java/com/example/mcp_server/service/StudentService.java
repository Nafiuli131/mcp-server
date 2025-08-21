package com.example.mcp_server.service;

import com.example.mcp_server.dto.StudentDto;
import com.example.mcp_server.entity.Course;
import com.example.mcp_server.entity.Student;
import com.example.mcp_server.repository.CourseRepository;
import com.example.mcp_server.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {
    private final StudentRepository students;
    private final CourseRepository courses;

    public StudentService(StudentRepository students, CourseRepository courses) {
        this.students = students;
        this.courses = courses;
    }

    public StudentDto create(StudentDto dto) {
        students.findByEmail(dto.getEmail()).ifPresent(s -> { throw new IllegalArgumentException("email exists"); });
        Student s = Student.builder().name(dto.getName()).email(dto.getEmail()).build();
        return toDto(students.save(s));
    }

    public StudentDto get(Long id) {
        return students.findById(id).map(this::toDto).orElseThrow(() -> new IllegalArgumentException("student not found"));
    }

    @Transactional(readOnly = true)
    public List<StudentDto> list() {
        return students.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public StudentDto update(Long id, StudentDto dto) {
        Student s = students.findById(id).orElseThrow(() -> new IllegalArgumentException("student not found"));
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        return toDto(s);
    }

    public void delete(Long id) {
        students.deleteById(id);
    }

    public StudentDto enroll(Long studentId, Long courseId) {
        Student s = students.findById(studentId).orElseThrow(() -> new IllegalArgumentException("student not found"));
        Course c = courses.findById(courseId).orElseThrow(() -> new IllegalArgumentException("course not found"));
        s.getCourses().add(c);
        c.getStudents().add(s);
        return toDto(students.save(s));
    }

    private StudentDto toDto(Student s) {
        return new StudentDto(s.getId(), s.getName(), s.getEmail());
    }
}
