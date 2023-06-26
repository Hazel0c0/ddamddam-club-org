package kr.co.ddamddam.kakaologin.dto.request;

import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class KakaoSignUpRequestDTO {

    private String userId;
    private String userNickname;
    private String userEmail;
    private String userPassword;
    private String profile;

    public KakaoSignUpRequestDTO(String email, String nickname, String userPassword, String id, String profile) {
        this.userId = id;
        this.userNickname = nickname;
        if (email != null) {
            this.userEmail = email;
        }
        this.userPassword = userPassword;
        if (profile != null) {
            this.profile = profile;
        }
    }

    public KakaoSignUpRequestDTO(
            HashMap<String, Object> userInfo
    ) {
        // 카카오 계정으로 회원가입 시 비밀번호 임의 지정
        String userPassword = "-1";

        this.userId = userInfo.get("id").toString();
        this.userEmail = userInfo.get("email").toString();
        this.userNickname = userInfo.get("nickname").toString();
        this.userPassword = userPassword;
        this.profile = userInfo.get("profile").toString();
    }

    public User toEntity() {
        return User.builder()
                .userEmail(this.userEmail)
                .userNickname(this.userNickname)
                .userPassword(this.userPassword)
                .userName("카카오회원")
                .userPosition(UserPosition.FRONTEND)
                .userRole(UserRole.SNS)
                .userBirth(LocalDate.of(2000, 1, 1))
                .userProfile(this.profile)
                .build();
    }
}
