package kr.co.ddamddam.review.dto.response;

import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponseDTO {
    private int count; // 총게시물수
    private PageResponseDTO pageInfo; //페이지 렌더링 정보
    private List<ReviewDetailResponseDTO> employmentList; //게시물 렌더링 정보

}
