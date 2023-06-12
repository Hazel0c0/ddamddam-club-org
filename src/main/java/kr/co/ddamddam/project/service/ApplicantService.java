package kr.co.ddamddam.project.service;

import kr.co.ddamddam.project.User.repository.DdamDdamUserRepository;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
//import kr.co.ddamddam.project.entity.applicant.Apply;
import kr.co.ddamddam.project.User.UserProject;
//import kr.co.ddamddam.project.repository.ApplicantRepository;
import kr.co.ddamddam.project.repository.ApplicantRepository;
import kr.co.ddamddam.project.repository.BackRepository;
import kr.co.ddamddam.project.repository.FrontRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicantService {

  private final ApplicantRepository applicantRepository;

  private final DdamDdamUserRepository ddamDdamUserRepository;
  private final BackRepository backRepository;
  private final FrontRepository frontRepository;
  private final ProjectRepository projectRepository;
  private final ProjectService projectService;

  public void apply(Long userIdx, Long projectIdx) {
    log.info("apply service");
    // 게시글 정보 가져오기
    Project currProject = projectService.getProject(projectIdx);

    applicantRepository.save(
        Project.builder()
            .build()
    );

    log.info("프로젝트만 담은 apply : {}");

    // 유저 객체
    UserProject foundUser = ddamDdamUserRepository.getById(userIdx);
    log.info("foundUser : {}", foundUser);

    //유저 포지션별 분류
    if (foundUser.getUserPosition() == UserPosition.BACKEND) {
      System.out.println("이 유저는 backend 다");
      ApplicantOfBack back = backRepository.save(
          ApplicantOfBack.builder()
              .userIdx(userIdx)
              .build()
      );
//      apply.addBack(back);
    } else {
      System.out.println("이 유저는 front 다");

      ApplicantOfFront front = frontRepository.save(
          ApplicantOfFront.builder()
              .userIdx(userIdx)
              .build()
      );
//      apply.addFront(front);
    }

//    log.info("백/프론트 apply : {}", apply);

//    return apply;
  }


  // 신청자 방 개별 조회


  public void cancel(UserProject user, Long projectIdx) {


  }


}
