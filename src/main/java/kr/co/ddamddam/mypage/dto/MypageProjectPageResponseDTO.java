package kr.co.ddamddam.mypage.dto;

import kr.co.ddamddam.project.dto.page.PageResponseDTO;
import lombok.*;

import java.util.List;
import java.util.stream.DoubleStream;

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
    private List<MypageProjectPageResponseDTO> posts; // 게시물 렌더링 정보

}
