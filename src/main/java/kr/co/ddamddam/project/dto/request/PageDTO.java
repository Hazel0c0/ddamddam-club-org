package kr.co.ddamddam.project.dto.request;

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
