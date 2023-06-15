package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListResponseDTO {

  private Long boardIdx;
  private String boardTitle;
  private String boardContent;
  private String projectType;

  /**
   *   true - 모집완료
   *   false - 구인중
   */
  private boolean completion;
  private int likeCount;

  private LocalDateTime projectDate; // 게시글 작성 날짜
  private String offerPeriod; //모집기간


  public ProjectListResponseDTO(Project project){
    this.boardIdx =project.getProjectIdx();
    this.boardTitle =project.getProjectTitle();
    this.boardContent =project.getProjectContent();
    this.likeCount =project.getLikeCount();
    this.projectType = project.getProjectType();
    this.completion = (project.getMaxFront() - project.getApplicantOfFronts().size()) == 0;
    this.projectDate =project.getProjectDate();
    this.offerPeriod =project.getOfferPeriod();
  }
}
