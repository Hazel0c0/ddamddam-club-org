package kr.co.ddamddam.project.dto.response;

import kr.co.ddamddam.project.dto.page.ProjectPageResponseDTO;
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
  private ProjectPageResponseDTO pageInfo; // 페이지 렌더링 정보
  private List<ProjectDetailResponseDTO> projects; // 게시물 렌더링 정보
}
