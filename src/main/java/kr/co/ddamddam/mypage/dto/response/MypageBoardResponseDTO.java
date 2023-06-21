package kr.co.ddamddam.mypage.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    // TODO : 전체 Validate 처리
    private String boardType; // 게시판 타입
    private Long boardIdx;
    // TODO : 제목 길면 Truncate 처리
    private String boardTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime boardDate;

}
