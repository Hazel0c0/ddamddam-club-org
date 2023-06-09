package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyResponseDTO {

    // TODO : Validate 처리
    private Long ReplyIdx; // 식별번호
    private String ReplyContent;
    private String ReplyWriter;
    private String ReplyProfile;
    private LocalDateTime ReplyDate;
    private QnaAdoption ReplyAdoption = QnaAdoption.N; // 기본값: 채택되지 않은 상태인 N

}
