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

    @Query("SELECT COUNT(g) > 0 FROM Group g WHERE g.course = :course AND g.section = :section AND g.gender = :gender AND (:excludeId IS NULL OR g.id != :excludeId)")
    boolean existsDuplicateSection(@Param("course") Course course, @Param("section") String section, @Param("gender") Gender gender, @Param("excludeId") UUID excludeId);

    @Query("SELECT COUNT(g) > 0 FROM Group g WHERE g.course = :course AND g.gender = :gender AND g.generalGroup = true AND (:excludeId IS NULL OR g.id != :excludeId)")
    boolean existsDuplicateGeneralPerGender(@Param("course") Course course, @Param("gender") Gender gender, @Param("excludeId") UUID excludeId);

    @Query("SELECT COUNT(g) > 0 FROM Group g WHERE g.course = :course AND g.generalGroupMaleAndFemale = true AND (:excludeId IS NULL OR g.id != :excludeId)")
    boolean existsDuplicateGeneralForBoth(@Param("course") Course course, @Param("excludeId") UUID excludeId);
}


