package kr.co.ddamddam.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequestDTO {
    private Long senderId;
    private Long receiverId;
    private Long mentorIdx;
}
