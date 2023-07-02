package kr.co.ddamddam.mentor.dto.request;

import kr.co.ddamddam.mentor.entity.Mentor;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter @Setter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorWriteRequestDTO {


    @NotBlank
    @Size(min = 1 ,max = 100)
    private String mentorTitle;

    @NotBlank
    @Size(min = 1, max = 3000)
    private String mentorContent;

    @NotBlank
    private String mentorSubject;

    @NotBlank
    private String mentorCurrent;

    private int mentorMentee;



    // dto를 엔터티로 변환하는 메서드
    public Mentor toEntity(){
        return Mentor.builder()
                .mentorTitle(this.mentorTitle)
                .mentorContent(this.mentorContent)
                .mentorSubject(this.mentorSubject)
                .mentorCurrent(this.mentorCurrent)
                .mentorMentee(this.mentorMentee)
                .build();
    }


}

