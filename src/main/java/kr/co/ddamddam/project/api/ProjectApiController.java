package kr.co.ddamddam.project.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.project.dto.page.PageDTO;
import kr.co.ddamddam.project.dto.request.ProjectModifyRequestDTO;
import kr.co.ddamddam.project.dto.request.ProjectSearchRequestDto;
import kr.co.ddamddam.project.dto.request.ProjectWriteDTO;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListPageResponseDTO;
import kr.co.ddamddam.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import kr.co.ddamddam.upload.UploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/project")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectApiController {
  /*
    전체 게시글 조회 :  [GET] findAll()    -/project
    게시글 상세 조회 :  [GET] getDetail()  -/project/{idx}
    게시글 작성 :      [POST] write()     -/project
    게시글 수정 :      [PATCH] modify()   -/project/{idx}
    게시글 삭제 :      [DELETE] delete()  -/project/{idx}
    게시글 검색 :      [GET]
   */

  private final ProjectService projectService;
  private final UploadService uploadService;


  /**
   * 게시글 전체 조회
   *
   * @param pageDTO : 페이지 정보
   * @return : 페이징 처리 된 프로젝트 리스트 정보
   * <p>
   * 기본 조회 (조회순) - keyword 입력 없음
   * 좋아요 순 조회 - keyword : like
   * 프론트 / 백 조회 : keyword : front/back
   */
  @GetMapping
  private ApplicationResponse<ProjectListPageResponseDTO> getList(
      PageDTO pageDTO,
      ProjectSearchRequestDto searchRequestDto
  ) {
    log.info("/api/ddamddam/page={}$size={}", pageDTO.getPage(), pageDTO.getSize());

    ProjectListPageResponseDTO dto = projectService.getList(pageDTO, searchRequestDto);
//    log.info("dto의 값 : {}",dto);
    return ApplicationResponse.ok(dto);
  }

  // 게시글 상세 보기
  @GetMapping("/{projectIdx}")
  public ApplicationResponse<?> getDetail(@PathVariable Long projectIdx) {
    log.info("/api/ddamddam/{} GET", projectIdx);

    try {
      ProjectDetailResponseDTO dto = projectService.getDetail(projectIdx);

      return ApplicationResponse.ok(dto);

    } catch (Exception e) {
      return ApplicationResponse.bad(e.getMessage());
    }
  }


  /**
   * 게시글 작성
   * @param dto
   * @param projectImg
   * @return
   */
  @Parameters({
      @Parameter(name = "boardTitle", description = "제목을 입력하세요", example = "제목을 입력하세요", required = true)
      , @Parameter(name = "boardContent", description = "내용을 입력하세요", example = "내용을 입력하세요", required = true)
      , @Parameter(name = "projectType", description = "프로젝트 타입을 입력하세요", example = "프로젝트 타입을 입력하세요", required = true)
  })
  @PostMapping
  public ApplicationResponse<?> write(
      @Validated @RequestPart("project") ProjectWriteDTO dto,
      @RequestPart(
          value = "projectImage",
          required = false) MultipartFile projectImg
  ) {

    log.info("/api/ddamddam write POST!! - dto: {}  \n img : {}", dto,projectImg);

    if (dto == null) {
      return ApplicationResponse.bad("게시글 정보를 전달해주세요");
    }

    try {
      String uploadedFilePath = fileUpload(projectImg);

      ProjectDetailResponseDTO responseDTO = projectService.write(dto,uploadedFilePath);
      return ApplicationResponse.ok(responseDTO);

    } catch (RuntimeException e) {
      e.printStackTrace();
      return ApplicationResponse.error(e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  /**
   * 파일 업로드
   * @param projectImg
   * @return
   * @throws IOException
   */
  private String fileUpload(MultipartFile projectImg) throws IOException {
    String uploadedFilePath = null;
    String boardType="project";

    if(projectImg != null) {
      log.info("projectImage file name: {}", projectImg.getOriginalFilename());
      uploadedFilePath = uploadService.uploadFileImage(projectImg,boardType);
    }
    return uploadedFilePath;
  }


  /**
   * 게시글 수정
   * @param dto
   * @param projectImg
   * @return
   */
  @PatchMapping
  public ApplicationResponse<?> modify(
      @Validated @RequestPart("project") ProjectModifyRequestDTO dto,
      @RequestPart(
          value = "projectImage",
          required = false) MultipartFile projectImg
  ) {
    log.info("/api/ddamddam modify {} !! - dto : {}", dto,projectImg);

    try {
      String uploadedFilePath = fileUpload(projectImg);

      ProjectDetailResponseDTO responseDTO = projectService.modify(dto,uploadedFilePath);
      return ApplicationResponse.ok(responseDTO);
    } catch (Exception e) {
      return ApplicationResponse.error(e.getMessage());
    }
  }

  //삭제
  @DeleteMapping("/{idx}")
  public ApplicationResponse<?> delete(@PathVariable Long idx) {
    log.info("/api/ddamddam {}  DELETE!! ", idx);

    try {
      projectService.delete(idx);
      return ApplicationResponse.ok("DEL SUCCESS!!");
    } catch (Exception e) {
      e.printStackTrace();
      return ApplicationResponse.bad(e.getMessage());
    }
  }


}
