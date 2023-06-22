package kr.co.ddamddam.project.service;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.project.dto.page.PageDTO;
import kr.co.ddamddam.project.dto.page.PageResponseDTO;
import kr.co.ddamddam.project.dto.request.ProjectModifyRequestDTO;
import kr.co.ddamddam.project.dto.request.ProjectSearchRequestDto;
import kr.co.ddamddam.project.dto.request.ProjectWriteDTO;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListPageResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ValidateToken validateToken;

    public ProjectListPageResponseDTO getList(PageDTO dto, ProjectSearchRequestDto searchDto) {

        Pageable pageable = getPageable(dto, searchDto);

        Page<Project> projectPage = search(pageable, searchDto);

        return getProjectList(projectPage);
    }

    private static ProjectListPageResponseDTO getProjectList(Page<Project> projectPage) {
        List<Project> projects = projectPage.getContent();
        List<ProjectListResponseDTO> projectList = projects.stream()
                .map(project -> new ProjectListResponseDTO(project))
                .collect(Collectors.toList());

        return ProjectListPageResponseDTO.builder()
                .count(projectList.size())
                .pageInfo(new PageResponseDTO<Project>(projectPage))
                .projects(projectList)
                .build();
    }

    private static Pageable getPageable(
            PageDTO dto,
            ProjectSearchRequestDto searchDto) {
        Pageable pageable = null;

        // 최신순, 인기순 정렬
        if (StringUtils.isEmpty(searchDto.getSort())) {
            pageable = PageRequest.of(
                    dto.getPage() - 1,
                    dto.getSize(),
                    Sort.by("projectDate").descending()
            );
        } else if ("like".equals(searchDto.getSort())) {
            pageable = PageRequest.of(
                    dto.getPage() - 1,
                    dto.getSize(),
                    Sort.by("likeCount").descending()
            );
        }
        return pageable;
    }

    private Page<Project> search(Pageable pageable, ProjectSearchRequestDto searchDto) {
        // 포지션별 조회
        Page<Project> projectPage;
        if ("front".equals(searchDto.getPosition())) {
            projectPage = projectRepository.findByFrontNotZero(pageable);
        } else if ("back".equals(searchDto.getPosition())) {
            projectPage = projectRepository.findByBackNotZero(pageable);
        } else {
            projectPage = projectRepository.findAll(pageable);
        }

        // 검색어 조회
        if ("search".equals(searchDto.getSearch())) {
            projectPage = projectRepository.findProjectsBySearchWord(pageable, searchDto.getKeyword());
        }
        return projectPage;
    }

    public ProjectDetailResponseDTO getDetail(Long projectIdx) {

        Project foundProject = getProject(projectIdx);

        return new ProjectDetailResponseDTO(foundProject);
    }

    public Project getProject(Long projectIdx) {
        return projectRepository.findById(projectIdx)
                .orElseThrow(() -> new RuntimeException(projectIdx + "번 게시물이 존재하지 않습니다!"));
    }

    // 글 작성
    public ProjectDetailResponseDTO write(
            final TokenUserInfo tokenUserInfo,
            final ProjectWriteDTO dto,
            final String uploadedFilePath
    ) {
        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new RuntimeException(userIdx+"회원이 존재하지 않습니다!"));

        Project saved = projectRepository.save(dto.toEntity(user, uploadedFilePath));

        return new ProjectDetailResponseDTO(saved);
    }


    public ProjectDetailResponseDTO modify(
            TokenUserInfo tokenUserInfo,
            ProjectModifyRequestDTO dto,
            String uploadedFilePath
    ) {
        validateDTO(tokenUserInfo, dto.getBoardIdx());

        Project currProject = getProject(dto.getBoardIdx());

        if (currProject.getUser().getUserEmail().equals(
            tokenUserInfo.getUserEmail())
        ) {
            log.info("이 게시글 작성자와 현재 로그인 사용자가 일치합니다");

            currProject.setProjectTitle(dto.getBoardTitle());
            currProject.setProjectContent(dto.getBoardContent());
            currProject.setProjectType(dto.getProjectType());
            currProject.setMaxFront(dto.getMaxFront());
            currProject.setMaxBack(dto.getMaxBack());
            currProject.setOfferPeriod(dto.getOfferPeriod());
            currProject.setProjectIdx(dto.getBoardIdx());
            currProject.setProjectImg(uploadedFilePath);
            currProject.setProjectDate(LocalDateTime.now());

            Project modifiedProject = projectRepository.save(currProject);

            return new ProjectDetailResponseDTO(modifiedProject);
        } else {
            throw new UnauthorizationException(ErrorCode.ACCESS_FORBIDDEN, tokenUserInfo.getUserEmail());
        }
  }

  public void delete(Long id) {
    projectRepository.deleteById(id);
  }

  // 퀵 매칭
  // select : 오래된 순 / 내 포지션 / 남은자리가 작은것 부터
  public ProjectListPageResponseDTO quickMatching(TokenUserInfo tokenUserInfo, PageDTO dto, ProjectSearchRequestDto searchDto) {
    Pageable pageable = null;

    if (StringUtils.isEmpty(searchDto.getSort())) {
      if ("front".equals(searchDto.getPosition())) {
        pageable = PageRequest.of(
            dto.getPage() - 1,
            dto.getSize(),
            Sort.by(
                Sort.Order.asc("applicantOfFronts.size"),
                Sort.Order.asc("projectDate")
            )
        );
      } else if ("back".equals(searchDto.getPosition())) {
        pageable = PageRequest.of(
            dto.getPage() - 1,
            dto.getSize(),
            Sort.by(
                Sort.Order.asc("applicantOfBacks.size"),
                Sort.Order.asc("projectDate")
            )
        );
      }
    }
    Page<Project> projectPage = search(pageable, searchDto);

    return getProjectList(projectPage);
  }

    /**
     * 토큰 유효성을 검사합니다.
     * 로그인 중인 유저와 게시글 작성자가 동일한지 검사합니다.
     *
     * @param tokenUserInfo
     * @param boardIdx
     * @return
     */
    private Project validateDTO(TokenUserInfo tokenUserInfo, Long boardIdx) {
        // 토큰 인증 실패
        if (tokenUserInfo == null) {
            throw new UnauthorizationException(UNAUTHENTICATED_USER, "로그인 후 이용 가능합니다.");
        }

        // 게시글 존재 여부 확인
        Project project = projectRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        // 토큰 내 회원 이메일과 게시글 작성자의 이메일이 일치하지 않음 -> 작성자가 아님 -> 수정 및 삭제 불가
        if (!project.getUser().getUserEmail().equals(tokenUserInfo.getUserEmail())) {
            throw new UnauthorizationException(ACCESS_FORBIDDEN, tokenUserInfo.getUserEmail());
        }

        return project;
    }
}
