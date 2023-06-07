package kr.co.ddamddam.login.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.login.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 로그인 성공시 응답 dto
 */
public class LoginResponseDTO {

    private Long userIdx;
    private String userid;
    private String userEmail;
    private String userName;
    private String userNickname;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm:ss")
    private LocalDateTime userRegdate;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDate userBirth;
    private String userPosition;
    private String userCareer;
    private int userPoint;
    private String userProfile;
    private String token; // 인증 토큰

    // 엔터티를 DTO 로 변경
    public LoginResponseDTO(User user, String token) {
        this.userIdx = user.getUserIdx();
        this.userid = user.getUserid();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.userRegdate = user.getUserRegdate();
        this.userBirth = user.getUserBirth();
        this.userPosition = user.getUserPosition();
        this.userCareer = user.getUserCareer();
        this.userPoint = user.getUserPoint();
        this.userProfile = user.getUserProfile();
        this.token = token;
    }
}
