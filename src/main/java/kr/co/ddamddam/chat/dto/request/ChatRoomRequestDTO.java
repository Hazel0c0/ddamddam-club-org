package kr.co.ddamddam.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequestDTO {
    @NotBlank
    private Long senderId;
//    private Long receiverId;
    @NotBlank
    private Long mentorIdx;
}
