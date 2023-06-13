package kr.co.ddamddam.qna.qnaBoard.dto.page;

import lombok.*;

/**
 * qna 패키지 전체에서 사용합니다.
 */

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PageDTO {
    
    private int page;
    private int size;
    
    // 첫 페이지는 1로 지정, 한 페이지에 5개의 게시글 보이도록 지정
    public PageDTO() {
        this.page = 1;
        this.size = 5;
    }
    
}
