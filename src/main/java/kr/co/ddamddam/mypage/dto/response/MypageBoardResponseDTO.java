package kr.co.ddamddam.mypage.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
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

    private String boardType; // 게시판 타입
    private Long boardIdx;
    private String boardTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime boardDate;

}
