package kr.co.ddamddam.mentor.entity;


import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@ToString(exclude = {"user", "chatRooms"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_idx")
    private Long mentorIdx;

    @Column(name = "mentor_title", nullable = false)
    private String mentorTitle;

    @Column(name = "mentor_content",nullable = false)
    private String mentorContent;

    @Column(name = "mentor_subject",nullable = false)
    private String mentorSubject;

    @Column(name = "mentor_current",nullable = false)
    private String mentorCurrent;

    @Column(name = "mentor_career",nullable = false)
    private String mentorCareer;

    @CreationTimestamp
    @Column(name = "mentor_date",updatable = false)
    private LocalDateTime mentorDate; // 작성시간

    @Column(name = "mentor_mentee",columnDefinition = "Integer default 0")
    private int mentorMentee; // 멘티 인원 제한 컬럼

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "mentor")
    @Builder.Default
    private List<ChatRoom> chatRooms = new ArrayList<>();
}
