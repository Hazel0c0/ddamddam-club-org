package kr.co.ddamddam.qna.service;

import kr.co.ddamddam.qna.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.entity.QnaAdoption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static kr.co.ddamddam.qna.entity.QnaAdoption.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class QnaServiceTest {

    @Autowired
    QnaService qnaService;

    @Test
    @DisplayName("1번 QNA 게시글의 QnaAdoption 필드값이 Y 로 바뀌어야한다.")
    void adoptTest() {
        //given
        Long boardIdx = 1L;
        //when
        qnaService.adoptQnaBoard(boardIdx);
        QnaDetailResponseDTO foundQnaBoard = qnaService.getDetail(boardIdx);
        //then
        assertEquals(Y, foundQnaBoard.getBoardAdoption());
    }

}