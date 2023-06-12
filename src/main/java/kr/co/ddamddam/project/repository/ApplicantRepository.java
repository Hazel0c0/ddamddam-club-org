package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Project,Long> {
}
