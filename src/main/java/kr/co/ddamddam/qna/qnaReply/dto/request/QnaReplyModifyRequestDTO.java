package kr.co.ddamddam.qna.qnaReply.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    private Long replyIdx;
    @NotBlank
    @Size(min = 1, max = 1000)
    private String replyContent;

}
