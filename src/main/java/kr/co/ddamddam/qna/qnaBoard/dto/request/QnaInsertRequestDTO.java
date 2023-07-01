package kr.co.ddamddam.qna.qnaBoard.dto.request;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * QNA 게시글 작성, 수정 요청 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaInsertRequestDTO {

    private Long boardIdx;
    @NotBlank
    @Size(min = 1, max = 100)
    private String boardTitle;
    @NotBlank
    @Size(min = 1, max = 1000)
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
