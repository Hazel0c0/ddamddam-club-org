package kr.co.ddamddam.kakaologin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 카카오 로그인 응답 DTO
 * 로그인 처리 상태코드, 우리 서비스의 토큰, 회원 정보를 담고있습니다.
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoLoginResponseDTO {

    private HttpStatus status;
    private String token;

    private Long userIdx;
    private String userEmail;
    private String userName;
    private String userNickname;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime userRegdate;
    private LocalDate userBirth;
    private String userPosition;
    private int userCareer;
    private Long userPoint;
    private String userProfile;
    private String userRole;

    public KakaoLoginResponseDTO(HttpStatus status, String token, User user) {
        this.status = status;
        this.token = token;
        this.userIdx = user.getUserIdx();
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userRegdate = user.getUserRegdate();
        this.userBirth = user.getUserBirth();
        this.userPosition = String.valueOf(user.getUserPosition());
        this.userCareer = user.getUserCareer();
        this.userPoint = user.getUserPoint();
        this.userProfile = user.getUserProfile();
        this.userRole = String.valueOf(user.getUserRole());
    }
}
