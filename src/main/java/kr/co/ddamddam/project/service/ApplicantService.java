
package kr.co.ddamddam.project.service;

import kr.co.ddamddam.UserUtil;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicantService {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  private final BackRepository backRepository;
  private final FrontRepository frontRepository;

  private final ProjectService projectService;
  private final UserUtil userUtil;

  public ProjectDetailResponseDTO apply(Long userIdx, Long projectIdx) {
    log.info("apply service");
    // 게시글 정보 가져오기
    Project currProject = projectService.getProject(projectIdx);

    // 유저 객체
    User foundUser = userUtil.getUser(userIdx);

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
      }else {
        // 최대 백엔드 지원자 수를 초과한 경우 예외 처리
        throw new IllegalStateException("백엔드 지원자 정원 마감");
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
      } else {
        throw new IllegalStateException("프론트 지원자 정원 마감");
      }
    }

    log.info("백/프론트 currProject : {}", currProject);

    return new ProjectDetailResponseDTO(currProject);
  }



  public void cancel(Long userIdx, Long projectIdx) {
    Project currProject = projectService.getProject(projectIdx);

    User foundUser = userUtil.getUser(userIdx);

    //유저 포지션별 분류
    if (foundUser.getUserPosition() == UserPosition.BACKEND) {
      System.out.println("이 유저는 backend 다");
      backRepository.deleteById(userIdx);
    } else {
      frontRepository.deleteById(userIdx);
    }
  }


}

