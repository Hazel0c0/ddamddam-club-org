package kr.co.ddamddam.qna.qnaReply.repository;

import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QnaReplyRepositoryTest {

    @Autowired
    QnaReplyRepository qnaReplyRepository;
    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("QNA 댓글 더미데이터 20개 생성")
    void insertBulk() {

        for (int i = 1; i <= 20 ; i++) {

            QnaReply qnaReply = QnaReply.builder()
                    .qnaReplyContent("댓글 내용" + i)
                    .qnaReplyWriter("댓글 작성자" + i)
                    .qna(qnaRepository.findById(1L).orElseThrow(() -> {
                        throw new RuntimeException();
                    }))
                    .user(userRepository.findById(1L).orElseThrow(() -> {
                        throw new RuntimeException();
                    }))
                    .build();

            qnaReplyRepository.save(qnaReply);
        }
    }

}