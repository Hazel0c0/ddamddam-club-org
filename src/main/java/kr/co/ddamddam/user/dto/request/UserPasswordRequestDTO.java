package kr.co.ddamddam.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordRequestDTO {

    @NotBlank
    @Email
    @Size(min = 8, max = 15)
    private String newUserPassword;

}
