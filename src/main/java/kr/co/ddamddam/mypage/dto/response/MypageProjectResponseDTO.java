package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.UserUtil;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageProjectResponseDTO {
    /*
          프로젝트 명
          프로젝트 개최자
          프로젝트 참가자 - 백/프론트
     */
    private String boardTitle;
    private String boardWriter;
    private UserPosition writerPosition;
    private List<String> front= new ArrayList<>();
    private List<String> back= new ArrayList<>();

    public MypageProjectResponseDTO(Project p, UserPosition writerPosition) {
        this.boardTitle = p.getProjectTitle();
        this.boardWriter = p.getUserNickname();
        this.writerPosition = writerPosition;
    }

    public void setBack(Project p) {
        List<ApplicantOfBack> applicantOfBacks = p.getApplicantOfBacks();
        for (ApplicantOfBack back : applicantOfBacks) {
            String userNickname = back.getUser().getUserNickname();
            this.back.add(userNickname);
        }
    }

    public void setFront(Project p) {
        List<ApplicantOfFront> applicantOfFronts = p.getApplicantOfFronts();
        for (ApplicantOfFront front : applicantOfFronts) {
            String userNickname = front.getUser().getUserNickname();
            this.back.add(userNickname);
        }
    }

}
