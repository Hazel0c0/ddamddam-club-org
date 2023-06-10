package kr.co.ddamddam.project.service;

import kr.co.ddamddam.project.dto.request.PageDTO;
import kr.co.ddamddam.project.dto.request.ProjectModifyRequestDTO;
import kr.co.ddamddam.project.dto.request.ProjectWriteDTO;
import kr.co.ddamddam.project.dto.response.PageResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectDetailResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListPageResponseDTO;
import kr.co.ddamddam.project.dto.response.ProjectListResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  public ProjectListPageResponseDTO getList(PageDTO dto) {

    Pageable pageable = PageRequest.of(
        dto.getPage() - 1,
        dto.getSize(),
        Sort.by("projectDate").descending()
    );
    Page<Project> projectPage = projectRepository.findAll(pageable);
    List<Project> projects =projectPage.getContent();
//    log.info("project list : {} ",projectList);

    List<ProjectListResponseDTO> projectList
        = projects.stream()
        .map(project -> new ProjectListResponseDTO(project))
        .collect(Collectors.toList());

    return ProjectListPageResponseDTO.builder()
        .count(projectList.size())
        .pageInfo(new PageResponseDTO<Project>(projectPage))
        .projects(projectList)
        .build();
  }

  public ProjectDetailResponseDTO getDetail(Long id) {
    Project foundProject = getProject(id);

    return new ProjectDetailResponseDTO(foundProject);
  }

  public Project getProject(Long id) {
    return projectRepository.findById(id)
        .orElseThrow(
            () -> new RuntimeException(
                id + "번 게시물이 존재하지 않습니다!"
            )
        );
  }

  public ProjectDetailResponseDTO write(final ProjectWriteDTO dto) {
    Project saved = projectRepository.save(dto.toEntity());

    return new ProjectDetailResponseDTO(saved);
  }


  public ProjectDetailResponseDTO modify(ProjectModifyRequestDTO dto) {
    Project currProject = getProject(dto.getProjectIdx());

    currProject.setProjectTitle(dto.getTitle());
    currProject.setProjectContent(dto.getContent());
    currProject.setProjectType(dto.getProjectType());
    currProject.setMaxFront(dto.getMaxFront());
    currProject.setMaxBack(dto.getMaxBack());
    currProject.setApplicantionPeriod(dto.getApplicantionPeriod());
    currProject.setProjectIdx(dto.getProjectIdx());

    Project modifiedProject = projectRepository.save(currProject);

    return new ProjectDetailResponseDTO(modifiedProject);
  }

  public void delete(Long id) {
    projectRepository.deleteById(id);
  }
}
