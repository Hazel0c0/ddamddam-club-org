package kr.co.ddamddam.project.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectModifyRequestDTO {
  @NotBlank
  private String boardTitle;
  private String boardContent;
  private String projectType;

  //모집인원
  @NotNull
  @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private String offerPeriod; //모집기간

  @NotNull
  private Long boardIdx;
}
