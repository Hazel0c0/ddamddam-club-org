package kr.co.ddamddam.project.service;


import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.project.UserUtil;
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
    private final ValidateToken validateToken;

    private final UserUtil userUtil;
    private final ProjectService projectService;

    public void handleLike(TokenUserInfo tokenUserInfo, Long projectIdx) {
        validateToken.validateToken(tokenUserInfo);
        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        User user = userUtil.getUser(userIdx);
        Project project = projectService.getProject(projectIdx);

        ProjectLike projectLike = projectLikeRepository.findByUserAndProject(user, project);

        if (projectLike == null) {
            // 좋아요 추가
            projectLike = ProjectLike.builder()
                    .user(user)
                    .project(project)
                    .build();
            projectLikeRepository.save(projectLike);

            // 좋아요 수 증가
            project.setLikeCount(project.getLikeCount() + 1);
            log.info("{}가 좋아요 누름 -> {}", userIdx, project.getLikeCount());
        } else {
            // 좋아요 취소
            projectLikeRepository.delete(projectLike);
            log.info("{}가 좋아요 취소 -> {}", userIdx, project.getLikeCount());

            // 좋아요 수 감소
            project.setLikeCount(project.getLikeCount() - 1);
        }
        projectRepository.save(project);
    }

    public boolean checkIfLiked(TokenUserInfo tokenUserInfo, Long projectIdx) {
        validateToken.validateToken(tokenUserInfo);

        // 사용자의 userIdx와 프로젝트의 projectIdx를 기반으로 좋아요 여부를 조회합니다.

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        ProjectLike projectLike
                = projectLikeRepository.findByUserAndProject(
                userUtil.getUser(userIdx), projectService.getProject(projectIdx)
        );
        System.out.println("좋아요 여부 = " + projectLike);

        // projectLike가 null이 아니라면 좋아요가 선택되어 있다고 판단합니다.
        return projectLike != null;
    }
}
