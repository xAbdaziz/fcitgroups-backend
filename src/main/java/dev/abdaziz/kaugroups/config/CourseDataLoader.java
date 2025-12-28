package dev.abdaziz.kaugroups.config;

import dev.abdaziz.kaugroups.model.Course;
import dev.abdaziz.kaugroups.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CourseDataLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;

    @Value("${app.data.load-courses:false}")
    private boolean loadCourses;

    @Override
    public void run(String... args) throws Exception {
        if (!loadCourses) {
            log.debug("Course loading disabled. Set app.data.load-courses=true to enable.");
            return;
        }

        try (InputStream inputStream = new ClassPathResource("data/courses.json").getInputStream()) {
            List<Map<String, String>> coursesData = new ObjectMapper().readValue(
                    inputStream,
                    new TypeReference<>() {}
            );

            Set<String> existingKeys = courseRepository.findAll().stream()
                    .map(c -> c.getCode() + "-" + c.getNumber())
                    .collect(Collectors.toSet());

            List<Course> newCourses = coursesData.stream()
                    .filter(data -> !existingKeys.contains(data.get("code") + "-" + data.get("number")))
                    .map(data -> Course.builder()
                            .code(data.get("code"))
                            .number(Integer.parseInt(data.get("number")))
                            .name(data.get("name"))
                            .build())
                    .toList();

            if (!newCourses.isEmpty()) {
                courseRepository.saveAll(newCourses);
                log.info("Loaded {} new courses.", newCourses.size());
            }

        } catch (Exception e) {
            log.error("Failed to load courses: {}", e.getMessage(), e);
            throw e;
        }
    }
}

