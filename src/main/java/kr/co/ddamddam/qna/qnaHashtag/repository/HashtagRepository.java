package kr.co.ddamddam.qna.qnaHashtag.repository;

import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    int countHashtagsByHashtagContent(String hashtagContent);

    Hashtag findByHashtagContent(String hashtagContent);
}
