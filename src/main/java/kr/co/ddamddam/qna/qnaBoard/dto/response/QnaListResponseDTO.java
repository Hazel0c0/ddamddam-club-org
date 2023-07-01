package kr.co.ddamddam.qna.qnaBoard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.ddamddam.common.common.TruncateString.*;

/**
 * QNA 페이지에 일부 정보만 보여주는 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaListResponseDTO {

    // TODO : Validated 처리
    private Long boardIdx; // 식별번호
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardProfile;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;
    private int viewCount;
    private int replyCount;
    private List<String> hashtagList;

    /**
     * Entity 를 DTO 로 변환하는 생성자
     * Hashtag 리스트를 문자열 리스트로 변경해서 생성합니다.
     */
    public QnaListResponseDTO(Qna qna, List<String> hashtagList) {
        this.boardIdx = qna.getQnaIdx();
        this.boardTitle = truncate(qna.getQnaTitle(), 25);
        this.boardContent = truncate(qna.getQnaContent(), 40);
        this.boardWriter = qna.getQnaWriter();
        this.boardDate = qna.getQnaDate();
        this.boardAdoption = qna.getQnaAdoption();
        this.viewCount = qna.getViewCount();
        this.replyCount = qna.getReplyCount();
        this.hashtagList = hashtagList;
    }

}
