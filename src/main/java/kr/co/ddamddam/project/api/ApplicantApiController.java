package kr.co.ddamddam.project.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.project.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/applicant")
@Slf4j
public class ApplicantApiController {

    /*
          신청하기
          게시글에서 신청하기 버튼을 누르면
          자동으로 내가 입력했던 포지션을 가져와서 신청이 됨
     */

    /**
     * 프로젝트 참가 신청
     * @param user : 세션에서 내 정보 받아올것
     */
    @GetMapping
    private ApplicationResponse<?> apply(User user){

        return null;
    }
}
