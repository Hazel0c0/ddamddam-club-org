package kr.co.ddamddam.config.security;

import kr.co.ddamddam.user.entity.UserRole;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUserInfo {

    private String userIdx;
    private String userEmail;
    private UserRole userRole;
}
