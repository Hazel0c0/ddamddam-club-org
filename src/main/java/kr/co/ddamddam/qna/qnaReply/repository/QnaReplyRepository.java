package kr.co.ddamddam.qna.qnaReply.repository;

import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {

    // 채택완료 댓글을 맨 처음으로 가져오고, 미채택 댓글은 최신순으로 정렬합니다.
    List<QnaReply> findByQnaQnaIdxOrderByQnaReplyAdoptionDescQnaReplyDateAsc(Long boardIdx);

}
