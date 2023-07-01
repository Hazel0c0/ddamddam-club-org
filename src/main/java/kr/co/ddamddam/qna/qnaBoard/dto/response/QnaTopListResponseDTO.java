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

    private Long boardIdx; // 식별번호
    private String boardTitle;
    private String boardContent;
    private QnaAdoption boardAdoption;
    private int boardViewCount;
    private int boardReplyCount; // 해당 게시글의 댓글수

}
