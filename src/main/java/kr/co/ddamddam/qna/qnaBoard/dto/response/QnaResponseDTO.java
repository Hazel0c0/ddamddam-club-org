package kr.co.ddamddam.qna.qnaBoard.dto.response;

import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaHashtag;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaResponseDTO {

    // TODO : Validate 처리
    private Long qnaIdx; // 식별번호
    private String qnaTitle;
    private String qnaContent;
    private String qnaWriter;
    private LocalDateTime qnaDate;
    private final QnaAdoption qnaAdoption = QnaAdoption.N; // 기본값: 채택되지 않은 상태인 N
    private List<QnaReply> qnaReply;
    private List<QnaHashtag> qnaHashtag;
    private User user;

}
