package kr.co.ddamddam.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatAllListResponseDTO {

    private int count; // 채팅 entity
    private String nickName; // 멘토 게시판 entity
    private String content; // 메세지 entity
    @JsonFormat(pattern = "hh:mm a")
    private LocalDateTime time; // 메세지 entity

}
