package kr.co.ddamddam.mypage.dto.request;

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
}
