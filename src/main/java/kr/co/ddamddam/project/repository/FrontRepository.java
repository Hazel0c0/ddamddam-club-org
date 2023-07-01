package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FrontRepository extends JpaRepository<ApplicantOfFront,Long> {
    Page<ApplicantOfFront> findByUser(User user, Pageable pageable);
    boolean existsByProjectAndUser(Project project, User user);

    @Modifying
    @Query("DELETE FROM ApplicantOfFront f WHERE f.project = :project AND f.user = :user")
    void deleteByProjectAndUser(@Param("project") Project project, @Param("user") User user);
}
