package kr.co.ddamddam.mypage.service;

import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.chat.repository.ChatRoomRepository;
import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.UserUtil;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.mypage.dto.page.PageDTO;
import kr.co.ddamddam.mypage.dto.page.PageMaker;
import kr.co.ddamddam.mypage.dto.request.MypageModifyRequestDTO;
import kr.co.ddamddam.mypage.dto.response.ChatRoomResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageChatPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageProjectResponseDTO;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfBack;
import kr.co.ddamddam.project.entity.applicant.ApplicantOfFront;
import kr.co.ddamddam.project.repository.BackRepository;
import kr.co.ddamddam.project.repository.FrontRepository;
import kr.co.ddamddam.project.repository.ProjectRepository;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.review.entity.Review;
import kr.co.ddamddam.review.repository.ReviewRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageService {

    // 게시판 타입 상수
    private final String QNA = "Q&A";
    private final String PROJECT = "프로젝트 모집";
    private final String MENTOR = "멘토/멘티";
    private final String REVIEW = "취업후기";
    private final String MENTEE_CHAT_ROOM = "멘티 채팅방";

    // 레포지토리
    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;
    private final MentorRepository mentorRepository;
    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final BackRepository backRepository;
    private final FrontRepository frontRepository;
    private final UserUtil userUtil;
    private final ValidateToken validateToken;



    /**
     * 마이페이지의 <내가 참여한 멘티 채팅방> 채팅방 조회 및 페이징
     *
     * @param tokenUserInfo
     * @param pageDTO       - 클라이언트에서 요청한 페이지 번호
     * @return 페이지 정보, 페이징 처리 된 로그인 유저가 참여한 멘티의 채팅방 리스트
     */
    public MypageChatPageResponseDTO getChatList(
//            TokenUserInfo tokenUserInfo,
            TokenUserInfo tokenUserInfo, PageDTO pageDTO
    ){
         Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());
//
//        Long userIdx = 44L;

        List<ChatRoom> chatRoomList = chatRoomRepository.findBySenderUserIdx(userIdx);

        PageMaker maker = new PageMaker(pageDTO, chatRoomList.size());
        int pageStart = getPageStart(pageDTO);
        int pageEnd = getPageEnd(pageStart, pageDTO.getSize(), chatRoomList.size());

        List<ChatRoom> sliceChatRoomList = chatRoomList.subList(pageStart, pageEnd);

        List<ChatRoomResponseDTO> collect = sliceChatRoomList.stream().map(chatRoom ->
                        convertChatRoomToMypageDto(chatRoom))
                .collect(Collectors.toList());

        return MypageChatPageResponseDTO.builder()
                .chatRoomList(collect)
                .count(sliceChatRoomList.size())
                .pageInfo(maker)
                .build();

    }


    /**
     * 마이페이지의 <내가 쓴 게시글> 목록 조회 및 페이징
     * @param pageDTO - 클라이언트에서 요청한 페이지 번호
     * @return 페이지 정보, 페이징 처리 된 로그인 유저가 작성한 게시글 목록 리스트
     */
    public MypageBoardPageResponseDTO getBoardList(
            TokenUserInfo tokenUserInfo,
            PageDTO pageDTO
    ) {
        // 토큰 유효성 검사
        validateToken.validateToken(tokenUserInfo);

        // 유효성 검사 통과 시 서비스 기능 수행
        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());
//        Long userIdx = 1L;

        List<Qna> qnaList = qnaRepository.findByUserUserIdx(userIdx);
        List<Mentor> mentorList = mentorRepository.findByUserUserIdx(userIdx);
        List<Review> reviewList = reviewRepository.findByUserUserIdx(userIdx);
        List<Project> projectList = projectRepository.findByUserUserIdx(userIdx);

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
     * @param qnaList      - 로그인 유저가 작성한 Qna 게시글 리스트
     * @param mentorList   - 로그인 유저가 작성한 Mentor 게시글 리스트
     * @param reviewList   - 로그인 유저가 작성한 Review 게시글 리스트
     * @param projectList  - 로그인 유저가 작성한 Project 게시글 리스트
     * @return - 최신순으로 정렬된 내가 쓴 게시글 리스트
     */
    private List<MypageBoardResponseDTO> getMypageDtoList(
            List<Qna> qnaList,
            List<Mentor> mentorList,
            List<Review> reviewList,
            List<Project> projectList
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
        // Mentee 채팅방 리스트 매핑
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
                .boardTitle(qna.getQnaTitle())
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
                .boardTitle(mentor.getMentorTitle())
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
                .boardTitle(review.getReviewTitle())
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
                .boardTitle(project.getProjectTitle())
                .boardDate(project.getProjectDate())
                .build();
    }

    /**
     * ChatRoom 를 ChatRoomReponseDTO 로 변환
     * @param chatRoom - ChatRoom 엔터티
     * @return - ChatRoomReponseDTO
     */
    private ChatRoomResponseDTO convertChatRoomToMypageDto(ChatRoom chatRoom) {

        return ChatRoomResponseDTO.builder()
                .chatType(MENTEE_CHAT_ROOM)
                .mentorIdx(chatRoom.getMentor().getMentorIdx())
                .roomIdx(chatRoom.getRoomId())
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

    /**
     * 회원정보 수정하라고 해서 한다
     * @param dto
     * @param
     */
    public void myPageModify(MypageModifyRequestDTO dto, Long userIdx, String uploadedFilePath) {

//        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        User dupUser = userRepository.findByUserNickname(dto.getUserNickname());

        if (dupUser == null){
            User user = userRepository.findById(userIdx)
                    .orElseThrow(() -> new IllegalArgumentException("없는 유저입니다"));
                user.setUserName(dto.getUserName());
                user.setUserNickname(dto.getUserNickname());
                user.setUserBirth(dto.getUserBirth());
                user.setUserCareer(dto.getUserCareer());
                user.setUserPosition(UserPosition.valueOf(dto.getUserPosition()));
                user.setUserProfile(uploadedFilePath);

                userRepository.save(user);
        }

    }

// TODO : 사용안하는지 확인하고 지우기

    /**
     *  프로젝트 목록 조회 조회
     * @param userIdx : 로그인한 회원 idx
     * @return : 내가 만든 프로젝트 게시판 목록
     */
    public List<MypageProjectResponseDTO> getProjectList(Long userIdx) {

        // 내가 작성한 프로젝트 모든 게시글 찾기
        List<Project> myProjects = projectRepository.findByUserUserIdx(userIdx);
        System.out.println("mypage - foundProject = " + myProjects);

        return toDtoList(myProjects);
    }

    /**
     * @return : 내가 신청한 프로젝트 목록 조회
     */
    public List<MypageProjectResponseDTO> getArrayProjectList(Long userIdx) {

        // 로그인한 유저 객체 가져오기
        User user = userUtil.getUser(userIdx);
        UserPosition userPosition = user.getUserPosition();

        List<Project> arrayProjects = new ArrayList<>();
        if (userPosition == UserPosition.FRONTEND) {
            List<Project> frontProjects  = frontRepository.findByUser(user)
                .stream().map(ApplicantOfFront::getProject)
                .collect(Collectors.toList());
            arrayProjects.addAll(frontProjects);
        } else {
            List<Project> backProjects  = backRepository.findByUser(user)
                .stream().map(ApplicantOfBack::getProject)
                .collect(Collectors.toList());
            arrayProjects.addAll(backProjects);
        }

        return toDtoList(arrayProjects);
    }

    private static List<MypageProjectResponseDTO> toDtoList(List<Project> myProjects) {
        List<MypageProjectResponseDTO> dtoList = myProjects.stream()
            .map(project -> {
                // 게시글 작성자의 포지션 가져오기
                UserPosition writerPosition = project.getUser().getUserPosition();
                MypageProjectResponseDTO dto = new MypageProjectResponseDTO(project, writerPosition);
                dto.setFront(project);
                dto.setBack(project);
                return dto;
            })
            .collect(Collectors.toList());
        return dtoList;
    }
}
