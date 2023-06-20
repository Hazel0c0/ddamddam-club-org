package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.mypage.dto.page.PageMaker;
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
    private PageMaker pageInfo;
    private List<ChatRoomResponseDTO> chatRoomList;
}
