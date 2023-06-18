package kr.co.ddamddam.mentor.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.ddamddam.mentor.entity.Mentor;
import lombok.*;

import java.io.PipedReader;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorDetailResponseDTO {


    private Long idx;
    private String title;
    private String content;
    private String subject;
    private String current;
    private int mentee;
    private String nickName;
    private String profile;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime date;
    private String career;
    private Long userIdx;

    public MentorDetailResponseDTO(Mentor mentor){
        this.title = mentor.getMentorTitle();
        this.content = mentor.getMentorContent();
        this.subject = mentor.getMentorSubject();
        this.current = mentor.getMentorCurrent();
        this.date = mentor.getMentorDate();
        this.mentee = mentor.getMentorMentee();
    }

}
