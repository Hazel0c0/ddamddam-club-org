package kr.co.ddamddam.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatValidateRequestDTO {
    private Long mentorIdx;
    private Long roomId;
    private Long senderId;
    private String name;
    private String msg;
    private String date;
}
