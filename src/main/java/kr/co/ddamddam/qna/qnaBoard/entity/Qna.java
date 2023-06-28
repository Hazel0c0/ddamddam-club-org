package kr.co.ddamddam.qna.qnaBoard.entity;

import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"qnaReplyList", "hashtagList", "user"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_idx")
    private Long qnaIdx; // 식별번호

    @Column(name = "qna_title", nullable = false, length = 100)
    private String qnaTitle;

    @Column(name = "qna_content", nullable = false, length = 3000)
    private String qnaContent;

    @Column(name = "qna_writer", nullable = false, length = 10)
    private String qnaWriter;

    @CreationTimestamp // 데이터가 추가되는 시간을 값으로 설정합니다.
    @Column(name = "qna_date", nullable = false)
    private LocalDateTime qnaDate;

    @Column(name = "qna_view", nullable = false)
    @Builder.Default
    private int viewCount = 0;

    @Column(name = "qna_weekly_view", nullable = false)
    @Builder.Default
    private int weeklyViewCount = 0; // 주간 조회수

    @Column(name = "qna_reply", nullable = false)
    @Builder.Default
    private int replyCount = 0;

    @Column(name = "qna_adoption", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private QnaAdoption qnaAdoption = QnaAdoption.N; // 기본값: 채택되지 않은 상태인 N

    @OneToMany(mappedBy = "qna", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Hashtag> hashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "qna", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<QnaReply> qnaReplyList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx") // FK
    private User user;

    public void clearHashtagList() {
        hashtagList.clear();
    }

}
