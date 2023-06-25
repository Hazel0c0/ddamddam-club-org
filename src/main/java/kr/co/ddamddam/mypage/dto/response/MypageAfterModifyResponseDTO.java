package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.user.entity.UserPosition;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageAfterModifyResponseDTO {

    private String userName;
    private String userNickName;
    private LocalDate userBirth;
    private UserPosition userPosition;
    private int userCareer;
    private String userProfile;
}
