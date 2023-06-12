package kr.co.ddamddam.qna.qnaReply.dto.page;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PageDTO {

    private int page;
    private int size;

    // 첫 페이지는 1로 지정, 한 페이지에 5개의 댓글 보이도록 지정
    public PageDTO() {
        this.page = 1;
        this.size = 5;
    }

}
