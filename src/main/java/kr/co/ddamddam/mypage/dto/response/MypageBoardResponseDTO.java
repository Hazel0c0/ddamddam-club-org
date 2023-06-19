package kr.co.ddamddam.mypage.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 마이페이지 <내가 쓴 게시글> 의 게시글 하나를 보여주는 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageBoardResponseDTO {

    // TODO : Validate 처리
    private String boardType; // 게시판 타입
    private Long boardIdx;
    private LocalDateTime boardDate;

}
