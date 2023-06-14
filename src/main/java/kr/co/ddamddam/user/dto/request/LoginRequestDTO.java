package kr.co.ddamddam.user.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 클라이언트 측 로그인 요청 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank
    private String userEmail;
    @NotBlank
    private String userPassword;

}
