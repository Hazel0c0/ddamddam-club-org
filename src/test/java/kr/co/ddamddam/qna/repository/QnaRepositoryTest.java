package kr.co.ddamddam.qna.repository;

import kr.co.ddamddam.qna.entity.Qna;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
class QnaRepositoryTest {

    @Autowired
    private QnaRepository qnaRepository;

    @Test
    @DisplayName("QNA 게시글 더미데이터 50개 생성")
    void InsertBulk() {

        for (int i = 11; i <= 50; i++) {

            Qna qna = Qna.builder()
                    .qnaTitle("Qna Title " + i)
                    .qnaContent("Qna Content " + i)
                    .qnaWriter("Writer " + i)
                    .build();

            qnaRepository.save(qna);
        }
    }

}