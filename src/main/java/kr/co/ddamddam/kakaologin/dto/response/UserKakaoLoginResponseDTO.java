package kr.co.ddamddam.kakaologin.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserKakaoLoginResponseDTO {

    private HttpStatus status;
    private String token;
    private String userEmail;

}
