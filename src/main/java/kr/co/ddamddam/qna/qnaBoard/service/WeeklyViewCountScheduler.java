package kr.co.ddamddam.qna.qnaBoard.service;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WeeklyViewCountScheduler {

    private final QnaRepository qnaRepository;

    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 오전 0시에 자동 실행
    public void resetWeeklyViewCount() {
        List<Qna> qnaList = qnaRepository.findAll();

        // 모든 QNA 게시글의 주간 조회수 초기화
        for (Qna qna : qnaList) {
            qna.setWeeklyViewCount(0);
        }

        qnaRepository.saveAll(qnaList);
    }
}
