package kr.co.ddamddam.chat.service;

import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatRoomRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.chat.dto.response.UserResponseDTO;
import kr.co.ddamddam.chat.entity.ChatMessage;
import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.chat.repository.ChatMessageRepository;
import kr.co.ddamddam.chat.repository.ChatRoomRepository;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;


    public ChatRoomResponseDTO createChatRoom(ChatRoomRequestDTO dto, Long userId) {

        ChatRoom findByChatRoomUser = chatRoomRepository.findByMentorMentorIdxAndSenderUserIdx(dto.getMentorIdx(), userId);
//        log.info("해당 게시글에 채팅방 생성 이력이 있는지 : {}",findByChatRoomUser.toString());

        if (findByChatRoomUser == null) {
            log.info("채팅방 이력 없음: ");
            User sender = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid senderId"));

            Mentor findMentor = mentorRepository.findById(dto.getMentorIdx()).orElseThrow();
            User receiver = findMentor.getUser();

//        User receiver = userRepository.findById(requestDTO.getReceiverId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid receiverId"));

            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setSender(sender);
            chatRoom.setReceiver(receiver);
            chatRoom.setMentor(findMentor);

            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

            return convertToChatRoomResponseDTO(savedChatRoom);
        }
        else{

            ChatRoomResponseDTO responseDTO = new ChatRoomResponseDTO();
            responseDTO.setRoomId(findByChatRoomUser.getRoomId());
            responseDTO.setSender(new UserResponseDTO(findByChatRoomUser.getSender()));
            responseDTO.setReceiver(new UserResponseDTO(findByChatRoomUser.getReceiver()));

            return responseDTO;
        }
    }

    public ChatMessageResponseDTO sendMessage(Long mentorIdx, ChatMessageRequestDTO requestDTO) {
        ChatRoom senderUserIdx = chatRoomRepository.findByMentorMentorIdxAndSenderUserIdx(mentorIdx, requestDTO.getSenderId());

        ChatRoom chatRoom = chatRoomRepository.findById(senderUserIdx.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid roomId"));

        User sender = userRepository.findById(requestDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid senderId"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setContent(requestDTO.getMessage());
        chatMessage.setSentAt(LocalDateTime.now());

        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);

        return convertToChatMessageResponseDTO(savedChatMessage);
    }

    private ChatRoomResponseDTO convertToChatRoomResponseDTO(ChatRoom chatRoom) {
        ChatRoomResponseDTO responseDTO = new ChatRoomResponseDTO();
        responseDTO.setRoomId(chatRoom.getRoomId());
        responseDTO.setSender(convertToUserResponseDTO(chatRoom.getSender()));
        responseDTO.setReceiver(convertToUserResponseDTO(chatRoom.getReceiver()));
        return responseDTO;
    }

    private ChatMessageResponseDTO convertToChatMessageResponseDTO(ChatMessage chatMessage) {
        ChatMessageResponseDTO responseDTO = new ChatMessageResponseDTO();
        responseDTO.setMessageId(chatMessage.getId());
        responseDTO.setMessage(chatMessage.getContent());
        responseDTO.setSender(convertToUserResponseDTO(chatMessage.getSender()));
        responseDTO.setSentAt(chatMessage.getSentAt());
        return responseDTO;
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserIdx(user.getUserIdx());
        responseDTO.setUserName(user.getUserName());
        responseDTO.setUserNickname(user.getUserNickname());
        return responseDTO;
    }

    // 멘토가 보는 채팅방 (멘티들의 채팅방들이 다 보임)
    public List<ChatMessageResponseDTO> getList(Long mentorIdx) {

        List<ChatRoom> chatRoomList = chatRoomRepository.findByMentorMentorIdx(mentorIdx);

        List<ChatMessageResponseDTO> collect = chatRoomList.stream().map(room -> {
            ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
            dto.setRoomId(room.getRoomId());
            dto.setSender(new UserResponseDTO(room.getSender()));
            dto.setMessage(room.getMessages().get(room.getMessages().size() - 1).getContent());
            dto.setSentAt(room.getMessages().get(room.getMessages().size() - 1).getSentAt());
            dto.setMessageId(room.getMessages().get(room.getMessages().size() - 1).getId());

            return dto;
        }).collect(Collectors.toList());
        return collect;
    }

    // 멘티 채팅방 메세지 조회
    public List<ChatMessageResponseDTO> getDetail(Long mentorIdx, Long senderIdx) {
        ChatRoom senderUserId = chatRoomRepository.findByMentorMentorIdxAndSenderUserIdx(mentorIdx, senderIdx);


        List<ChatMessageResponseDTO> responseDTOS = senderUserId.getMessages().stream().map(msg -> {
            ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
            dto.setMessageId(msg.getId());
            dto.setRoomId(msg.getRoom().getRoomId());
            dto.setSentAt(msg.getSentAt());
            dto.setSender(new UserResponseDTO(msg.getSender()));
            dto.setMessage(msg.getContent());

            return dto;
        }).collect(Collectors.toList());

        return responseDTOS;

    }

    // 채팅방 삭제
    public void delete(Long roomId) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();

        chatRoomRepository.delete(chatRoom);
    }
}

