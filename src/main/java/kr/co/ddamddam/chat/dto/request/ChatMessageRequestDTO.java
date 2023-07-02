package kr.co.ddamddam.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDTO {

    @NotBlank
    private Long roomId;
    @NotBlank
    @Size(min= 1, max = 2000)
    private String message;
    @NotBlank
    private Long senderId;
}
