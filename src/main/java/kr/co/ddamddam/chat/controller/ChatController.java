package kr.co.ddamddam.chat.controller;

import kr.co.ddamddam.chat.dto.request.ChatMentorDetailRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatRoomRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.chat.service.ChatService;
import kr.co.ddamddam.config.security.TokenUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ddamddam/chat")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/rooms")
    public ResponseEntity<?> createChatRoom(
            @RequestBody ChatRoomRequestDTO dto
            , @AuthenticationPrincipal TokenUserInfo tokenUserInfo
    ) {
        log.info("requestDTO 들어옴: {}",dto);

            ChatRoomResponseDTO responseDTO = chatService.createChatRoom(dto,tokenUserInfo);
            log.info("responseDTO 보냄 : {}", responseDTO.getRoomId());
            return ResponseEntity.ok(responseDTO);
    }

    //멘티 채팅 저장
    @PostMapping("/mentee/{mentorIdx}/messages")
    public ResponseEntity<ChatMessageResponseDTO> sendMessage(
            @PathVariable("mentorIdx") Long mentorId,
            @RequestBody ChatMessageRequestDTO requestDTO
    ) {
        log.info("메세지 저장:{}",requestDTO.getSenderId());
        ChatMessageResponseDTO responseDTO = chatService.sendMessage(mentorId, requestDTO);
        log.info("메세지 출력:{}",responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    //멘토 채팅 저장
    @PostMapping("/mentor/{roomIdx}/messages")
    public ResponseEntity<ChatMessageResponseDTO> saveMentorMsg(
            @PathVariable("roomIdx") Long roomId,
            @RequestBody ChatMessageRequestDTO requestDTO
    ) {
        log.info("메세지 저장:{}",requestDTO.getSenderId());
        ChatMessageResponseDTO responseDTO = chatService.saveMentorMessage(roomId, requestDTO);
        log.info("메세지 출력:{}",responseDTO);
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
    @GetMapping("/mentee/list/{roomIdx}")
    public ResponseEntity<?> detail(
            @PathVariable Long roomIdx
            ,@AuthenticationPrincipal TokenUserInfo userInfo
    ){
        Long senderIdx = Long.valueOf(userInfo.getUserIdx());
        log.info("userIdx : {}",senderIdx);
        try {
            List<ChatMessageResponseDTO> list = chatService.getDetail(roomIdx, senderIdx);
            return ResponseEntity.ok().body(list);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("채팅 기록이 없습니다");
        }
    }

    // 멘토의 채팅방 개별 조회 (채팅 내용으로 바로 뿌려주기)
    @GetMapping("/mentor/chatroom/detail")
    public ResponseEntity<?> chatList(
            ChatMentorDetailRequestDTO dto
            ){
        log.info("respons cahhhhhh: {}",dto);
        try {
            List<ChatMessageResponseDTO> list = chatService.getMentorDetail(dto);
            return ResponseEntity.ok().body(list);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("채팅 기록이 없습니다");
        }
    }

    // 채팅방 삭제
    @DeleteMapping("/{roomId}/{mentorIdx}")
    public ResponseEntity<?> delete(
            @PathVariable Long roomId
            ,@PathVariable Long mentorIdx
            ,@AuthenticationPrincipal TokenUserInfo tokenUserInfo
    ){

        chatService.delete(roomId,tokenUserInfo);
        List<ChatMessageResponseDTO> list = chatService.getList(mentorIdx);

        return ResponseEntity.ok().body(list);
    }


    // 기타 API 엔드포인트 (채팅방 목록 조회, 채팅 메시지 조회 등) 추가
}

