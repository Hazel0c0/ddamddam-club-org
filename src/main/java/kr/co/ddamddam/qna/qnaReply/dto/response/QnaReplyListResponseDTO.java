package kr.co.ddamddam.qna.qnaReply.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import lombok.*;

import java.time.LocalDateTime;

/**
 * QNA 댓글 페이지에 댓글의 일부 정보만 보여주는 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyListResponseDTO {

    private Long replyIdx; // 식별번호
    private String replyContent;
    private String replyWriter;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime replyDate;
    private QnaAdoption replyAdoption;

    // Entity 를 DTO 로 변환하는 생성자
    public QnaReplyListResponseDTO(QnaReply qnaReply) {
        this.replyIdx = qnaReply.getQnaReplyIdx();
        this.replyContent = qnaReply.getQnaReplyContent();
        this.replyWriter = qnaReply.getQnaReplyWriter();
        this.replyDate = qnaReply.getQnaReplyDate();
        this.replyAdoption = qnaReply.getQnaReplyAdoption();
    }
}
