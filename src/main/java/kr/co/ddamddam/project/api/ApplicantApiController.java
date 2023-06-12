package kr.co.ddamddam.project.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/applicant")
@Slf4j
public class ApplicantApiController {

  private final ApplicantService applicantService;

    /*
          신청하기
          게시글에서 신청하기 버튼을 누르면
          자동으로 내가 입력했던 포지션을 가져와서 신청
     */

  /**
   * 프로젝트 참가 신청
   *
   * @param userIdx : 세션에서 내 (유저)정보 받아올것
   */
  @PatchMapping("/{userIdx}/{projectIdx}")
  private ApplicationResponse<?> apply(
      @RequestBody @PathVariable Long userIdx,
      @RequestBody @PathVariable Long projectIdx
  ) {
    log.info("/api/ddamddam/applicant/user={}/board={}", userIdx, projectIdx);

    try {
      ProjectDetailResponseDTO projectDto = applicantService.apply(userIdx, projectIdx);

      log.info("신청하기 - {}",projectDto);
      return ApplicationResponse.ok(projectDto);
    } catch (Exception e) {
      return ApplicationResponse.bad("다시 신청해주세요");
    }

  }



/*
  // 신청자 방 개별 조회


  public void cancel(UserProject user, Long projectIdx) {


 */
}
//}
