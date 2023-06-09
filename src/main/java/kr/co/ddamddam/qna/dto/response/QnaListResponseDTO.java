package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import lombok.*;

import java.time.LocalDateTime;

/**
 * QNA 페이지에 일부 정보만 보여주는 DTO
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaListResponseDTO {

    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;

    // Entity 를 DTO 로 변환하는 생성자
    public QnaListResponseDTO(Qna qna) {
        this.boardTitle = qna.getQnaTitle();
        this.boardContent = qna.getQnaContent();
        this.boardWriter = qna.getQnaWriter();
        this.boardDate = qna.getQnaDate();
        this.boardAdoption = qna.getQnaAdoption();
    }
}
