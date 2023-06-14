package kr.co.ddamddam.qna.qnaBoard.repository;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    /**
     * QNA 게시글 조회순 TOP3 정렬
     */
    List<Qna> findTop3ByOrderByViewCountDesc();

}
