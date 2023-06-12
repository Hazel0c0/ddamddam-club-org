package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import kr.co.ddamddam.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback(false)
class ProjectRepositoryTest {

  @Autowired
  ProjectRepository projectRepository;


  @Test
  @DisplayName("bulk insert")
  void bulkInsert() {
    for (int i = 0; i < 50; i++) {
      projectRepository.save(
          Project.builder()
              .writer("지원" + i)
              .projectTitle("하하호호제목" + i)
              .projectContent("깔깔깔깔내용" + i)
              .projectType("웹페이지")
              .maxFront(3)
              .maxBack(2)
              .memberIdx("1")
              .build()
      );
    }
  }
}