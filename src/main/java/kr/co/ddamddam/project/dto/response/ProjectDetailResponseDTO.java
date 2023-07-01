package kr.co.ddamddam.project.dto.response;

//import kr.co.ddamddam.project.entity.applicant.Apply;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private UserPosition writerPosition;
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

    private LocalDateTime projectDate; // 게시글 작성 날짜
    private LocalDateTime offerPeriod; //모집기간

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
        this.likeCount =project.getLikeCount();
        this.offerPeriod = project.getOfferPeriod();
        this.projectDate = project.getProjectDate();
    }



}
