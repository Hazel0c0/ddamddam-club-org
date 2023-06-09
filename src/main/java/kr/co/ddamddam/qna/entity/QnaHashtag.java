package kr.co.ddamddam.qna.entity;

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
    private Long QnaHashtagIdx;

    @Column(name = "qna_hashtag_name", nullable = false, length = 10)
    private String QnaHashtagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_idx") // FK
    private Qna qna;
}
