package kr.co.ddamddam.chat.entity;

import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence")
    @SequenceGenerator(name = "message_sequence", sequenceName = "message_sequence", allocationSize = 1)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(nullable = false, length = 2000)
    private String content;

    @CreationTimestamp
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom room;
}
