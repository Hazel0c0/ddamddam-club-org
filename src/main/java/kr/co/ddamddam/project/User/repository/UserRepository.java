package kr.co.ddamddam.project.User.repository;

import kr.co.ddamddam.project.User.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserProject, Long> {
}
