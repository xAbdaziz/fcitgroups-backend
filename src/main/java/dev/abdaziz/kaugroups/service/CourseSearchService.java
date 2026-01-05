package dev.abdaziz.kaugroups.service;

import dev.abdaziz.kaugroups.model.Course;
import dev.abdaziz.kaugroups.repository.CourseRepository;
import dev.abdaziz.kaugroups.util.ArabicNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CourseSearchService {

    private final CourseRepository courseRepository;

    private static final Pattern CODE_NUMBER_PATTERN = Pattern.compile("^([A-Za-z]+)(\\d+)$");
    private static final Pattern CODE_ONLY_PATTERN = Pattern.compile("^[A-Za-z]+$");
    private static final Pattern NUMBER_ONLY_PATTERN = Pattern.compile("^\\d+$");

    public List<Course> search(String query, int limit) {
        if (query == null || query.isBlank()) {
            return List.of();
        }

        String normalized = query.replaceAll("[-–—]", " ").replaceAll("\\s+", " ").trim();
        String[] tokens = normalized.split(" ");

        String code = null;
        Integer number = null;
        StringBuilder nameBuilder = new StringBuilder();

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            Matcher combinedMatcher = CODE_NUMBER_PATTERN.matcher(token);
            if (combinedMatcher.matches()) {
                code = combinedMatcher.group(1).toUpperCase();
                number = Integer.parseInt(combinedMatcher.group(2));
                continue;
            }

            if (CODE_ONLY_PATTERN.matcher(token).matches()) {
                code = token.toUpperCase();
                continue;
            }

            if (code != null && NUMBER_ONLY_PATTERN.matcher(token).matches()) {
                number = Integer.parseInt(token);
                continue;
            }

            if (!nameBuilder.isEmpty()) nameBuilder.append(" ");
            nameBuilder.append(token);
        }

        String nameQuery = nameBuilder.toString();
        String normalizedNameQuery = ArabicNormalizer.normalize(nameQuery);

        final String finalCode = code;
        final Integer finalNumber = number;

        return courseRepository.findAll().stream().sorted(Comparator.comparingInt(Course::getNumber))
                .filter(course -> {
                    if (finalCode != null && !course.getCode().equalsIgnoreCase(finalCode)) {
                        return false;
                    }
                    if (finalNumber != null && course.getNumber() != finalNumber) {
                        return false;
                    }
                    if (!normalizedNameQuery.isEmpty()) {
                        String normalizedCourseName = ArabicNormalizer.normalize(course.getName());
                        return normalizedCourseName.contains(normalizedNameQuery);
                    }
                    return true;
                })
                .limit(limit)
                .toList();
    }
}
