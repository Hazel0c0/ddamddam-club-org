package kr.co.ddamddam.qna.qnaReply.repository;

import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {
    List<QnaReply> findByQnaQnaIdxOrderByQnaReplyDateAsc(Long boardIdx);

}
