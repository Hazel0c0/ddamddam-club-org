package kr.co.ddamddam.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModifyRequestDTO {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Size(min =  8, max = 20)
    private String userPw;

    @NotBlank
    @Size(min=2, max = 5)
    private String userName;

    @NotBlank
    @Size(min=2,max=5)
    private String userNickName;

    @NotBlank
    private String userPosition;

    @NotNull
    private int userCareer;

    private String userProfile;
}

