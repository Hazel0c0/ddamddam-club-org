package kr.co.ddamddam.chat.dto.response;

import kr.co.ddamddam.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long userIdx;
    private String userName;
    private String userNickname;
    private String userProfile;

    public UserResponseDTO(User user){
        this.userIdx = user.getUserIdx();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userProfile = user.getUserProfile();
    }
}
