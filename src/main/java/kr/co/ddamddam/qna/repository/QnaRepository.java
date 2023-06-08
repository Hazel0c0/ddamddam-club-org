package kr.co.ddamddam.qna.repository;

import kr.co.ddamddam.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}
