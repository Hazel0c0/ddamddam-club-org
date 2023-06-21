package kr.co.ddamddam.mentor.service;

import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.chat.repository.ChatRoomRepository;
import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mentor.dto.page.PageDTO;
import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import kr.co.ddamddam.mentor.dto.request.MentorModifyRequestDTO;
import kr.co.ddamddam.mentor.dto.request.MentorWriteRequestDTO;
import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MentorListResponseDTO;
import kr.co.ddamddam.mentor.entity.Mentee;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MenteeRepository;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;
import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final MenteeRepository menteeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ValidateToken validateToken;

    public MentorListResponseDTO getList(PageDTO pageDTO) {
        // Pageable 객체생성
        Pageable pageable = PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize()
        );

        // 게시글 목록 조회
        Page<Mentor> mentors;
        mentors = mentorRepository.findAll(pageable);


        List<Mentor> mentorList = mentors.getContent();
        List<MentorDetailResponseDTO> mentorDetailResponseDTOList = mentorList.stream().map(mentor -> {
            MentorDetailResponseDTO dto = new MentorDetailResponseDTO();
            dto.setIdx(mentor.getMentorIdx());
            dto.setTitle(mentor.getMentorTitle());
            dto.setContent(mentor.getMentorContent());
            dto.setSubject(mentor.getMentorSubject());
            dto.setCurrent(mentor.getMentorCurrent());
            dto.setDate(mentor.getMentorDate());
            dto.setMentee(mentor.getMentorMentee());
            dto.setCareer(mentor.getMentorCareer());

            User user = mentor.getUser();
            if (user != null){
                dto.setProfile(user.getUserProfile());
                dto.setNickName(user.getUserNickname());
            }
            return dto;
        }).collect(toList());

        return MentorListResponseDTO.builder()
                .count(mentorDetailResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Mentor>(mentors))
                .mentors(mentorDetailResponseDTOList)
                .build();
    }

    // 주제 검색
    public MentorListResponseDTO getSubList(PageDTO pageDTO, List<String> subjects) {
        // Pageable 객체생성
        Pageable pageable = PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by(Sort.Direction.DESC, "mentorDate")
        );

        // 게시글 목록 조회
        Page<Mentor> mentors;
        if (subjects.isEmpty()){
            mentors = mentorRepository.findAll(pageable);
        }else {
            mentors = mentorRepository.findByMentorSubjectInIgnoreCase(subjects, pageable);
        }

        List<Mentor> mentorList = mentors.getContent();
        List<MentorDetailResponseDTO> mentorDetailResponseDTOList = mentorList.stream().map(mentor -> {
            MentorDetailResponseDTO dto = new MentorDetailResponseDTO();
            dto.setIdx(mentor.getMentorIdx());
            dto.setTitle(mentor.getMentorTitle());
            dto.setContent(mentor.getMentorContent());
            dto.setSubject(mentor.getMentorSubject());
            dto.setCurrent(mentor.getMentorCurrent());
            dto.setDate(mentor.getMentorDate());
            dto.setMentee(mentor.getMentorMentee());
            dto.setCareer(mentor.getMentorCareer());

            User user = mentor.getUser();
            if (user != null){
                dto.setProfile(user.getUserProfile());
                dto.setNickName(user.getUserNickname());
            }
            return dto;
        }).collect(toList());

        return MentorListResponseDTO.builder()
                .count(mentorDetailResponseDTOList.size())
                .pageInfo(new PageResponseDTO<Mentor>(mentors))
                .mentors(mentorDetailResponseDTOList)
                .build();
    }

    // 멘토 게시판 상세 조회
    public MentorDetailResponseDTO getDetail(Long mentorIdx, TokenUserInfo tokenUserInfo) {

        validateToken.validateToken(tokenUserInfo);

        if (mentorIdx == null) {
            throw new NotFoundBoardException(INVALID_PARAMETER, mentorIdx);
        }

        Mentor mentor  = mentorRepository.findById(mentorIdx).orElseThrow(
                () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, mentorIdx);});

        MentorDetailResponseDTO dto = new MentorDetailResponseDTO();
        dto.setIdx(mentor.getMentorIdx());
        dto.setTitle(mentor.getMentorTitle());
        dto.setContent(mentor.getMentorContent());
        dto.setSubject(mentor.getMentorSubject());
        dto.setCurrent(mentor.getMentorCurrent());
        dto.setDate(mentor.getMentorDate());
        dto.setMentee(mentor.getMentorMentee());
        dto.setCareer(mentor.getMentorCareer());

        User user = mentor.getUser();
        if (user != null){
            dto.setProfile(user.getUserProfile());
            dto.setNickName(user.getUserNickname());
            dto.setUserIdx(user.getUserIdx());
        }
        return dto;
    }

    // 게시글 작성
    // user_idx값을 받아와 save하기전에 mentor테이블 user_idx값에 넣기[User]
    public MentorDetailResponseDTO write(MentorWriteRequestDTO dto, TokenUserInfo tokenUserInfo) {

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        Mentor mentor = dto.toEntity();
        User user = userRepository.findById(userIdx).orElseThrow(
                () -> {throw new NotFoundUserException(NOT_FOUND_USER, userIdx);
                }
        );
        mentor.setUser(user);
        Mentor saved = mentorRepository.save(mentor);
        return getDetail(saved.getMentorIdx(),tokenUserInfo);
    }

    // 게시글 수정
    public MentorDetailResponseDTO modify(MentorModifyRequestDTO dto, TokenUserInfo tokenUserInfo)
            throws RuntimeException{

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        Mentor findByMentor = mentorRepository.findByMentorIdxAndUserUserIdx(dto.getMentorIdx(), userIdx)
                .orElseThrow( () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, dto.getMentorIdx());}
                );



        if (findByMentor != null){
            findByMentor.setMentorTitle(dto.getMentorTitle());
            findByMentor.setMentorContent(dto.getMentorContent());
            findByMentor.setMentorSubject(dto.getMentorSubject());
            findByMentor.setMentorCurrent(dto.getMentorCurrent());
            findByMentor.setMentorMentee(dto.getMentorMentee());

            mentorRepository.save(findByMentor);
        }else {
            throw new RuntimeException("해당 게시판은 없습니다");
        }

        return getDetail(dto.getMentorIdx(),tokenUserInfo);
    }

    // 게시판 삭제

    public MentorListResponseDTO delete(Long mentorIdx, Long userIdx) throws RuntimeException{

        Mentor targetMentor = mentorRepository.findByMentorIdxAndUserUserIdx(mentorIdx,userIdx)
                .orElseThrow( () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, mentorIdx);}
        );
        if (targetMentor != null){
            mentorRepository.delete(targetMentor);
        }
        else {
            throw new RuntimeException(mentorIdx+"해당 게시판은 없습니다");
        }

        return null;
    }

    // 멘티 테이블 저장
    public int menteeSave(Long mentorIdx, TokenUserInfo tokenUserInfo) {

        validateToken.validateToken(tokenUserInfo);

        Long enterUserIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        // 멘티 몇 명 확정했는지
        List<Mentee> findByMenteeList = menteeRepository.findByMentorMentorIdx(mentorIdx);

        // 멘티 제한 인원이 다 찼는지 확인하기
        Mentor mentor = mentorRepository.findByMentorIdxAndUserUserIdx(mentorIdx,enterUserIdx)
                .orElseThrow( () -> { throw  new NotFoundBoardException(NOT_FOUND_BOARD,mentorIdx);}
                );
        if (findByMenteeList.size() == mentor.getMentorMentee()){
            return mentor.getMentorMentee();
        }
        // 제한인원이 다 안 찼으면 멘티 확정하기
        else if (findByMenteeList.size() < mentor.getMentorMentee()){
            User user = userRepository.findById(enterUserIdx).orElseThrow(
                    () -> {throw  new NotFoundUserException(NOT_FOUND_USER, enterUserIdx);}
            );
            ChatRoom chatRoom = chatRoomRepository.findByMentorMentorIdxAndSenderUserIdx(mentorIdx, enterUserIdx);
            Mentee mentee = new Mentee();
            mentee.setMentor(mentor);
            mentee.setUser(user);
            mentee.setChatRoom(chatRoom);
            menteeRepository.save(mentee);

            // 멘티 확정인원 리턴
            return menteeRepository.findByMentorMentorIdx(mentorIdx).size();
        }

        return mentor.getMentorMentee();
    }
}
