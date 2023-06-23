package kr.co.ddamddam.mypage.dto;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageProjectResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageProjectPageResponseDTO {
    private int count; // 총게시물 수
    private PageResponseDTO pageInfo; // 페이지 렌더링 정보
    private List<MypageProjectResponseDTO> posts; // 게시물 렌더링 정보

}
