package kr.co.ddamddam.qna.qnaBoard.dto.response;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
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
public class QnaDetailResponseDTO {

    // TODO : Validated 처리
    private Long boardIdx; // 식별번호
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardProfile;
    private LocalDateTime boardDate;
    private QnaAdoption boardAdoption;
    private List<String> hashtagList;
    private List<QnaReply> replyList;

    // Qna 엔터티, User 엔터티를 이용하여 해당 DTO 를 만드는 생성자
    public QnaDetailResponseDTO(Qna qna, User user) {
        this.boardIdx = qna.getQnaIdx();
        this.boardTitle = qna.getQnaTitle();
        this.boardContent = qna.getQnaContent();
        this.boardWriter = user.getUserNickname();
        this.boardProfile = user.getUserProfile();
        this.boardDate = qna.getQnaDate();
        this.boardAdoption = qna.getQnaAdoption();
        this.replyList = qna.getQnaReplyList();
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }
}
