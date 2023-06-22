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

  @NotNull
  private Long boardIdx;
  @NotBlank
  private String boardTitle;
  @NotBlank
  private String boardContent;
  @NotBlank
  private String projectType;

  //모집인원
  @NotNull @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private String offerPeriod; //모집기간
}
