package kr.co.ddamddam.project.dto.page;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PageDTO {
  private int page;
  public int size;

  public PageDTO() {
    this.page = 1;
    this.size = 10;
  }
}
