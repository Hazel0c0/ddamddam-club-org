package kr.co.ddamddam.qna.qnaBoard.repository;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
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
    List<Qna> findTop3ByOrderByQnaViewDesc();

//    @Query("SELECT qr FROM Qna q JOIN QnaReply qr WHERE qr.qna.qnaIdx = :qnaIdx ORDER BY qr.qnaReplyDate DESC")
//@Query("SELECT qr FROM QnaReply qr WHERE qr.qna.qnaIdx = :boardIdx ORDER BY qr.qnaReplyDate ASC")



}