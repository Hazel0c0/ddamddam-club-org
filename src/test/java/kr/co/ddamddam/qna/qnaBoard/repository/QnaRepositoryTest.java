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
                    "모든 국민은 법 앞에 평등하다. 누구든지 성별·종교 또는 사회적 신분에 의하여 정치적·경제적·사회적·문화적 생활의 모든 영역에 있어서 차별을 받지 아니한다."
            };

            String[] randomContent = {
                    "대법원장과 대법관이 아닌 법관의 임기는 10년으로 하며, 법률이 정하는 바에 의하여 연임할 수 있다. 모든 국민은 신체의 자유를 가진다. 누구든지 법률에 의하지 아니하고는 체포·구속·압수·수색 또는 심문을 받지 아니하며, 법률과 적법한 절차에 의하지 아니하고는 처벌·보안처분 또는 강제노역을 받지 아니한다.\n" +
                            "\n" +
                            "모든 국민은 고문을 받지 아니하며, 형사상 자기에게 불리한 진술을 강요당하지 아니한다. 대통령은 제1항과 제2항의 처분 또는 명령을 한 때에는 지체없이 국회에 보고하여 그 승인을 얻어야 한다.\n" +
                            "\n" +
                            "대통령의 국법상 행위는 문서로써 하며, 이 문서에는 국무총리와 관계 국무위원이 부서한다. 군사에 관한 것도 또한 같다. 공무원인 근로자는 법률이 정하는 자에 한하여 단결권·단체교섭권 및 단체행동권을 가진다.\n" +
                            "\n" +
                            "의무교육은 무상으로 한다. 국회가 재적의원 과반수의 찬성으로 계엄의 해제를 요구한 때에는 대통령은 이를 해제하여야 한다. 국무총리 또는 행정각부의 장은 소관사무에 관하여 법률이나 대통령령의 위임 또는 직권으로 총리령 또는 부령을 발할 수 있다.\n" +
                            "\n" +
                            "국군은 국가의 안전보장과 국토방위의 신성한 의무를 수행함을 사명으로 하며, 그 정치적 중립성은 준수된다. 대통령의 임기연장 또는 중임변경을 위한 헌법개정은 그 헌법개정 제안 당시의 대통령에 대하여는 효력이 없다."
            };

            QnaAdoption[] randomAdoption = {QnaAdoption.Y, QnaAdoption.N};

            int index1 = (int) (Math.random() * 5); // 0 ~ 4
            int index2 = (int) (Math.random() * 5); // 0 ~ 4
            int index3 = (int) (Math.random() * 2); // 0 ~ 1
            Long index4 = (long) (Math.random() * 20 + 1); // 1 ~ 20

            if (index4 == 4L) index4 = 1L;
            if (index4 == 19L) index4 = 1L;

            Long finalIndex = index4;
            User user = userRepository.findById(2L).orElseThrow(() -> {
                throw new NotFoundUserException(ErrorCode.NOT_FOUND_USER, finalIndex);
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

        for (int i = 1; i <= 50; i++) {
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