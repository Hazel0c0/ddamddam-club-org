package kr.co.ddamddam.mentor.entity;


import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mentorIdx;

    @Column(nullable = false)
    private String mentorTitle;

    @Column(nullable = false)
    private String mentorContent;

    @Column(nullable = false)
    private String mentorSubject;

    @Column(nullable = false)
    private String mentorCurrent;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime mentorDate; // 작성시간

    @Column(columnDefinition = "Integer default 0")
    private int mentorLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;
}
