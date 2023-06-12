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
  private String writer;

  @NotBlank
  private String title;
  private String content;
  private String projectType;

  //모집인원
  @NotNull @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private String applicantionPeriod; //모집기간

  public Project toEntity() {
    return Project.builder()
        .writer(this.writer)
        .projectTitle(this.title)
        .projectContent(this.content)
        .projectType(this.projectType)
        .maxFront(this.maxFront)
        .maxBack(this.maxBack)
        .build();
  }
}
