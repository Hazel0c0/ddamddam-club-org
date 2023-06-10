package kr.co.ddamddam.chat.controller;

import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatRoomRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/rooms")
    public ResponseEntity<ChatRoomResponseDTO> createChatRoom(@RequestBody ChatRoomRequestDTO requestDTO) {
        ChatRoomResponseDTO responseDTO = chatService.createChatRoom(requestDTO);
        log.info("requestDTO 들어옴: {}",requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 채팅 주고받기
    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ChatMessageResponseDTO> sendMessage(
            @PathVariable("roomId") Long roomId,
            @RequestBody ChatMessageRequestDTO requestDTO
    ) {
        ChatMessageResponseDTO responseDTO = chatService.sendMessage(roomId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 기타 API 엔드포인트 (채팅방 목록 조회, 채팅 메시지 조회 등) 추가
}

