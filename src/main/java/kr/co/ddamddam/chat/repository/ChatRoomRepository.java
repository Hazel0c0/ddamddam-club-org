package kr.co.ddamddam.chat.repository;

import kr.co.ddamddam.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 추가적인 쿼리 메서드가 필요한 경우 작성
    List<ChatRoom> findByMentorMentorIdx(Long mentorIdx);
    // 멘티의 아이디로 채팅방 찾기
    ChatRoom findByMentorMentorIdxAndSenderUserIdx(Long mentorIdx, Long senderIdx);
    ChatRoom findByMentorMentorIdxAndReceiverUserIdx(Long mentorIdx, Long receiverIdx);
}