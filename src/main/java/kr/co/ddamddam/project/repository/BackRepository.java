package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackRepository extends JpaRepository<ApplicantOfBack,Long> {
}
