package kr.co.ddamddam.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDTO {
    private Long messageId;
    private String message;
    private UserResponseDTO sender;
    private LocalDateTime sentAt;
}
