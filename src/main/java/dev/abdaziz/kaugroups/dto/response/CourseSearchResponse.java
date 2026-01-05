package dev.abdaziz.kaugroups.dto.response;

import dev.abdaziz.kaugroups.model.Course;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CourseSearchResponse(
        List<CourseItem> courses,
        int count
) {
    @Builder
    public record CourseItem(
            UUID id,
            String name,
            String code,
            int number
    ) {
        public static CourseItem from(Course course) {
            return CourseItem.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .code(course.getCode())
                    .number(course.getNumber())
                    .build();
        }
    }

    public static CourseSearchResponse from(List<Course> courses) {
        List<CourseItem> items = courses.stream()
                .map(CourseItem::from)
                .toList();
        return CourseSearchResponse.builder()
                .courses(items)
                .count(items.size())
                .build();
    }
}




