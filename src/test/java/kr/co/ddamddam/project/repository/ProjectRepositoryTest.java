package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.project.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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
              .writer("지원"+i)
              .projectTitle("하하호호제목" + i)
              .projectContent("깔깔깔깔내용" + i)
              .projectType("웹페이지")
              .maxFront(3)
              .maxBack(2)
              .build()
      );
    }
  }
}