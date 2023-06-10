package kr.co.ddamddam.project.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.project.User.UserProject;
import kr.co.ddamddam.project.User.repository.UserRepository;
import kr.co.ddamddam.project.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/applicant")
@Slf4j
public class ApplicantApiController {

    private final ApplicantService applicantService;

    /*
          신청하기
          게시글에서 신청하기 버튼을 누르면
          자동으로 내가 입력했던 포지션을 가져와서 신청이 됨
     */

    /**
     * 프로젝트 참가 신청
     * @param userIdx : 세션에서 내 (유저)정보 받아올것
     */
    @GetMapping("/{userIdx}/{projectIdx}")
    private ApplicationResponse<?> apply(
        @PathVariable Long userIdx,
        @PathVariable Long projectIdx
    ){
        log.info("/api/ddamddam/applicant/user={}/board={}",userIdx,projectIdx);

        applicantService.apply(userIdx,projectIdx);

        return null;
    }


    // 신청자 방 개별 조회


    public void cancel(UserProject user, Long projectIdx) {


    }

}
