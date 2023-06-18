package kr.co.ddamddam.mypage.service;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.mypage.dto.page.PageDTO;
import kr.co.ddamddam.mypage.dto.page.PageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardResponseDTO;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.review.repository.ReviewRepository;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    // 게시판 타입 상수
    private final String QNA = "Q&A";
    private final String PROJECT = "프로젝트";
    private final String MENTOR = "멘토/멘티";
    private final String REVIEW = "취업후기";

    // 레포지토리
    private final UserRepository userRepository;
    @Autowired
    private final QnaRepository qnaRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final MentorRepository mentorRepository;

    // 토큰 유효성 검사 클래스
    @Autowired
    private final ValidateToken validateToken;

    public MypageBoardPageResponseDTO getBoardList(
            TokenUserInfo tokenUserInfo,
            PageDTO pageDTO
    ) {
        // 토큰 유효성 검사
        validateToken.validateToken(tokenUserInfo);

        // 유효성 검사 통과 시 서비스 기능 수행
        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        PageRequest pageable = getPageable(pageDTO);

        Page<Qna> qnaList = qnaRepository.findByUserIdx(userIdx, pageable);
        Page<Mentor> mentorList = mentorRepository.findByUserIdx(userIdx, pageable);
        
        List<MypageBoardResponseDTO> mypageBoardList 
                = getMypageDtoList(qnaList, mentorList);

        PageResponseDTO<MypageBoardResponseDTO> pageInfo
                = new PageResponseDTO<>(
                        mypageBoardList,
                        qnaList.getPageable(),
                    qnaList.getTotalElements() + mentorList.getTotalElements()
        );

        return MypageBoardPageResponseDTO.builder()
                .count(mypageBoardList.size())
                .pageInfo(pageInfo)
                .boardList(mypageBoardList)
                .build();
    }

    private List<MypageBoardResponseDTO> getMypageDtoList(
            Page<Qna> qnaList,
            Page<Mentor> mentorList
    ) {
        List<MypageBoardResponseDTO> mypageBoardList = new ArrayList<>();

        // Qna 게시글 리스트 매핑
        mypageBoardList.addAll(
                qnaList.stream()
                        .map(qna -> convertQnaToMypageDto(qna))
                        .collect(Collectors.toList())
        );

        // Mentor 게시글 리스트 매핑
        mypageBoardList.addAll(
                mentorList.stream()
                        .map(mentor -> convertMentorToMypageDto(mentor))
                        .collect(Collectors.toList())
        );

        // 작성시간 기준 최신순으로 정렬
        mypageBoardList.sort(
                Comparator.comparing(MypageBoardResponseDTO::getBoardDate).reversed()
        );

        return mypageBoardList;
    }

    /**
     * Qna 를 MypageBoardResponseDTO 로 변환
     * @param qna - Qna 엔터티
     * @return - MypageBoardResponseDTO
     */
    private MypageBoardResponseDTO convertQnaToMypageDto(Qna qna) {
        return MypageBoardResponseDTO.builder()
                .boardType(QNA)
                .boardIdx(qna.getQnaIdx())
                .boardDate(qna.getQnaDate())
                .build();
    }

    /**
     * Mentor 를 MypageBoardResponseDTO 로 변환
     * @param mentor - Mentor 엔터티
     * @return - MypageBoardResponseDTO
     */
    private MypageBoardResponseDTO convertMentorToMypageDto(Mentor mentor) {
        return MypageBoardResponseDTO.builder()
                .boardType(MENTOR)
                .boardIdx(mentor.getMentorIdx())
                .boardDate(mentor.getMentorDate())
                .build();
    }


    private PageRequest getPageable(PageDTO pageDTO) {

        return PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize()
        );
    }
}
