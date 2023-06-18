package kr.co.ddamddam.mypage.dto.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Setter @Getter
@ToString
public class PageResponseDTO<T> extends PageImpl<T> {

    private int startPage; // 시작페이지
    private int endPage;
    private int currentPage;
    private boolean prev;
    private boolean next;
    private int totalCount;

    // 한페이지에 배치할 페이지 수 (1~9 // 10~18)
    private static final int PAGE_COUNT = 9;

    public PageResponseDTO(List<T> content, Pageable pageable, long total
    ) {
        super(content, pageable, total);

        this.totalCount = (int) total;
        this.currentPage = pageable.getPageNumber() + 1;
        this.endPage = (int) (Math.ceil((double) currentPage / PAGE_COUNT) * PAGE_COUNT);
        this.startPage = endPage - PAGE_COUNT + 1;

        int realEnd = getTotalPages();

        if (realEnd < this.endPage) this.endPage = realEnd;

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }
}