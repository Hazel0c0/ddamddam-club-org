package kr.co.ddamddam.project.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.service.ApplicantService;
import kr.co.ddamddam.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/project/applicant")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicantApiController {

    private final ApplicantService applicantService;

    /**
     * 프로젝트 참가 신청
     * 게시글에서 신청하기 버튼을 누르면
     * 자동으로 내가 입력했던 포지션을 가져와서 신청
     *
     */
    @PatchMapping("/{projectIdx}")
    private ResponseEntity<?> apply(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @PathVariable Long projectIdx
    ) {

        if (tokenUserInfo.getUserIdx()==null) log.info("토큰 XX");
        log.info("/api/ddamddam/applicant/board={}" , projectIdx);

        try {
            ProjectDetailResponseDTO projectDto = applicantService.apply(tokenUserInfo, projectIdx);

            log.info("신청하기 - {}", projectDto);
            return ResponseEntity.ok().body(projectDto);
        } catch (IllegalStateException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("정원 마감");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("다시 신청해주세요");
        }
    }

    // 신청 취소
    @DeleteMapping("/{userIdx}/{projectIdx}")
    public ApplicationResponse<?> cancel(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @RequestBody @PathVariable Long projectIdx
    ) {
        log.info("/api/ddamddam/applicant/user={}/board={} DELETE !! ", tokenUserInfo.getUserIdx(), projectIdx);

        try {
            applicantService.cancel(tokenUserInfo, projectIdx);
            return ApplicationResponse.ok("DELETE SUCCESS!");
        } catch (Exception e) {
            return ApplicationResponse.error("ERROR");
        }
    }
}

