package com.spe.peerpulse.repository;

import com.spe.peerpulse.entity.User;
import com.spe.peerpulse.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE id IN (SELECT student_id FROM student_group_map WHERE group_id = ?1)", nativeQuery = true)
    List<User> findStudentsInGroup(Long groupId);

    @Query(value = "SELECT * FROM user WHERE role = 'STUDENT' AND id NOT IN (SELECT DISTINCT student_id FROM student_group_map WHERE group_id in (SELECT class_group_id FROM class_group WHERE class_id = ?1))", nativeQuery = true)
    List<User> findStudentsNotInGroup(Long classId);

    List<User> findByRole(Role role);

    @Query(value = "SELECT * FROM user WHERE id IN (SELECT student_id FROM student_group_map WHERE group_id IN (SELECT DISTINCT group_id FROM class_group, student_group_map WHERE class_group_id = group_id AND student_id = ?1 AND class_id = ?2)) AND id != ?1", nativeQuery = true)
    List<User> getGroupMembersInClass(Long studentId, Long classId);
}
