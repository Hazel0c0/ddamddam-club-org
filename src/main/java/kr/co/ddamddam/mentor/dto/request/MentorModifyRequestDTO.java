package kr.co.ddamddam.mentor.dto.request;

import kr.co.ddamddam.mentor.entity.Mentor;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorModifyRequestDTO {


    @NotBlank
    @Size(min = 1 ,max = 30)
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String subject;

    @NotBlank
    private String current;



    // dto를 엔터티로 변환하는 메서드
    public Mentor toEntity(){
        return Mentor.builder()
                .mentorTitle(this.title)
                .mentorContent(this.content)
                .mentorSubject(this.subject)
                .mentorCurrent(this.current)
                .build();
    }


}