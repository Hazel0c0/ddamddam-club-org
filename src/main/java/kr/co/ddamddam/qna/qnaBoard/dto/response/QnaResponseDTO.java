package kr.co.ddamddam.qna.qnaBoard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
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
    private Long boardIdx; // 식별번호
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime boardDate;
    private final QnaAdoption boardAdoption = QnaAdoption.N; // 기본값: 채택되지 않은 상태인 N
    private List<QnaReply> replyList;
    private List<Hashtag> hashtagList;
    private User user;

}
