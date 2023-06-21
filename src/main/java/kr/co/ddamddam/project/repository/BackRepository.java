package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackRepository extends JpaRepository<ApplicantOfBack,Long> {
    List<ApplicantOfBack> findByUser(User user);
}
