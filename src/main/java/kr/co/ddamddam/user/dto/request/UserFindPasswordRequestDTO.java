package kr.co.ddamddam.user.dto.request;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class UserFindPasswordRequestDTO {

    @Email
    private String userEmail;
    @Size(min = 2, max = 30)
    private String userName;
}
