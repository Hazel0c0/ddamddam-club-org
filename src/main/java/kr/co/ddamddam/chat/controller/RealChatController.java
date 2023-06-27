package kr.co.ddamddam.chat.controller;

import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class RealChatController {

    private final ChatService chatService;
//    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat") // 클라이언트에서 메시지 전송 시 '/chat'으로 매핑
//    @SendTo("/topic/chat") // 구독 중인 클라이언트에게 메시지 전송
    public ChatMessageResponseDTO sendMessage(@Payload ChatMessageRequestDTO requestDTO) {
        log.info("dto 제발: {}",requestDTO);
        // 채팅 메시지 처리 로직
        ChatMessageResponseDTO responseDTO = chatService.processChatMessage(requestDTO);
//        simpMessagingTemplate.convertAndSend("/topic/"+requestDTO.getSenderId(),requestDTO.getMessage());
        log.info("보낸다:{}",responseDTO);
        return responseDTO;
    }
}

