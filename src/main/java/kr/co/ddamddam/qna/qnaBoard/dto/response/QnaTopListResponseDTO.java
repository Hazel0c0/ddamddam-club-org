package kr.co.ddamddam.qna.qnaBoard.dto.response;

import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaTopListResponseDTO {

    // TODO : Validate 처리
    private Long boardIdx; // 식별번호
    private String boardTitle;
    private String boardContent;
    private QnaAdoption qnaAdoption;
    private int boardViewCount;
    private int boardReplyCount; // 해당 게시글의 댓글수

}
