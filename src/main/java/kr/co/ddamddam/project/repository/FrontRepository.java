package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrontRepository extends JpaRepository<ApplicantOfFront,Long> {
    Page<ApplicantOfFront> findByUser(User user, Pageable pageable);
}
