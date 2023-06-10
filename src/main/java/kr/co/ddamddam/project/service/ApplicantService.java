package kr.co.ddamddam.project.service;

import kr.co.ddamddam.project.User.repository.UserRepository;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.project.entity.applicant.Apply;
import kr.co.ddamddam.project.User.UserProject;
import kr.co.ddamddam.project.repository.ApplicantRepository;
import kr.co.ddamddam.project.repository.BackRepository;
import kr.co.ddamddam.project.repository.FrontRepository;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicantService {

  private final ApplicantRepository applicantRepository;

  private final UserRepository userRepository;
  private final BackRepository backRepository;
  private final FrontRepository frontRepository;


  public void apply(Long userIdx, Long projectIdx) {
    // 게시글 정보 담기
    Apply.builder()
        .projectIdx(projectIdx).build();

  // 유저 객체
    UserProject foundUser = userRepository.getById(userIdx);

    //유저 포지션별 분류
    if (foundUser.getUserPosition() == UserPosition.BACKEND) {
      ApplicantOfBack back=backRepository.save(
          ApplicantOfBack.builder()
              .userIdx(userIdx)
              .build()
      );
    } else {
      ApplicantOfFront front=frontRepository.save(
          ApplicantOfFront.builder()
              .userIdx(userIdx)
              .build()
      );

    }
  }

  // 신청자 방 개별 조회


  public void cancel(UserProject user, Long projectIdx) {


  }


}
