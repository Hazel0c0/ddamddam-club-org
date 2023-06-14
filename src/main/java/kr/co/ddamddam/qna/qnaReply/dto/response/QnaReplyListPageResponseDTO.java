package kr.co.ddamddam.qna.qnaReply.dto.response;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import lombok.*;

import java.util.List;

/**
 * QNA 댓글의 페이지 처리를 하는 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyListPageResponseDTO {

    private int count;
    private PageResponseDTO pageInfo;
    private List<QnaReplyListResponseDTO> replyList;

}
