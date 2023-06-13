package kr.co.ddamddam.qna.qnaBoard.repository;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.QnaErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @DisplayName("QNA 게시글 더미데이터 10개 생성")
    void InsertBulk2() {

        for (int i = 1; i <= 20; i++) {

            Qna qna = Qna.builder()
                    .qnaTitle("큐앤에이큐앤에이큐앤에이큐앤큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이" + i)
                    .qnaContent("큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이큐앤에이" + i)
                    .qnaWriter("Writer " + i)
                    .build();

            qnaRepository.save(qna);
        }
    }
    
    @Test
    @Transactional
    @DisplayName("QNA 게시글의 해시태그 확인")
    void HashtagViewTest() {
        //given
        Long boardIdx = 86L;
        //when
        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(QnaErrorCode.NOT_FOUND_BOARD, boardIdx);
        });
        //then
        System.out.println("qna.getHashtagMappingList() = " + qna.getHashtagMappingList());
    }

}