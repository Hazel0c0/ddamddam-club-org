package kr.co.ddamddam.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDTO {
    private Long roomId;
    private UserResponseDTO sender;
    private UserResponseDTO receiver;
}
