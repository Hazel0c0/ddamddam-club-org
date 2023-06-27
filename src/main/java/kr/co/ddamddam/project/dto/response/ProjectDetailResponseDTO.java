package kr.co.ddamddam.project.dto.response;

//import kr.co.ddamddam.project.entity.applicant.Apply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponseDTO {

    private Long boardIdx;
    private String boardWriter;
    private String boardImg;
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

    /**
     *   true - 모집완료
     *   false - 구인중
     */
    private boolean completion;

    private String projectDate; // 게시글 작성 날짜
    private LocalDate offerPeriod; //모집기간

    private int likeCount;

    public ProjectDetailResponseDTO(Project project) {
        this.boardWriter = project.getUserNickname();
        this.boardIdx = project.getProjectIdx();
        this.boardImg = project.getProjectImg();
        this.boardTitle = project.getProjectTitle();
        this.boardContent = project.getProjectContent();
        this.projectType = project.getProjectType();
        this.maxFront = project.getMaxFront();
        this.maxBack = project.getMaxBack();
        this.applicantOfFront = project.getApplicantOfFronts().size();
        this.applicantOfBack = project.getApplicantOfBacks().size();
        this.completion = (project.getMaxFront() - project.getApplicantOfFronts().size()) == 0 && (project.getMaxBack() - project.getApplicantOfBacks().size()) == 0;
        this.offerPeriod = project.getOfferPeriod();
        this.likeCount =project.getLikeCount();

        this.projectDate = formatProjectDate(project.getProjectDate());

    }
    private String formatProjectDate(LocalDateTime projectDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd  HH:mm");
        return projectDate.format(formatter);
    }

}
