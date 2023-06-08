package kr.co.ddamddam.chat.controller;

import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatRoomRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/rooms")
    public ResponseEntity<ChatRoomResponseDTO> createChatRoom(@RequestBody ChatRoomRequestDTO requestDTO) {
        ChatRoomResponseDTO responseDTO = chatService.createChatRoom(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

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

