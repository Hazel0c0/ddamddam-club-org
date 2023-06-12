package kr.co.ddamddam.qna.qnaBoard.dto.response;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import lombok.*;

import java.util.List;

/**
 * QNA 페이지 처리를 하는 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaListPageResponseDTO {

    private int count;
    private PageResponseDTO pageInfo;
    private List<QnaListResponseDTO> qnas;

}
