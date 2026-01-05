package dev.abdaziz.kaugroups.controller;

import dev.abdaziz.kaugroups.dto.response.CourseSearchResponse;
import dev.abdaziz.kaugroups.model.Course;
import dev.abdaziz.kaugroups.service.CourseSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseSearchService courseSearchService;

    @GetMapping("/search")
    public ResponseEntity<CourseSearchResponse> searchCourses(
            @RequestParam("q") String q,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        List<Course> results = courseSearchService.search(q, Math.min(limit, 100));
        return ResponseEntity.ok(CourseSearchResponse.from(results));
    }
}




