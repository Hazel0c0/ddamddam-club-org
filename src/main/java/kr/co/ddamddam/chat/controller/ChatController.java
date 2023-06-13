package kr.co.ddamddam.chat.controller;

import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatRoomRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatAllListResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ddamddam/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/rooms")
    public ResponseEntity<?> createChatRoom(
            @RequestBody ChatRoomRequestDTO dto
    ) {
        Long userId = 1L;
        ChatRoomResponseDTO responseDTO = chatService.createChatRoom(dto,userId);
        log.info("requestDTO 들어옴: {}",dto);
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

    // 멘토의 채팅방 전체 조회
    // 멘토인지 검증은 프론트 단에서 검증하기(수정,삭제처럼)
    @GetMapping("/mentor/list/{mentorIdx}")
    public ResponseEntity<?> list(
            @PathVariable Long mentorIdx
    ){
        List<ChatMessageResponseDTO> list = chatService.getList(mentorIdx);

        return ResponseEntity.ok().body(list);
    }

    // 멘티의 채팅방 개별 조회 (채팅 내용으로 바로 뿌려주기)
    @GetMapping("/mentee/list/{mentorIdx}")
    public ResponseEntity<?> detail(
            @PathVariable Long mentorIdx
    ){
        Long senderIdx = 1L;

        List<ChatMessageResponseDTO> list = chatService.getDetail(mentorIdx,senderIdx);

        return ResponseEntity.ok().body(list);
    }

    // 채팅방 삭제
    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> delete(
            @PathVariable Long roomId
    ){

        chatService.delete(roomId);

        return ResponseEntity.ok().body("삭제 성공!");
    }


    // 기타 API 엔드포인트 (채팅방 목록 조회, 채팅 메시지 조회 등) 추가
}

