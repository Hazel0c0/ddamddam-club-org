package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrontRepository extends JpaRepository<ApplicantOfFront,Long> {
    List<ApplicantOfFront> findByUser(User user);
}
