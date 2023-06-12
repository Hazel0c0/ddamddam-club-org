package kr.co.ddamddam.qna.qnaReply.dto.response;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

/**
 * QNA 댓글 응답 기본 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyResponseDTO {

    private Long qnaReplyIdx; // 식별번호
    private String qnaReplyContent;
    private String qnaReplyWriter;
    private LocalDateTime qnaReplyDate;
    private QnaAdoption qnaReplyAdoption;

}
