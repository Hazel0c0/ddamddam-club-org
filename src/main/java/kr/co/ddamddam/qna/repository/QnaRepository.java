package kr.co.ddamddam.qna.repository;

import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    /**
     * QNA 게시글 조회순 TOP3 정렬
     */
    List<Qna> findTop3ByOrderByQnaViewDesc();

    /**
     * QNA 게시글 중 채택 or 미채택 게시글만 조회
     *
     * @param qnaAdoption - 채택 또는 미채택 상태 (Y or N)
     * @return - 채택 또는 미채택 상태인 QNA 게시글 리스트
     */
//    Page<Qna> findByQnaAdoption(QnaAdoption qnaAdoption, Pageable pageable);

}
