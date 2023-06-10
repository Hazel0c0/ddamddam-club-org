package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.entity.Applicant;
import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponseDTO {
  private String writer;

  private String title;
  private String content;
  private String projectType;

  //모집인원
  private int maxFront;
  private int maxBack;

  // 모집 된 인원
  private Applicant applicantOfFront;
  private Applicant applicantOfBack;

  private String applicantionPeriod; //모집기간

  private LocalDateTime projectDate;

  public ProjectDetailResponseDTO(Project project){
    this.writer=project.getWriter();
    this.title=project.getProjectTitle();
    this.content=project.getProjectContent();
    this.projectType = project.getProjectType();
    this.maxFront=project.getMaxFront();
    this.maxBack=project.getMaxBack();
    this.applicantOfFront=null;
    this.applicantOfBack=null;
    this.applicantionPeriod=project.getApplicantionPeriod();
    this.projectDate=project.getProjectDate();
  }
}
