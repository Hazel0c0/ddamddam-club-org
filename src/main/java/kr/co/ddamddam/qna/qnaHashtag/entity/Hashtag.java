package kr.co.ddamddam.qna.qnaHashtag.entity;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "hashtagMappingList")
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

    @Column(name = "hashtag_content", nullable = false, length = 10)
    private String hashtagContent; // 해시태그 내용

    @OneToMany(mappedBy = "hashtag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<HashtagMapping> hashtagMappingList = new ArrayList<>();

}
