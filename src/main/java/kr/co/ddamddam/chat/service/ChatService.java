package kr.co.ddamddam.chat.service;

import kr.co.ddamddam.chat.dto.request.ChatMentorDetailRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatMessageRequestDTO;
import kr.co.ddamddam.chat.dto.request.ChatRoomRequestDTO;
import kr.co.ddamddam.chat.dto.response.ChatMessageResponseDTO;
import kr.co.ddamddam.chat.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.chat.dto.response.UserResponseDTO;
import kr.co.ddamddam.chat.entity.ChatMessage;
import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.chat.repository.ChatMessageRepository;
import kr.co.ddamddam.chat.repository.ChatRoomRepository;
import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final ValidateToken validateToken;


    /**
     * 접속한 멘티가 채팅방을 만들면서 상대방은 멘토로 고정
     * 멘토는 멘토게시글에서 user Entity를 가져와서 DB에 저장
     * @param dto
     * @param
     * @return
     */
    public ChatRoomResponseDTO createChatRoom(ChatRoomRequestDTO dto, TokenUserInfo tokenUserInfo) {

//        validateToken.validateToken(tokenUserInfo);

        log.info("token들어와: {}",tokenUserInfo);


        Long senderIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        Mentor mentor = mentorRepository.findById(dto.getMentorIdx()).orElseThrow( () ->
                {
                   throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, dto.getMentorIdx());
                }
        );
        ChatRoom findByChatRoomUser = chatRoomRepository.findByMentorMentorIdxAndSenderUserIdx(dto.getMentorIdx(), senderIdx);

        log.info("USER: {}",mentor.getUser().getUserIdx());

        // 멘티 입장에서 채팅방 생성
        if (findByChatRoomUser == null && mentor.getUser().getUserIdx() != senderIdx) {
            log.info("멘티 입장에서 채팅방 생성: ");
            User sender = userRepository.findById(senderIdx)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid senderId"));

            Mentor findMentor = mentorRepository.findById(dto.getMentorIdx()).orElseThrow(
                    () -> { throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, dto.getMentorIdx());}
            );
            User receiver = findMentor.getUser();

            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setSender(sender);
            chatRoom.setReceiver(receiver);
            chatRoom.setMentor(findMentor);

            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

            return convertToChatRoomResponseDTO(savedChatRoom);
        }
        // 멘티 입장 후, 채팅방이 이미 생성되었을 떄
    else if (findByChatRoomUser != null && mentor.getUser().getUserIdx() != senderIdx){
            log.info("멘티 입장 후, 채팅방이 이미 생성되었을 떄");
            ChatRoomResponseDTO responseDTO = new ChatRoomResponseDTO();
            responseDTO.setRoomId(findByChatRoomUser.getRoomId());
            responseDTO.setSender(new UserResponseDTO(findByChatRoomUser.getSender()));
            responseDTO.setReceiver(new UserResponseDTO(findByChatRoomUser.getReceiver()));

            return responseDTO;
        }
    // 멘토 입장 후 채팅방 생성 불가
    else if(findByChatRoomUser == null && mentor.getUser().getUserIdx() == senderIdx){
            log.info("멘토 입장 후 채팅방 생성 불가");
            ChatRoomResponseDTO responseDTO = new ChatRoomResponseDTO();
            responseDTO.setRoomId(0L);
            responseDTO.setSender(new UserResponseDTO(userRepository.findById(senderIdx).orElseThrow()));
            responseDTO.setReceiver(new UserResponseDTO(mentor.getUser()));

            return responseDTO;

        }

        return null;
    }

    public ChatMessageResponseDTO sendMessage(Long mentorIdx, ChatMessageRequestDTO requestDTO) {
//        requestDTO.setSenderId(2L);
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
        log.info("chatRoom: {}",chatRoomList);
//        log.info("chatRoom size: {}",chatRoomList.get(chatRoomList.size()-1).getRoomId());
        if (!chatRoomList.isEmpty()){
            List<ChatMessageResponseDTO> collect = chatRoomList.stream().map(room -> {
                ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
                dto.setRoomId(room.getRoomId());
                dto.setSender(new UserResponseDTO(room.getSender()));

                if (!room.getMessages().isEmpty()) {
                    dto.setMessage(room.getMessages().get(room.getMessages().size() - 1).getContent());
                    dto.setSentAt(room.getMessages().get(room.getMessages().size() - 1).getSentAt());
                }

                return dto;
            }).collect(Collectors.toList());
            return collect;
        } else if (chatRoomList.get(0).getMessages().isEmpty()){
            return null;
        }
        else {
            return null;
        }

    }

    // 멘티 채팅방 메세지 조회
    public List<ChatMessageResponseDTO> getDetail(Long roomIdx, Long senderIdx) {
        ChatRoom senderUserId = chatRoomRepository.findByMentorMentorIdxAndSenderUserIdx(roomIdx, senderIdx);


        List<ChatMessageResponseDTO> responseDTOS = senderUserId.getMessages().stream().map(msg -> {
            ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
            dto.setRoomId(msg.getRoom().getRoomId());
            dto.setSentAt(msg.getSentAt());
            dto.setSender(new UserResponseDTO(msg.getSender()));
            dto.setMessage(msg.getContent());

            return dto;
        }).collect(Collectors.toList());

        return responseDTOS;

    }

    // 멘토 채팅방 메세지 조회
    public List<ChatMessageResponseDTO> getMentorDetail(ChatMentorDetailRequestDTO requestDTO) {

        Mentor mentor = mentorRepository.findById(requestDTO.getMentorIdx()).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findByMentorMentorIdxAndReceiverUserIdxAndSenderUserIdx
                (requestDTO.getMentorIdx(), mentor.getUser().getUserIdx(), requestDTO.getSenderIdx());

        List<ChatMessageResponseDTO> responseDTOS = chatRoom.getMessages().stream().map(msg -> {
            ChatMessageResponseDTO dto = new ChatMessageResponseDTO();
            dto.setRoomId(msg.getRoom().getRoomId());
            dto.setSentAt(msg.getSentAt());
            dto.setSender(new UserResponseDTO(msg.getSender()));
            dto.setMessage(msg.getContent());

            return dto;
        }).collect(Collectors.toList());

        return responseDTOS;

    }

    // 채팅방 삭제
    public void delete(Long roomId, TokenUserInfo tokenUserInfo) {

        validateToken.validateToken(tokenUserInfo);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> {throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, roomId);}
        );

        chatRoomRepository.delete(chatRoom);
    }

    public ChatMessageResponseDTO processChatMessage(ChatMessageRequestDTO requestDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(requestDTO.getRoomId())
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

    public ChatMessageResponseDTO saveMentorMessage(Long roomId, ChatMessageRequestDTO requestDTO) {

        ChatRoom findChatRoom = chatRoomRepository.findById(roomId).orElseThrow();

        User sender = userRepository.findById(requestDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid senderId"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoom(findChatRoom);
        chatMessage.setSender(sender);
        chatMessage.setContent(requestDTO.getMessage());
        chatMessage.setSentAt(LocalDateTime.now());

        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);

        return convertToChatMessageResponseDTO(savedChatMessage);

    }
}

