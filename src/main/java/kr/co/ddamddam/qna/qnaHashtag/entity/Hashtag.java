package kr.co.ddamddam.qna.qnaHashtag.entity;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "qna")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hashtag")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_idx")
    private Long hashtagIdx; // 식별번호

    @Column(name = "hashtag_content", length = 15)
    private String hashtagContent; // 해시태그 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) // FK
    private Qna qna; // 연결된 게시글

}
