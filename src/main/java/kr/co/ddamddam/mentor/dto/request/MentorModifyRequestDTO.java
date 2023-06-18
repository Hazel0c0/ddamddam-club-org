package kr.co.ddamddam.mentor.dto.request;

import kr.co.ddamddam.mentor.entity.Mentor;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorModifyRequestDTO {

    @NotNull
    private Long mentorIdx;

    @NotBlank
    @Size(min = 1 ,max = 30)
    private String mentorTitle;

    @NotBlank
    private String mentorContent;

    @NotBlank
    private String mentorSubject;

    @NotBlank
    private String mentorCurrent;

    @NotBlank
    private String mentorCareer;

//    @NotBlank
    private int mentorMentee;



    // dto를 엔터티로 변환하는 메서드
    public Mentor toEntity(){
        return Mentor.builder()
                .mentorTitle(this.mentorTitle)
                .mentorContent(this.mentorContent)
                .mentorSubject(this.mentorSubject)
                .mentorCurrent(this.mentorCurrent)
                .mentorMentee(this.mentorMentee)
                .mentorCareer(this.mentorCareer)
                .build();
    }


}