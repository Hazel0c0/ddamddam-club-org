package kr.co.ddamddam.qna.qnaBoard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import lombok.*;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;
    private int boardViewCount;
    private int boardReplyCount;

    // Entity 를 DTO 로 변환하는 생성자
    public QnaListResponseDTO(Qna qna) {
        this.boardIdx = qna.getQnaIdx();
        this.boardTitle = qna.getQnaTitle();
        this.boardContent = qna.getQnaContent();
        this.boardWriter = qna.getQnaWriter();
        this.boardDate = qna.getQnaDate();
        this.boardAdoption = qna.getQnaAdoption();
        this.boardViewCount = qna.getQnaView();
        this.boardReplyCount = qna.getQnaReply().size();
    }
}
