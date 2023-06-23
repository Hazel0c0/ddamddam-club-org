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

  private boolean like;
  private String position;
  private String keyword;
}
