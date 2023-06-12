package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.dto.page.PageResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListPageResponseDTO {

  private int count; // 총게시물 수
  private PageResponseDTO pageInfo; // 페이지 렌더링 정보
  private List<ProjectListResponseDTO> projects; // 게시물 렌더링 정보
}
