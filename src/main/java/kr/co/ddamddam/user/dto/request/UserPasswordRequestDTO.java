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
    @Size(min = 8, max = 20)
    private String newUserPassword;

}
