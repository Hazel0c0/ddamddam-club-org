package kr.co.ddamddam.project.api;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import kr.co.ddamddam.project.UserUtil;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.project.repository.BackRepository;
import kr.co.ddamddam.project.repository.FrontRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.project.service.ProjectService;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ApplicantApiControllerTest {
  @Autowired
  ApplicantApiController applicantApiController;
  @Autowired
  BackRepository backRepository;
  @Autowired
  FrontRepository frontRepository;
  @Autowired
  ProjectService projectService;
  @Autowired
  UserUtil userUtil;

  @Test
  @DisplayName("apply !!")
  void bulkInsert() {
    for (int i = 0; i < 1; i++) {

      Long userIdx = (long) (Math.random() * 3 + 1);
      Long projectIdx = (long) (Math.random() * 101 + 1);

      Project currProject = projectService.getProject(projectIdx);
      User foundUser = userUtil.getUser(userIdx);

      // 게시글 작성자와 로그인 한 유저가 동일할 경우 예외처리 - 이메일로 검사합니다.
      if ((currProject.getUser().getUserIdx()!=userIdx)) {
        System.out.println("작성자: "+currProject.getUser().getUserIdx()+" == 로그인 유저 : "+userIdx);

        //유저 포지션별 분류
        if (foundUser.getUserPosition() == UserPosition.BACKEND) {
          System.out.println("이 유저는 backend 다");
          if (currProject.getApplicantOfBacks().size() < currProject.getMaxBack()) {

            currProject.addBack(backRepository.save(
                ApplicantOfBack.builder()
                    .user(foundUser)
                    .project(currProject)
                    .build()
            ));
          }
        } else {
          System.out.println("이 유저는 front 다");
          if (currProject.getApplicantOfFronts().size() < currProject.getMaxFront()) {
            currProject.addFront(frontRepository.save(
                ApplicantOfFront.builder()
                    .user(foundUser)
                    .project(currProject)
                    .build()
            ));
          }
        }
      }
    }
  }
}