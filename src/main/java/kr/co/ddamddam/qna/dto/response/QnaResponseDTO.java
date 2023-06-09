package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import kr.co.ddamddam.qna.entity.QnaHashtag;
import kr.co.ddamddam.qna.entity.QnaReply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaResponseDTO {

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
