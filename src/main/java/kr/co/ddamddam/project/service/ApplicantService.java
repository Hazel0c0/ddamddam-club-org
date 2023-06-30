
package kr.co.ddamddam.project.service;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.project.UserUtil;
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

import java.util.Optional;

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
    private final ValidateToken validateToken;
    private final UserUtil userUtil;

    public ProjectDetailResponseDTO apply(TokenUserInfo tokenUserInfo, Long projectIdx) throws IllegalStateException {
        log.info("apply service");

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        // 게시글 정보 가져오기
        Project currProject = projectService.getProject(projectIdx);
        // 유저 객체
        User foundUser = userUtil.getUser(userIdx);

        // 게시글 작성자와 로그인 한 유저가 동일할 경우 예외처리 - 이메일로 검사합니다.
        if (currProject.getUser().getUserEmail().equals(
            tokenUserInfo.getUserEmail())
        ) {
            throw new UnauthorizationException(ErrorCode.ACCESS_FORBIDDEN, tokenUserInfo.getUserEmail());
        }

        //유저 포지션별 분류
        if (foundUser.getUserPosition() == UserPosition.BACKEND) {
            System.out.println("이 유저는 backend 다");
            if (backRepository.existsByProjectAndUser(currProject, foundUser)) {
                throw new IllegalStateException("이미 신청되었습니다");
            } else if (currProject.getApplicantOfBacks().size() < currProject.getMaxBack()) {
                log.info("남은자리 {}, MaxBack {}", currProject.getApplicantOfBacks().size(), currProject.getMaxBack());

                currProject.addBack(backRepository.save(
                    ApplicantOfBack.builder()
                        .user(foundUser)
                        .project(currProject)
                        .build()
                ));
            } else {
                // 최대 백엔드 지원자 수를 초과한 경우 예외 처리
                throw new IllegalStateException("백엔드 지원자의 정원이 마감되었습니다");
            }
        } else {
            System.out.println("이 유저는 front 다");
            if (frontRepository.existsByProjectAndUser(currProject, foundUser)) {
                throw new IllegalStateException("이미 신청되었습니다");
            } else if (currProject.getApplicantOfFronts().size() < currProject.getMaxFront()) {
                log.info("남은자리 {}, maxFront {}", currProject.getApplicantOfFronts().size(), currProject.getMaxFront());
                currProject.addFront(frontRepository.save(
                    ApplicantOfFront.builder()
                        .user(foundUser)
                        .project(currProject)
                        .build()
                ));
            } else {
                throw new IllegalStateException("프론트 지원자의 정원이 마감되었습니다");
            }
        }

        log.info("백/프론트 currProject : {}", currProject);

        return new ProjectDetailResponseDTO(currProject);
    }

    private User getUser(Long userIdx) {
        User foundUser = userRepository.findById(userIdx)
            .orElseThrow(
                () -> new RuntimeException(
                    userIdx + "번 유저 없음!"
                )
            );
        log.info("foundUser : {}", foundUser);
        return foundUser;
    }

    // TODO : 취소 처리 전에 로그인 유저가 해당 프로젝트에 신청이 되어있는 상태인지 검사하는 로직이 필요합니다. - 예원
    public void cancel(TokenUserInfo tokenUserInfo, Long projectIdx) {
        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());
        User foundUser = userUtil.getUser(userIdx);

        Project currProject = projectService.getProject(projectIdx);
        //유저 포지션별 분류

                log.info("신청 취소한 유저 포지션 : {} ", foundUser.getUserPosition());
        if (foundUser.getUserPosition() == UserPosition.BACKEND) {
            boolean isBack = backRepository.existsByProjectAndUser(currProject, foundUser);
            if (isBack) {
                log.info("프로젝트에 신청 했다면 ?  {} ", isBack);
                backRepository.deleteByUser(foundUser);
            } else {
                throw new IllegalStateException("신청하지 않은 프로젝트입니다");
            }
        } else {
            boolean isFront = frontRepository.existsByProjectAndUser(currProject, foundUser);
            if (isFront) {
                log.info("프로젝트에 신청 했다면 ?  {} ", isFront);
                frontRepository.deleteByUser(foundUser);
            } else {
                throw new IllegalStateException("신청하지 않은 프로젝트입니다");
            }
        }
    }


}

