package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaDetailResponseDTO {

    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;

    // Entity 를 DTO 로 변환하는 생성자
    public QnaDetailResponseDTO(Qna qna) {
        this.boardTitle = qna.getQnaTitle();
        this.boardContent = qna.getQnaContent();
        this.boardWriter = qna.getQnaWriter();
        this.boardDate = qna.getQnaDate();
        this.boardAdoption = qna.getQnaAdoption();
    }
}
