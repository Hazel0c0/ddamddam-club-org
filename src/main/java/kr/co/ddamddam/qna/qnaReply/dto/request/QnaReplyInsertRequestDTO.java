package kr.co.ddamddam.qna.qnaReply.dto.request;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * QNA 댓글 작성 요청 DTO
 */

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyInsertRequestDTO {

    private Long boardIdx; // 게시글번호
    @NotBlank
    @Size(min = 1, max = 1000)
    private String replyContent;

    // DTO 를 Entity 로 변환하는 생성자
    public QnaReply toEntity(Qna qna, User user) {
        return QnaReply.builder()
                .qnaReplyContent(this.getReplyContent())
                .qnaReplyWriter(user.getUserNickname())
                .qna(qna)
                .user(user)
                .build();
    }

}
