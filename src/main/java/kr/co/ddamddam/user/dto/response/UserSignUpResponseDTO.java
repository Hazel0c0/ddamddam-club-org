package kr.co.ddamddam.user.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import lombok.*;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;

// 회원가입 완료 후 클라이언트에게 응답할 데이터를 담는 객체
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "userEmail")
public class UserSignUpResponseDTO {

    private String userEmail;
    private String userPw;
    private String userName;
    private String userNickName;
    private LocalDate userBirth;
    private UserPosition userPosition;
    private int userCareer;
    private String userProfile;
    private UserRole userRole;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s")
//    private LocalDateTime userRegdate;

    public UserSignUpResponseDTO(User user){

        this.userEmail = user.getUserEmail();
        this.userPw = user.getUserPassword();
        this.userName = user.getUserName();
        this.userNickName = user.getUserNickname();
        this.userBirth =  user.getUserBirth();
        this.userPosition = user.getUserPosition();
        this.userCareer = user.getUserCareer();
        this.userProfile = user.getUserProfile();
        this.userRole = user.getUserRole();

    }






}
