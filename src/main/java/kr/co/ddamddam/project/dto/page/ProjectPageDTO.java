package kr.co.ddamddam.project.dto.page;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProjectPageDTO {
  private int page;
  public int size;

  public ProjectPageDTO() {
    this.page = 1;
    this.size = 9;
  }
}
