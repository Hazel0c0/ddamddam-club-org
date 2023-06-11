package kr.co.ddamddam.chat.entity;

import kr.co.ddamddam.mentor.entity.Mentee;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_idx")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_idx")
    private User receiver;

    // 채팅 메시지와의 관계 설정
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ChatMessage> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom")
    @Builder.Default
    private List<Mentee> mentee = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "mentor_idx")
    private Mentor mentor;
}

