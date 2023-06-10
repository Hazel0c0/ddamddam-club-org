package kr.co.ddamddam.qna.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaTopListResponseDTO {

    private String boardTitle;
    private String boardWriter;
    private int boardView;
    private int replyCount; // 해당 게시글의 댓글수

}
