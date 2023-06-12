package kr.co.ddamddam.project.dto.request;

import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectWriteDTO {

  @NotBlank
  private String boardWriter;

  @NotBlank
  private String boardTitle;
  private String boardContent;
  private String projectType;

  //모집인원
  @NotNull @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private String offerPeriod; //모집기간

  public Project toEntity() {
    return Project.builder()
        .writer(this.boardWriter)
        .projectTitle(this.boardTitle)
        .projectContent(this.boardContent)
        .projectType(this.projectType)
        .maxFront(this.maxFront)
        .maxBack(this.maxBack)
        .build();
  }
}
