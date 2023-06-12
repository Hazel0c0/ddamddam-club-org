package kr.co.ddamddam.qna.qnaBoard.dto.request;

import lombok.*;

import java.util.List;

/**
 * QNA 게시글 수정 요청 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaModifyRequestDTO {

    // TODO : Validated 처리
    private String boardTitle;
    private String boardContent;
    private List<String> hashtagList;
//
//    //
//    public QnaModifyRequestDTO(
//            QnaInsertRequestDTO dto,
//            List<HashtagMapping> newHashtagMappingList
//    ) {
//        this.boardTitle = dto.getBoardTitle();
//        this.boardContent = dto.getBoardContent();
//        for (HashtagMapping hashtagMapping : newHashtagMappingList) {
//            this.hashtagList.add()
//        }
//    }

}
