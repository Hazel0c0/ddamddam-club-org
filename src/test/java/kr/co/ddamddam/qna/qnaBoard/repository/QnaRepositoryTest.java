package kr.co.ddamddam.qna.qnaBoard.repository;

import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
import kr.co.ddamddam.qna.qnaHashtag.repository.HashtagRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(false)
class QnaRepositoryTest {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Test
    @DisplayName("QNA 게시글 더미데이터 50개 생성")
    void InsertBulk1() {

        for (int i = 1; i <= 120; i++) {

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
            Long index4 = (long) (Math.random() * 20 + 1); // 1 ~ 20

            if (index4 == 4L) index4 = 1L;
            if (index4 == 19L) index4 = 1L;

            Long finalIndex = index4;
            User user = userRepository.findById(1L).orElseThrow(() -> {
                throw new NotFoundUserException(ErrorCode.NOT_FOUND_USER, finalIndex);
            });

            Qna qna = Qna.builder()
                    .qnaTitle(randomTitle[index1])
                    .qnaContent(i+randomContent[index2])
                    .qnaWriter(user.getUserNickname())
                    .qnaAdoption(randomAdoption[index3])
                    .user(user)
                    .build();

            qnaRepository.save(qna);
        }
    }
    
    @Test
    @DisplayName("1~50번 게시글에 해시태그 더미 데이터 생성")
    void insertBulk2() {
        //given
        
        String[] randomHashtag = {"자바", "질문", "취업", "진로고민", "프론트엔드_고민", "백엔드_고민", "신입_개발자", "회사_문화", "퇴사_상담", "알고리즘", "C++", "파이썬", "학습서추천", "인텔리제이", "이클립스"};

        for (int i = 122; i <= 240; i++) {
            Long qnaIdx = (long) i;
            int index3 = (int) (Math.random() * 10); // 0 ~ 9 사이의 랜덤 정수

            Qna qna = qnaRepository.findById(qnaIdx).orElseThrow(() -> {
                throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, qnaIdx);
            });

            //when
            for (int j = 0; j <= index3; j++) {

                int index1 = (int) (Math.random() * 15); // 0 ~ 14 사이의 랜덤 정수

                Hashtag newHashtag = Hashtag.builder()
                        .hashtagContent(randomHashtag[index1])
                        .qna(qna)
                        .build();
                Hashtag saved = hashtagRepository.save(newHashtag);
            }
        }
        //then
    }

}