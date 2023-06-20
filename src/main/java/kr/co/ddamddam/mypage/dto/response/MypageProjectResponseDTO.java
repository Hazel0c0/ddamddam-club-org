package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.user.entity.UserPosition;

import java.util.ArrayList;
import java.util.List;

public class MypageProjectResponseDTO {
    /*
          프로젝트 명
          프로젝트 개최자
          프로젝트 참가자 - 백/프론트
     */
    private String boardTitle;
    private String boardWriter;
    private UserPosition writerPosition;
    private List<String> front;
    private List<String> back;

    public MypageProjectResponseDTO(Project p, UserPosition writerPosition) {
        this.boardTitle = p.getProjectTitle();
        this.boardWriter = p.getUserNickname();
        this.writerPosition = writerPosition;

        this.front = new ArrayList<>();
        List<ApplicantOfFront> applicantOfFronts = p.getApplicantOfFronts();
        for (ApplicantOfFront front : applicantOfFronts) {
            Long userIdx = front.getUserIdx();
            this.front.add(String.valueOf(userIdx));
        }

        this.back = new ArrayList<>();
        List<ApplicantOfBack> applicantOfBacks = p.getApplicantOfBacks();
        for (ApplicantOfBack back : applicantOfBacks) {
            Long userIdx = back.getUserIdx();
            this.back.add(String.valueOf(userIdx));
        }
    }

}
