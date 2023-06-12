package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListResponseDTO {

  private String title;
  private String content;
  private String projectType;

  /**
   *   true - 모집완료
   *   false - 구인중
   */
  private boolean completion;

  private String applicantionPeriod; //모집기간


  public ProjectListResponseDTO(Project project){
    this.title=project.getProjectTitle();
    this.content=project.getProjectContent();
    this.projectType = project.getProjectType();
    this.completion=false;
    this.applicantionPeriod=project.getOfferPeriod();
  }
}
