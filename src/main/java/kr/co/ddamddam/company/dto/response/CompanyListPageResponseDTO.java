package kr.co.ddamddam.company.dto.response;

import kr.co.ddamddam.company.dto.page.PageResponseDTO;
import kr.co.ddamddam.review.dto.response.ReviewListResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListPageResponseDTO {
    private int count; // 총게시물수
    private PageResponseDTO pageInfo; //페이지 렌더링 정보
    private List<CompanyListResponseDTO> responseList; //게시물 렌더링 정보
}
