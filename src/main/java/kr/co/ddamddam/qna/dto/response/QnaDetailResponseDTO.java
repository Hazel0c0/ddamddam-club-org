package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import kr.co.ddamddam.qna.entity.QnaReply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
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
    private String boardProfile;
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;
    private List<QnaReply> replyList;

    // Qna 엔터티, User 엔터티를 이용하여 해당 DTO 를 만드는 생성자
    public QnaDetailResponseDTO(Qna qna, User user) {
        QnaDetailResponseDTO.builder()
            .boardTitle(qna.getQnaTitle())
            .boardContent(qna.getQnaContent())
            .boardWriter(user.getUserNickname())
            .boardProfile(user.getUserProfile())
            .boardDate(qna.getQnaDate())
            .boardAdoption(qna.getQnaAdoption())
            .replyList(qna.getQnaReply())
            .build();
    }

}
