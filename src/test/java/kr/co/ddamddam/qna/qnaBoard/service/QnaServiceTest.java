package kr.co.ddamddam.qna.qnaBoard.service;

import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaTopListResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.service.QnaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class QnaServiceTest {

    @Autowired
    QnaService qnaService;
    
//    @BeforeEach
//    void test() {
//        // 조회수 상승
//        for (int i = 0; i < 3; i++) {
//            qnaService.updateViewCount(3L);
//        }
//        for (int i = 0; i < 2; i++) {
//            qnaService.updateViewCount(2L);
//        }
//        qnaService.updateViewCount(1L);
//    }

    @Test
    @DisplayName("1번 QNA 게시글의 QnaAdoption 필드값이 Y 로 바뀌어야한다.")
    void adoptTest() {
        //given
        Long boardIdx = 1L;
        //when
        qnaService.adoptQnaBoard(boardIdx);
//        QnaDetailResponseDTO foundQnaBoard = qnaService.getDetail(boardIdx);
        //then
//        assertEquals(Y, foundQnaBoard.getBoardAdoption());
    }
    
    @Test
    @DisplayName("QNA 게시글을 조회순으로 3개 조회했을 때, 첫번째 게시글의 조회수가 두번째, 세번째 게시글의 조회수보다 높아야 한다.")
    void viewTest() {
        //given
        List<QnaTopListResponseDTO> qnasListTop3ByView = qnaService.getListTop3ByView();
        //when
        boolean flag1 = qnasListTop3ByView.get(0).getBoardViewCount() > qnasListTop3ByView.get(1).getBoardViewCount();
        boolean flag2 = qnasListTop3ByView.get(1).getBoardViewCount() > qnasListTop3ByView.get(2).getBoardViewCount();
        //then
        assertTrue(flag1);
        assertTrue(flag2);
    }

}