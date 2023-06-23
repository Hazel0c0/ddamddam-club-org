package kr.co.ddamddam.mentor.dto.page;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
@ToString @EqualsAndHashCode
public class MentorPageDTO {

    private int page;
    private int size;

    public MentorPageDTO(){
        this.page = 1;
        this.size = 9;
    }
}