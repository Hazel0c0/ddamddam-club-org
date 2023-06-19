package kr.co.ddamddam.qna.qnaBoard.dto.request;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import java.util.ArrayList;
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
    private Long boardIdx;
    private String boardTitle;
    private String boardContent;
    private List<String> hashtagList;

    // DTO 를 Entity 로 변환
    public Qna toEntity(User user) {

        return Qna.builder()
                .qnaTitle(this.boardTitle)
                .qnaContent(this.boardContent)
                .qnaWriter(user.getUserNickname())
                .user(user)
                .build();
    }

}
