package dev.abdaziz.fcitgroups.repository;

import dev.abdaziz.fcitgroups.model.Course;
import dev.abdaziz.fcitgroups.model.Gender;
import dev.abdaziz.fcitgroups.model.Group;
import dev.abdaziz.fcitgroups.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByUser(User user);
    List<Group> findByCourseAndGender(Course course, Gender gender);
}

