package kr.co.ddamddam.project.service;

import kr.co.ddamddam.project.dto.page.PageDTO;
import kr.co.ddamddam.project.dto.request.ProjectModifyRequestDTO;
import kr.co.ddamddam.project.dto.request.ProjectWriteDTO;
import kr.co.ddamddam.project.dto.page.PageResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListPageResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.repository.ProjectRepository;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProjectService {
  private final ProjectRepository projectRepository;

  public ProjectListPageResponseDTO getList(PageDTO dto, String keyword, String position) {

    Pageable pageable = null;

    // 최신순, 인기순 정렬
    if (StringUtils.isEmpty(keyword)) {
      pageable = PageRequest.of(
          dto.getPage() - 1,
          dto.getSize(),
          Sort.by("projectDate").descending()
      );
    } else if ("like".equals(keyword)) {
      pageable = PageRequest.of(
          dto.getPage() - 1,
          dto.getSize(),
          Sort.by("likeCount").descending()
      );
    }

    // 포지션별 조회
    Page<Project> projectPage;
    if ("front".equals(position)) {
      projectPage = projectRepository.findByFrontNotZero(pageable);
    } else if ("back".equals(position)) {
      projectPage = projectRepository.findByBackNotZero(pageable);
    } else {
      projectPage = projectRepository.findAll(pageable);
    }

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

  public ProjectDetailResponseDTO getDetail(Long projectIdx) {
    Project foundProject = getProject(projectIdx);

    return new ProjectDetailResponseDTO(foundProject);
  }

  public Project getProject(Long projectIdx) {
    return projectRepository.findById(projectIdx)
        .orElseThrow(
            () -> new RuntimeException(
                projectIdx + "번 게시물이 존재하지 않습니다!"
            )
        );
  }

  public ProjectDetailResponseDTO write(final ProjectWriteDTO dto) {
    Project saved = projectRepository.save(dto.toEntity());

    return new ProjectDetailResponseDTO(saved);
  }


  public ProjectDetailResponseDTO modify(ProjectModifyRequestDTO dto) {
    Project currProject = getProject(dto.getProjectIdx());

    currProject.setProjectTitle(dto.getBoardTitle());
    currProject.setProjectContent(dto.getBoardContent());
    currProject.setProjectType(dto.getProjectType());
    currProject.setMaxFront(dto.getMaxFront());
    currProject.setMaxBack(dto.getMaxBack());
    currProject.setOfferPeriod(dto.getOfferPeriod());
    currProject.setProjectIdx(dto.getProjectIdx());

    Project modifiedProject = projectRepository.save(currProject);

    return new ProjectDetailResponseDTO(modifiedProject);
  }

  public void delete(Long id) {
    projectRepository.deleteById(id);
  }
}
