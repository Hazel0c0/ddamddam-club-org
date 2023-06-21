package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
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
import java.util.Optional;

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
      int index = (int) (Math.random() * 4+1); // 0 ~ 5
      Long ran = (long) (Math.random() * 20 + 1);
      User user = userRepository.findById(ran).orElseThrow(() -> {
        throw new NotFoundBoardException(ErrorCode.NOT_FOUND_USER, ran);
      });

      projectRepository.save(
          Project.builder()
              .user(user)
              .projectTitle("11제목" + i + "이다")
              .projectContent("내용" + i)
              .projectType("웹페이지")
              .maxFront(index)
              .maxBack(index)
              .build()
      );
    }
  }
}