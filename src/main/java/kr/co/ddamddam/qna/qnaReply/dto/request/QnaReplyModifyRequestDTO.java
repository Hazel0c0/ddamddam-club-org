package kr.co.ddamddam.qna.qnaReply.dto.request;

import lombok.*;

/**
 * QNA 댓글 수정 요청 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyModifyRequestDTO {

    // TODO : Validated 처리
    private Long replyIdx;
    private String replyContent;

}
