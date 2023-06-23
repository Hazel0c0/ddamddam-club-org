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

        for (int i = 1; i <= 200; i++) {

            String[] randomTitle = {
                    "모든 국민은 법 앞에 평등하다. 누구든지 성별·종교 또는 사회적 신분에 의하여 정치적·경제적·사회적·문화적 생활의 모든 영역에 있어서 차별을 받지 아니한다.",
                    "이상의 품으며, 오직 아니다. 꽃 피가 자신과 것이다. 앞이 길을 밥을 이상의 황금시대다.",
                    "투명하되 밝은 끓는 사는가 철환하였는가?",
                    "심장은 광야에서 두기 피어나는 같으며, 봄바람이다. 청춘을 구하지 없는 길을 구하지 이상의 철환하였는가?",
                    "인간에 가슴이 열매를 심장은 수 보내는 없으면, 그들은 인류의 황금시대다."
            };

            String[] randomContent = {
                    "산야에 위하여 생의 넣는 인간에 있으랴? 청춘을 미묘한 그것은 방황하여도, 있다. 착목한는 생생하며, 따뜻한 공자는 우는 것이다.보라, 봄바람이다. 얼마나 간에 산야에 가치를 아니다. 붙잡아 끓는 스며들어 그들에게 대한 못할 끝까지 청춘이 그들은 것이다. 이것은 가치를 청춘 봄날의 그것은 그들은 방황하였으며, 불러 청춘은 것이다. 내려온 이상의 위하여 심장의 그들을 하는 보이는 바이며, 이 아름다우냐? 그들은 산야에 인생을 곳이 그들은 그들의 부패를 같이, 스며들어 봄바람이다. 용감하고 찾아다녀도, 고행을 인생에 장식하는 생의 대중을 뜨고, 있으랴? 할지니, 이상의 있는 것이다. 반짝이는 보이는 봄날의 천자만홍이 실현에 이것이야말로 위하여 따뜻한 무한한 보라.",
                    "않는 없으면, 끓는 뿐이다. 따뜻한 실현에 청춘의 미인을 황금시대다. 같지 보배를 것은 방황하여도, 우는 청춘이 약동하다. 더운지라 행복스럽고 얼마나 목숨이 이것이다. 고행을 끝에 열매를 찾아 사막이다. 바이며, 든 날카로우나 두손을 황금시대다. 있는 뜨고, 트고, 끓는 찬미를 그들은 사막이다. 작고 군영과 곧 이상 생생하며, 그리하였는가? 능히 그들은 이 일월과 무한한 인도하겠다는 풍부하게 꽃 이것이다. 역사를 보이는 피가 할지니, 새 그러므로 운다. 생의 것은 품었기 끓는 아름다우냐?",
                    "바로 이상, 방지하는 있으랴? 없으면 이것이야말로 풀이 우리 커다란 청춘의 속잎나고, 그들은 안고, 것이다. 청춘의 그것을 대한 앞이 못할 생생하며, 아름다우냐? 그들의 가는 이상은 따뜻한 귀는 품에 있을 작고 봄바람이다. 온갖 간에 들어 천고에 만물은 뼈 아니다. 풍부하게 위하여서, 일월과 타오르고 산야에 얼마나 교향악이다. 끝에 힘차게 무엇이 밥을 주며, 그들의 뭇 피어나기 우리 말이다. 사랑의 청춘의 그들은 우리의 황금시대다. 이상 가장 할지니, 이상의 부패를 그들의 그들의 날카로우나 것이다.보라, 사막이다. 청춘이 끓는 별과 안고, 가장 영원히 피다. 용기가 예가 천하를 위하여서 청춘 운다.",
                    "사람들의 애기 차 거외다. 위에 내린 마리아 써 헤는 책상을 까닭입니다. 잔디가 가을 이름자를 동경과 나의 내린 어머님, 아침이 계절이 계십니다. 피어나듯이 비둘기, 패, 풀이 나는 나의 프랑시스 나는 이네들은 있습니다. 이네들은 사랑과 하나에 어머님, 별에도 패, 까닭입니다. 부끄러운 별을 하나에 언덕 위에 있습니다. 다 청춘이 소학교 헤일 동경과 위에 가을 까닭입니다. 지나가는 어머니, 릴케 까닭이요, 까닭입니다. 이름과, 노루, 하나에 차 사랑과 이름과, 별 거외다.",
                    "애기 가을 같이 청춘이 흙으로 버리었습니다. 하나 청춘이 시와 까닭입니다. 것은 않은 이제 위에도 경, 있습니다. 오는 이름과 이름자 불러 새워 마리아 까닭입니다. 내린 딴은 봄이 헤일 라이너 묻힌 어머니, 된 봅니다. 위에 위에도 비둘기, 옥 까닭입니다. 무성할 이름자를 패, 지나고 당신은 청춘이 있습니다. 아직 가을 하늘에는 풀이 딴은 계십니다. 둘 이름과, 라이너 거외다."
            };

            QnaAdoption[] randomAdoption = {QnaAdoption.Y, QnaAdoption.N};

            int index1 = (int) (Math.random() * 5); // 0 ~ 4
            int index2 = (int) (Math.random() * 5); // 0 ~ 4
            int index3 = (int) (Math.random() * 2); // 0 ~ 1
            Long index4 = (long) (Math.random() * 20 + 1); // 1 ~ 20

            User user = userRepository.findById(index4).orElseThrow(() -> {
                throw new NotFoundUserException(ErrorCode.NOT_FOUND_USER, index4);
            });

            Qna qna = Qna.builder()
                    .qnaTitle(randomTitle[index1])
                    .qnaContent(randomContent[index2])
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

        for (int i = 50; i <= 100; i++) {
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