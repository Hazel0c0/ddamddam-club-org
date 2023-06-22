package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.UserUtil;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class MypageProjectResponseDTO {
    /*
          프로젝트 명
          프로젝트 개최자
          프로젝트 참가자 - 백/프론트
     */
    private Long boardIdx;
    private String boardTitle;
    private String boardWriter;
    private UserPosition writerPosition;
    private List<String> front;
    private List<String> back;

    public MypageProjectResponseDTO(Project p, UserPosition writerPosition) {
        this.boardIdx = p.getProjectIdx();
        this.boardTitle = p.getProjectTitle();
        this.boardWriter = p.getUserNickname();
        this.writerPosition = writerPosition;
        this.front = new ArrayList<>();
        this.back = new ArrayList<>();
    }

    public void setBack(Project p) {
        List<ApplicantOfBack> applicantOfBacks = p.getApplicantOfBacks();
        log.info("applicantOfBacks: "+applicantOfBacks);
        for (ApplicantOfBack applyBack : applicantOfBacks) {
            String userNickname = applyBack.getUser().getUserNickname();
            log.info("유저 닉네임 : "+userNickname);
            this.back.add(userNickname);
        }
            log.info("set back -> {}",back);
    }

    public void setFront(Project p) {
        List<ApplicantOfFront> applicantOfFronts = p.getApplicantOfFronts();
        for (ApplicantOfFront front : applicantOfFronts) {
            String userNickname = front.getUser().getUserNickname();
            this.front.add(userNickname);
        }
    }

}
