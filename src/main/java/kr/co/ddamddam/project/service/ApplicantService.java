
package kr.co.ddamddam.project.service;

import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.project.repository.ApplicantRepository;
import kr.co.ddamddam.project.repository.BackRepository;
import kr.co.ddamddam.project.repository.FrontRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicantService {

  private final ApplicantRepository applicantRepository;

  private final BackRepository backRepository;
  private final FrontRepository frontRepository;
  private final ProjectRepository projectRepository;
  private final ProjectService projectService;
  private final UserRepository userRepository;

  public ProjectDetailResponseDTO apply(Long userIdx, Long projectIdx) {
    log.info("apply service");
    // 게시글 정보 가져오기
    Project currProject = projectService.getProject(projectIdx);

    // 유저 객체
    User foundUser = userRepository.findById(userIdx)
        .orElseThrow(
            () -> new RuntimeException(
                userIdx + "번 유저 없음!"
            )
        );
    log.info("foundUser : {}", foundUser);

    //유저 포지션별 분류
    if (foundUser.getUserPosition() == UserPosition.BACKEND) {
      System.out.println("이 유저는 backend 다");
      ApplicantOfBack save = backRepository.save(
          ApplicantOfBack.builder()
              .userIdx(userIdx)
              .project(currProject)
              .build()
      );
      currProject.addBack(save);
    } else {
      System.out.println("이 유저는 front 다");
      currProject.addFront(frontRepository.save(
          ApplicantOfFront.builder()
              .userIdx(userIdx)
              .project(currProject)
              .build()
      ));
    }

    log.info("백/프론트 currProject : {}", currProject);

    return new ProjectDetailResponseDTO(currProject);
  }


  // 신청자 방 개별 조회


  public void cancel(User user, Long projectIdx) {


  }


}

