package kr.co.ddamddam.mentor.dto.response;

import kr.co.ddamddam.mentor.dto.page.MentorPageResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorListResponseDTO {

    private int count;
    private MentorPageResponseDTO pageInfo;
    private List<MentorDetailResponseDTO> mentors;

}
