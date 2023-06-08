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
  private String title;
  private String content;
  private String projectType;

  //모집인원
  @NotNull
  @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private String applicantionPeriod; //모집기간

  @NotNull
  private Long projectIdx;
}
