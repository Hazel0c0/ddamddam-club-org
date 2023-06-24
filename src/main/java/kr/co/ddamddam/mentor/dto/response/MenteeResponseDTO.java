package kr.co.ddamddam.mentor.dto.response;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenteeResponseDTO {
    private Long menteeIdx;
    private Long roomId;
    private String menteeNickname;
    private Long menteeUserIdx;
    private Long mentorIdx;
}
