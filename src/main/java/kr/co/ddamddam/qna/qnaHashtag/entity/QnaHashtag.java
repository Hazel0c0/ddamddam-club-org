package kr.co.ddamddam.qna.qnaHashtag.entity;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_qna_hashtag")
public class QnaHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_hashtag_idx")
    private Long qnaHashtagIdx;

    @Column(name = "qna_hashtag_name", nullable = false, length = 10)
    private String qnaHashtagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_idx") // FK
    private Qna qna;
}
