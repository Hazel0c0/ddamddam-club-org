package kr.co.ddamddam.project.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
  @Size(max = 100)
  private String boardTitle;
  @NotBlank
  @Size(max = 3000)
  private String boardContent;
  @NotBlank
  private String projectType;

  //모집인원
  @NotNull @Max(5)
  private int maxFront;
  @NotNull @Max(5)
  private int maxBack;

  private LocalDateTime offerPeriod; //모집기간
}
