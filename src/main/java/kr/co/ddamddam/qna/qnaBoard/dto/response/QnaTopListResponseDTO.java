package kr.co.ddamddam.qna.qnaBoard.dto.response;

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
    private String boardWriter;
    private int boardView;
    private int replyCount; // 해당 게시글의 댓글수

}
