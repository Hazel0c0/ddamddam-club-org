package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.ProjectLike;
import kr.co.ddamddam.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {
  ProjectLike findByUserAndProject(User user, Project project);
}