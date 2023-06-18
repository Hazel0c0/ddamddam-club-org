package kr.co.ddamddam.project.dto.request;

import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectWriteDTO {

  @NotNull
  private Long boardWriterIdx;

  @NotBlank
  private String boardTitle;
  @NotBlank
  private String boardContent;
  private String projectType;

  //모집인원
  @NotNull @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private String offerPeriod; //모집기간

  public Project toEntity(String userName, String uploadedFilePath) {
    return Project.builder()
        .userIdx(this.boardWriterIdx)
        .writer(userName)
        .projectTitle(this.boardTitle)
        .projectImg(uploadedFilePath)
        .projectContent(this.boardContent)
        .projectType(this.projectType)
        .maxFront(this.maxFront)
        .maxBack(this.maxBack)
        .build();
  }
}
