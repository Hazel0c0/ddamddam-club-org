package kr.co.ddamddam.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 클라이언트 측에서 로그인 성공시 서버에서 보내주는 데이터
 */

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {

    // 유저 정보 (비밀번호 제외)
    private Long userIdx;
    private String userEmail;
    private String userName;
    private String userNickname;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime userRegdate;
    private LocalDate userBirth;
    private UserPosition userPosition;
    private int userCareer;
    private Long userPoint;
    private String userProfile;
    private UserRole userRole;

    // 인증 토큰
    private String token;

    // 로그인 응답 메세지
    private String message;

    // Entity 를 DTO 로 변경
    public LoginResponseDTO(User user, String token) {
        this.userIdx = user.getUserIdx();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userRegdate = user.getUserRegdate();
        this.userBirth = user.getUserBirth();
        this.userPosition = user.getUserPosition();
        this.userCareer = user.getUserCareer();
        this.userPoint = user.getUserPoint();
        this.userProfile = user.getUserProfile();
        this.userRole = user.getUserRole();
        this.token = token;
        this.message = String.valueOf(ResponseMessage.SUCCESS);
    }

}
