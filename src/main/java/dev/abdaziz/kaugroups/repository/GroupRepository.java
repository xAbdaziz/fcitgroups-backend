package dev.abdaziz.kaugroups.repository;

import dev.abdaziz.kaugroups.model.Course;
import dev.abdaziz.kaugroups.model.Gender;
import dev.abdaziz.kaugroups.model.Group;
import dev.abdaziz.kaugroups.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    Optional<Group> findById(UUID id);
    List<Group> findByUser(User user);
    List<Group> findByCourseAndGender(Course course, Gender gender);
    boolean existsByLink(String link);
    
    @Query("SELECT g FROM Group g WHERE g.course.code = :courseCode AND g.course.number = :courseNumber AND (g.generalGroupMaleAndFemale = true OR g.gender = :gender)")
    List<Group> findByCourseAndGenderOrGeneralForBoth(
        @Param("courseCode") String courseCode, 
        @Param("courseNumber") Integer courseNumber, 
        @Param("gender") Gender gender
    );
}


