package kr.co.ddamddam.qna.qnaBoard.repository;

import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundUserException;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.QnaErrorCode;
import kr.co.ddamddam.user.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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

        for (int i = 1; i <= 50; i++) {

            String[] randomTitle = {
                    "위에 이런 이름과, 시인의 까닭입니다.",
                    "위에 하늘에는 마리아 이름과, 아직 봅니다.",
                    "사람들의 하나에 가슴속에 이름을 둘 봅니다.",
                    "차 프랑시스 까닭이요, 우는 부끄러운 하나에 아이들의 헤는 듯합니다.",
                    "내 가을로 그리고 듯합니다. 패, 멀리 아침이 풀이 버리었습니다."
            };

            String[] randomContent = {
                    "위에 이런 이름과, 시인의 까닭입니다. 위에 하늘에는 마리아 이름과, 아직 봅니다. 사람들의 하나에 가슴속에 이름을 둘 봅니다.",
                    "차 프랑시스 까닭이요, 우는 부끄러운 하나에 아이들의 헤는 듯합니다. 내 가을로 그리고 듯합니다. 패, 멀리 아침이 풀이 버리었습니다.",
                    "인도하겠다는 풍부하게 설산에서 가치를 피다. 풀이 이상, 찾아다녀도, 평화스러운 동력은 우리의 붙잡아 청춘의 행복스럽고 위하여서. 스며들어 크고 많이 황금시대를 할지니, 할지라도 힘있다. 가치를 그들에게 아름답고 시들어 작고 많이 황금시대다.",
                    "날카로우나 때까지 별과 남는 것은 사랑의 사막이다. 피에 소금이라 아니한 없으면 피가 우리의 보내는 튼튼하며, 피고, 보라.",
                    "위하여서, 얼음 이성은 같이, 피가 말이다. 듣기만 같은 무엇을 소금이라 이상이 커다란 소담스러운 끝까지 힘있다. 돋고, 바이며, 속에 그리하였는가? 물방아 인간이 보배를 쓸쓸하랴?"
            };

            QnaAdoption[] randomAdoption = {QnaAdoption.Y, QnaAdoption.N};

            int index1 = (int) (Math.random() * 5); // 0 ~ 4
            int index2 = (int) (Math.random() * 5); // 0 ~ 4
            int index3 = (int) (Math.random() * 2); // 0 ~ 1
            Long index4 = (long) (Math.random() * 14 + 1); // 1 ~ 14

            Qna qna = Qna.builder()
                    .qnaTitle(randomTitle[index1])
                    .qnaContent(randomContent[index2])
                    .qnaWriter("큐앤에이작성자" + i)
                    .qnaAdoption(randomAdoption[index3])
                    .user(userRepository.findById(index4).orElseThrow(() -> {
                        throw new NotFoundUserException(QnaErrorCode.NOT_FOUND_USER, index4);
                    }))
                    .build();

            qnaRepository.save(qna);
        }
    }

}