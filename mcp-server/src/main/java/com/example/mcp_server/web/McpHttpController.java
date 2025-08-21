package com.example.mcp_server.web;

import com.example.mcp_server.dto.CourseDto;
import com.example.mcp_server.dto.StudentDto;
import com.example.mcp_server.service.CourseService;
import com.example.mcp_server.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor

//This small controller provides a simple /mcp/tools and /mcp/call
// interface usable by HTTP MCP clients (some clients expect an HTTP discovery).
// It delegates to the same services used by REST and tools — keep both for convenience.
public class McpHttpController {
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/tools")
    public Map<String, Object> tools() {
        return Map.of(
                "name", "education-mcp",
                "version", "1.0.0",
                "methods", new String[]{"listStudents", "addStudent", "listCourses", "addCourse", "enrollStudent"}
        );
    }

    @PostMapping("/call")
    public Object call(@RequestBody Map<String, Object> request) {
        String method = (String) request.get("method");
        Map<String, Object> params = (Map<String, Object>) request.getOrDefault("params", Map.of());

        return switch (method) {
            case "listStudents" -> studentService.list();
            case "addStudent" ->
                    studentService.create(new StudentDto(null, (String) params.get("name"), (String) params.get("email")));
            case "listCourses" -> courseService.list();
            case "addCourse" ->
                    courseService.create(new CourseDto(null, (String) params.get("title"), (String) params.get("description")));
            case "enrollStudent" ->
                    studentService.enroll(Long.valueOf(params.get("studentId").toString()), Long.valueOf(params.get("courseId").toString()));
            default -> Map.of("error", "unknown method");
        };
    }
}