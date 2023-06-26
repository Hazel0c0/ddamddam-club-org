package kr.co.ddamddam.mypage.dto.request;

import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageModifyRequestDTO {

    private String userNickName;

    private String userPosition;
    private int userCareer;
    private String userName;
    private LocalDate userBirth;
    private String userProfile;

    //엔티티로 변경하는 메서드
    public User toEntity(String uploadedFilePath){
        return User.builder()
                .userName(this.userName)
                .userBirth(this.userBirth)
                .userCareer(this.userCareer)
                .userNickname(this.userNickName)
                .userPosition(UserPosition.valueOf(this.userPosition))
                .userProfile(uploadedFilePath)
                .build();
    }
}
