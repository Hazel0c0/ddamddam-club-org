package kr.co.ddamddam.project.service;

import kr.co.ddamddam.common.common.ValidateToken;
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

    public ProjectDetailResponseDTO getDetail(TokenUserInfo tokenUserInfo, Long projectIdx) {
        validateToken.validateToken(tokenUserInfo);

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
                .orElseThrow(() -> new RuntimeException("존재하지 않습니다!"));

        Project saved = projectRepository.save(dto.toEntity(user, uploadedFilePath));

        return new ProjectDetailResponseDTO(saved);
    }


    public ProjectDetailResponseDTO modify(
            TokenUserInfo tokenUserInfo,
            ProjectModifyRequestDTO dto,
            String uploadedFilePath
    ) {
        validateDTO(tokenUserInfo, dto.getProjectIdx());

        Project currProject = getProject(dto.getProjectIdx());

        currProject.setProjectTitle(dto.getBoardTitle());
        currProject.setProjectContent(dto.getBoardContent());
        currProject.setProjectType(dto.getProjectType());
        currProject.setMaxFront(dto.getMaxFront());
        currProject.setMaxBack(dto.getMaxBack());
        currProject.setOfferPeriod(dto.getOfferPeriod());
        currProject.setProjectIdx(dto.getProjectIdx());
      currProject.setProjectImg(uploadedFilePath);

    Project modifiedProject = projectRepository.save(currProject);

    return new ProjectDetailResponseDTO(modifiedProject);
  }

  public void delete(Long id) {
    projectRepository.deleteById(id);
  }

  // 퀵 매칭
  // select : 오래된 순 / 내 포지션 / 남은자리가 작은것 부터
  public ProjectListPageResponseDTO quickMatching(PageDTO dto, ProjectSearchRequestDto searchDto) {
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
}
