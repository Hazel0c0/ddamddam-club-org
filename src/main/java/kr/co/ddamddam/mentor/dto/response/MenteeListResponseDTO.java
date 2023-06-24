package kr.co.ddamddam.mentor.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenteeListResponseDTO {

    List<MenteeResponseDTO> menteeResponseDTOList;
    String mentorNickname;
}
