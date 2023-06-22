package kr.co.ddamddam.mypage.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponseDTO {

    private String chatType; // 채팅방 타입

    private Long roomIdx;
    private Long mentorIdx;
    private String current;
    private String title;
    private String subject;

}
