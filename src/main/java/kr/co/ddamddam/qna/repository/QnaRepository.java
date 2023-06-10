package kr.co.ddamddam.qna.repository;

import kr.co.ddamddam.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    /**
     * QNA 게시글 조회순 TOP3 정렬
     */
    List<Qna> findTop3ByOrderByQnaViewDesc();
}
