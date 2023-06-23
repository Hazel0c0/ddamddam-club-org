package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.mentor.dto.page.MentorPageResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageChatPageResponseDTO {

    private int count;
    private MentorPageResponseDTO pageInfo;
    private List<ChatRoomResponseDTO> chatRoomList;
}
