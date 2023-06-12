package kr.co.ddamddam.qna.qnaHashtag.entity;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import lombok.*;

import javax.persistence.*;

/**
 * QNA 게시글과 해시태그의 다대다 관계를 풀어주는 조인 테이블입니다.
 */

@Setter
@Getter
@ToString(exclude = {"hashtag", "qna"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hashtag_mapping")
public class HashtagMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_mapping_idx")
    private Long hashtagMappingIdx; // 식별번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_idx", nullable = false)
    private Hashtag hashtag; // 해시태그 테이블

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_idx", nullable = false)
    private Qna qna; // QNA 게시글 테이블
}