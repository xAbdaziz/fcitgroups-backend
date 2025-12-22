package dev.abdaziz.kaugroups.repository;

import dev.abdaziz.kaugroups.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByCode(String code);
    List<Course> findByNumber(int number);
    Optional<Course> findByCodeAndNumber(String code, int number);
}



