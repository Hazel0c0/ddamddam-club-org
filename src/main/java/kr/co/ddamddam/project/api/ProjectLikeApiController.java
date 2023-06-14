package kr.co.ddamddam.project.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.project.service.ProjectLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/project/like")
@Slf4j
public class ProjectLikeApiController {

  /*
   * 사이드 프로젝트 - 좋아요 기능
   * 한 게시물에 좋아요 1번만 가능
   */

  private final ProjectLikeService projectLikeService;

  /**
   *
   * @param userIdx : 세션에서 내(사용자)정보 받아올 예정
   * @param projectIdx : 좋아요 누른 게시글 번호
   */

  @PostMapping("/{userIdx}/{projectIdx}")
  public ApplicationResponse<?> handleLike(
      @PathVariable Long userIdx,
      @PathVariable Long projectIdx
  ) {
    try {
      log.info("좋아요 click : userIdx={}, projectIdx={}",userIdx,projectIdx);
      boolean isLiked = projectLikeService.checkIfLiked(userIdx, projectIdx);
      if (isLiked) {
        projectLikeService.handleLike(userIdx, projectIdx);
        return ApplicationResponse.ok("좋아요가 취소되었습니다.");
      } else {
        projectLikeService.handleLike(userIdx, projectIdx);
        return ApplicationResponse.ok("좋아요가 올라갔습니다.");
      }
    } catch (Exception e) {
      return ApplicationResponse.error("좋아요 처리 중 오류가 발생했습니다.");
    }
  }
}
