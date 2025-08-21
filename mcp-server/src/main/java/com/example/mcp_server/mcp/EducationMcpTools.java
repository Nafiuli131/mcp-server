package com.example.mcp_server.mcp;

import com.example.mcp_server.dto.CourseDto;
import com.example.mcp_server.dto.StudentDto;
import com.example.mcp_server.service.CourseService;
import com.example.mcp_server.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

@Service
public class EducationMcpTools {

    private final StudentService studentService;
    private final CourseService courseService;

    public EducationMcpTools(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Tool(description = "List all students")
    public List<StudentDto> listStudents() {
        return studentService.list();
    }

    @Tool(description = "Create a student with name and email")
    public StudentDto addStudent(
            @ToolParam(description = "Full name of student") String name,
            @ToolParam(description = "Email address") String email) {
        return studentService.create(new StudentDto(null, name, email));
    }

    @Tool(description = "List all courses")
    public List<CourseDto> listCourses() {
        return courseService.list();
    }

    @Tool(description = "Create a course with title and description")
    public CourseDto addCourse(
            @ToolParam(description = "Course title") String title,
            @ToolParam(description = "Course description") String description) {
        return courseService.create(new CourseDto(null, title, description));
    }

    @Tool(description = "Enroll a student into a course by IDs")
    public StudentDto enrollStudent(
            @ToolParam(description = "Student ID") Long studentId,
            @ToolParam(description = "Course ID") Long courseId) {
        return studentService.enroll(studentId, courseId);
    }
}
