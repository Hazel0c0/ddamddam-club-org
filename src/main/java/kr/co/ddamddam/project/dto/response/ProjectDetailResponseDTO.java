package kr.co.ddamddam.project.dto.response;

//import kr.co.ddamddam.project.entity.applicant.Apply;
import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponseDTO {
  private String boardWriter;

  private Long boardIdx;
  private String boardTitle;
  private String boardContent;
  private String projectType;

  //모집인원
  private int maxFront;
  private int maxBack;

  // 모집 된 인원
//  @JsonIgnore
  private int applicantOfFront;
//  @JsonIgnore
  private int applicantOfBack;


  private String offerPeriod; //모집기간

  private LocalDateTime projectDate;

  public ProjectDetailResponseDTO(Project project){
    this.boardWriter =project.getUserNickname();
    this.boardIdx =project.getProjectIdx();
    this.boardTitle =project.getProjectTitle();
    this.boardContent =project.getProjectContent();
    this.projectType = project.getProjectType();
    this.maxFront=project.getMaxFront();
    this.maxBack=project.getMaxBack();
//    this.applicantOfFront=project.getApplicantOfFronts().stream().map(ApplicantOfFront::getUserIdx).collect(Collectors.toList());
    this.applicantOfFront=project.getApplicantOfFronts().size();
    this.applicantOfBack=project.getApplicantOfBacks().size();
    this.offerPeriod =project.getOfferPeriod();
    this.projectDate=project.getProjectDate();
  }



}
