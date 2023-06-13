package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.entity.Project;
import lombok.*;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListResponseDTO {

  private String boardTitle;
  private String boardContent;
  private String projectType;

  /**
   *   true - 모집완료
   *   false - 구인중
   */
  private boolean completion;

  private String offerPeriod; //모집기간


  public ProjectListResponseDTO(Project project){
    this.boardTitle =project.getProjectTitle();
    this.boardContent =project.getProjectContent();
    this.projectType = project.getProjectType();
    this.completion = (project.getMaxFront() - project.getApplicantOfFronts().size()) == 0;
    this.offerPeriod =project.getOfferPeriod();
  }
}
