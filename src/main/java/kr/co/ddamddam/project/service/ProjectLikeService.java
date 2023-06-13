package kr.co.ddamddam.project.service;


import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.ProjectLike;
import kr.co.ddamddam.project.repository.ProjectLikeRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProjectLikeService {

  private final ProjectLikeRepository projectLikeRepository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;

  public void likeUp(Long userIdx, Long projectIdx) {
    User user = userRepository.findById(userIdx)
        .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

    Project project = projectRepository.findById(projectIdx)
        .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));

    ProjectLike projectLike = projectLikeRepository.findByUserAndProject(user, project);

    if (projectLike == null) {
      projectLike = ProjectLike.builder()
          .user(user)
          .project(project)
          .build();
      projectLikeRepository.save(projectLike);

      // 좋아요 수 증가
      project.setLikeCount(project.getLikeCount() + 1);
      projectRepository.save(project);
    } else {
      throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
    }
  }

  public void cancelLike(Long userId, Long projectId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));

    ProjectLike projectLike = projectLikeRepository.findByUserAndProject(user, project);

    if (projectLike != null) {
      projectLikeRepository.delete(projectLike);

      // 좋아요 수 감소
      project.setLikeCount(project.getLikeCount() - 1);
      projectRepository.save(project);
    } else {
      throw new IllegalStateException("좋아요를 누르지 않았습니다.");
    }
  }
}
