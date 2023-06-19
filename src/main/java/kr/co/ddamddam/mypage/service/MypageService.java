package kr.co.ddamddam.mypage.service;

import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.mypage.dto.page.PageDTO;
import kr.co.ddamddam.mypage.dto.page.PageMaker;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.review.entity.Review;
import kr.co.ddamddam.review.repository.ReviewRepository;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final QnaRepository qnaRepository;
    private final MentorRepository mentorRepository;
    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;

    /**
     * 마이페이지의 <내가 쓴 게시글> 목록 조회 및 페이징
     * @param pageDTO - 클라이언트에서 요청한 페이지 번호
     * @return 페이지 정보, 페이징 처리 된 로그인 유저가 작성한 게시글 목록 리스트
     */
    public MypageBoardPageResponseDTO getBoardList(
//            TokenUserInfo tokenUserInfo,
            PageDTO pageDTO
    ) {
        // 토큰 유효성 검사
//        validateToken.validateToken(tokenUserInfo);

        // 유효성 검사 통과 시 서비스 기능 수행
//        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());
        Long userIdx = 1L;

        List<Qna> qnaList = qnaRepository.findByUserUserIdx(userIdx);
        List<Mentor> mentorList = mentorRepository.findByUserUserIdx(userIdx);
        List<Review> reviewList = reviewRepository.findByUserUserIdx(userIdx);
        // TODO : User 양방향 매핑으로 바꾸고 findByUserUserIdx 로 변경 필요
        List<Project> projectList = projectRepository.findByUserIdx(userIdx);

        List<MypageBoardResponseDTO> mypageBoardList
                = getMypageDtoList(qnaList, mentorList, reviewList, projectList);

        PageMaker maker = new PageMaker(pageDTO, mypageBoardList.size());
        int pageStart = getPageStart(pageDTO);
        int pageEnd = getPageEnd(pageStart, pageDTO.getSize(), mypageBoardList.size());

        List<MypageBoardResponseDTO> slicedMypageBoardList = mypageBoardList.subList(pageStart, pageEnd);

        return MypageBoardPageResponseDTO.builder()
                .count(slicedMypageBoardList.size())
                .pageInfo(maker)
                .boardList(slicedMypageBoardList)
                .build();
    }



    /**
     * 로그인한 유저가 작성한 Qna, Mentor, Project, Review 게시글들을
     * 리스트에 담아서 최신순으로 정렬해주는 기능
     * @param qnaList - 로그인 유저가 작성한 Qna 게시글 리스트
     * @param mentorList - 로그인 유저가 작성한 Mentor 게시글 리스트
     * @param reviewList - 로그인 유저가 작성한 Review 게시글 리스트
     * @param projectList - 로그인 유저가 작성한 Project 게시글 리스트
     * @return - 최신순으로 정렬된 내가 쓴 게시글 리스트
     */
    private List<MypageBoardResponseDTO> getMypageDtoList(
            List<Qna> qnaList,
            List<Mentor> mentorList,
            List<Review> reviewList, List<Project> projectList) {
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

        // Review 게시글 리스트 매핑
        mypageBoardList.addAll(
                reviewList.stream()
                        .map(review -> convertReviewToMypageDto(review))
                        .collect(Collectors.toList())
        );

        // Project 게시글 리스트 매핑
        mypageBoardList.addAll(
                projectList.stream()
                        .map(project -> convertProjectToMypageDto(project))
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

    /**
     * Review 를 MypageBoardResponseDTO 로 변환
     * @param review - Review 엔터티
     * @return - MypageBoardResponseDTO
     */
    private MypageBoardResponseDTO convertReviewToMypageDto(Review review) {
        return MypageBoardResponseDTO.builder()
                .boardType(REVIEW)
                .boardIdx(review.getReviewIdx())
                .boardDate(review.getReviewDate())
                .build();
    }

    /**
     * Project 를 MypageBoardResponseDTO 로 변환
     * @param project - Review 엔터티
     * @return - MypageBoardResponseDTO
     */
    private MypageBoardResponseDTO convertProjectToMypageDto(Project project) {
        return MypageBoardResponseDTO.builder()
                .boardType(PROJECT)
                .boardIdx(project.getProjectIdx())
                .boardDate(project.getProjectDate())
                .build();
    }

    /**
     * 페이징 - 클라이언트의 페이지번호 요청에 따른 시작페이지를 계산합니다.
     */
    private int getPageStart(PageDTO pageDTO) {
        return (pageDTO.getPage() - 1) * pageDTO.getSize();
    }

    /**
     * 페이징 - 클라이언트 페이지번호 요청에 따른 마지막 페이지를 계산합니다.
     */
    private int getPageEnd(int pageStart, int pageSize, int totalCount) {
        return Math.min(pageStart + pageSize, totalCount);
    }
}
