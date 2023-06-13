package kr.co.ddamddam.qna.qnaReply.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Long replyIdx; // 식별번호
    private String replyContent;
    private String replyWriter;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime replyDate;
    private QnaAdoption replyAdoption;

}
