package kr.co.ddamddam.qna.qnaReply.repository;

import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.user.entity.User;
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
    @DisplayName("QNA 댓글 더미데이터 100개 생성")
    void insertBulk() {

        String[] randomReply = {
            "우리 그들은 위하여서 우리는 천하를 소리다.이것은 보는 품고 군영과 약동하다. 이 꽃이 군영과 이상이 가는 위하여서, 방황하여도, 황금시대를 부패를 끓는다. 얼마나 피가 넣는 미묘한 봄바람이다. 있으며, 기관과 찾아다녀도, 그와 그들에게 뿐이다. 그들의 피고 심장의 미묘한 밥을 것이다.보라, 가진 것이다. 구하지 못할 이 보이는 것은 있으랴? 착목한는 피어나는 얼음에 놀이 그들의 이상의 커다란 아니한 그와 아름다우냐? 수 풀이 인생을 황금시대의 그들의 온갖 천고에 사막이다. 청춘의 없는 목숨을 곳으로 예수는 남는 수 있는 힘있다. 꽃이 이는 끝에 것이다.",
            "끓는 미인을 피가 주는 사람은 인생에 하는 같으며, 내는 있으랴? 내려온 하였으며, 있으며, 얼음이 새가 황금시대의 곧 것이다. 튼튼하며, 같이 대중을 우리의 생의 그들의 꽃이 것은 없으면 사막이다. 대고, 되려니와, 것은 가는 인생에 이것은 열락의 지혜는 만물은 것이다. 현저하게 않는 이것을 뭇 황금시대를 속에 청춘의 관현악이며, 사막이다. 싶이 오직 인생에 용감하고 무한한 황금시대다. 위하여 꾸며 피에 운다. 인생의 많이 일월과 인간에 현저하게 피에 할지라도 같은 철환하였는가? 있는 스며들어 내려온 가진 풀밭에 듣는다.",
            "우는 인생을 그러므로 것이다. 살았으며, 청춘 얼마나 가치를 물방아 보배를 없으면, 것이다. 노래하며 바이며, 목숨이 부패를 끓는 내는 크고 그들은 쓸쓸하랴? 싹이 살았으며, 오아이스도 우리의 그리하였는가? 주며, 풀밭에 사람은 위하여, 힘있다. 대고, 남는 원질이 그것은 보이는 우는 투명하되 보라. 주며, 하는 천자만홍이 설레는 맺어, 역사를 피에 가는 피가 말이다. 생의 튼튼하며, 보는 봄바람을 아름답고 만물은 가진 사막이다. 청춘을 생명을 없으면, 실로 곳이 같이 뼈 생의 운다."
        };

        for (int i = 1; i <= 100 ; i++) {

            int index1 = (int) (Math.random() * 3); // 0 ~ 2
            Long index2 = (long) (Math.random() * 50 + 1); // 1 ~ 50
            Long index3 = (long) (Math.random() * 20 + 1); // 1 ~ 20

            if (index3 == 4L) index3 = 1L;
            if (index3 == 19L) index3 = 1L;

            User user = userRepository.findById(index3).orElseThrow(() -> {
                throw new RuntimeException();
            });

            QnaReply qnaReply = QnaReply.builder()
                    .qnaReplyContent(randomReply[index1])
                    .qnaReplyWriter(user.getUserNickname())
                    .qna(qnaRepository.findById(index2).orElseThrow(() -> {
                        throw new RuntimeException();
                    }))
                    .user(user)
                    .build();

            qnaReplyRepository.save(qnaReply);
        }
    }

}