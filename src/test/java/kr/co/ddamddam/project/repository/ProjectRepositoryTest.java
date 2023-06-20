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

  @Autowired
  UserRepository userRepository;


  @Test
  @DisplayName("bulk insert")
  void bulkInsert() {
    for (int i = 1; i < 20; i++) {
      int index = (int) (Math.random() * 4); // 0 ~ 5
      Long ran = (long) (Math.random() * 20 + 1);

      projectRepository.save(
          Project.builder()
              .user(
                  userRepository.findById(ran)
                      .orElseThrow()
              )
              .projectTitle("제목" + i + "이다")
              .projectContent("내용" + i)
              .projectType("웹페이지")
              .maxFront(index)
              .maxBack(index)
              .build()
      );
    }
  }
}