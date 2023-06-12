package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrontRepository extends JpaRepository<ApplicantOfFront,Long> {
}
