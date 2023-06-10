package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.entity.applicant.Apply;
import kr.co.ddamddam.project.entity.Project;
import lombok.*;

import java.time.LocalDateTime;

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
  private Apply applicantOfFront;
  private Apply applicantOfBack;

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
