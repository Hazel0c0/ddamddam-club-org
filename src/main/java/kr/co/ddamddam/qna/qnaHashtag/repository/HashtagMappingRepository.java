package kr.co.ddamddam.qna.qnaHashtag.repository;

import kr.co.ddamddam.qna.qnaHashtag.entity.HashtagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagMappingRepository extends JpaRepository<HashtagMapping, Long> {
    List<HashtagMapping> findByQnaQnaIdx(Long qnaIdx);
}
