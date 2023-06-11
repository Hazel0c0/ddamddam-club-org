package kr.co.ddamddam.mentor.dto.page;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
@ToString @EqualsAndHashCode
public class PageDTO {

    private int page;
    private int size;
    private String sort;

    public PageDTO(){
        this.page = 1;
        this.size = 9;
        this.sort = "";
    }
}