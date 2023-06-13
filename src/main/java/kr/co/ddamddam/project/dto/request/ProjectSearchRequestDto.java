package kr.co.ddamddam.project.dto.request;

import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectSearchRequestDto {

  private String sort;
  private String position;
  private String search;
  private String keyword;
}
