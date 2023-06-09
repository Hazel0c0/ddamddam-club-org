package kr.co.ddamddam.qna.dto.request;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaHashtag;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import java.util.List;

/**
 * QNA 게시글 작성 요청 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaInsertRequestDTO {

    // TODO : Validated 처리
    private String boardTitle;
    private String boardContent;
    private List<QnaHashtag> QnaHashtagList;

    // DTO 를 Entity 로 변환
    public Qna toEntity(User user) {
        return Qna.builder()
                .qnaTitle(this.boardTitle)
                .qnaContent(this.boardContent)
                .qnaWriter(user.getUserNickname())
                .qnaHashtag(this.getQnaHashtagList())
                .user(user)
                .build();
    }

}
