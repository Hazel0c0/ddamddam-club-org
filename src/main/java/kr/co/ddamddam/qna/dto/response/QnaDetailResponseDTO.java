package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import kr.co.ddamddam.qna.entity.QnaReply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaDetailResponseDTO {

    // TODO : Validated 처리
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;
    private List<QnaReply> replyList;

    // Qna 엔터티를 DTO 로 변경
    public QnaDetailResponseDTO toEntity(Qna qna) {
        return QnaDetailResponseDTO.builder()
                .boardTitle(qna.getQnaTitle())
                .boardContent(qna.getQnaContent())
                .boardWriter(qna.getQnaWriter())
                .boardDate(qna.getQnaDate())
                .boardAdoption(qna.getQnaAdoption())
                .replyList(this.replyList)
                .build();
    }

}
