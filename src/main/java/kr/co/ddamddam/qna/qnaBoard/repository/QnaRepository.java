package kr.co.ddamddam.qna.qnaBoard.repository;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    /**
     * QNA 게시글 조회순 TOP3 정렬
     */
    List<Qna> findTop3ByOrderByViewCountDesc();

    @Query("SELECT DISTINCT q FROM Qna q " +
            "JOIN q.hashtagList h " +
            "WHERE q.qnaTitle LIKE %:keyword% " +
            "OR q.qnaContent LIKE %:keyword% " +
            "OR h.hashtagContent LIKE %:keyword%")
    Page<Qna> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Qna> findByUserUserIdx(@Param("userIdx") Long UserIdx);

    Page<Qna> findByQnaAdoption(QnaAdoption qnaAdoption, PageRequest pageable);
}
