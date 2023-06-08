package kr.co.ddamddam.chat.repository;

import kr.co.ddamddam.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 추가적인 쿼리 메서드가 필요한 경우 작성
}