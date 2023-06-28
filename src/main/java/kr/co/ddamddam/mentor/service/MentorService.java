package kr.co.ddamddam.mentor.service;

import kr.co.ddamddam.chat.entity.ChatRoom;
import kr.co.ddamddam.chat.repository.ChatRoomRepository;
import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mentor.dto.page.MentorPageDTO;
import kr.co.ddamddam.mentor.dto.page.MentorPageResponseDTO;
import kr.co.ddamddam.mentor.dto.request.MentorModifyRequestDTO;
import kr.co.ddamddam.mentor.dto.request.MentorWriteRequestDTO;
import kr.co.ddamddam.mentor.dto.response.MenteeListResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MenteeResponseDTO;
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

    public MentorListResponseDTO getList(MentorPageDTO mentorPageDTO) {
        // Pageable 객체생성
        Pageable pageable = PageRequest.of(
                mentorPageDTO.getPage() - 1,
                mentorPageDTO.getSize()
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
                .pageInfo(new MentorPageResponseDTO<Mentor>(mentors))
                .mentors(mentorDetailResponseDTOList)
                .build();
    }

    // 주제 검색
    public MentorListResponseDTO getSubList(MentorPageDTO mentorPageDTO, List<String> subjects) {
        // Pageable 객체생성
        Pageable pageable = PageRequest.of(
                mentorPageDTO.getPage() - 1,
                mentorPageDTO.getSize(),
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
                .pageInfo(new MentorPageResponseDTO<Mentor>(mentors))
                .mentors(mentorDetailResponseDTOList)
                .build();
    }

    // 멘토 게시판 상세 조회
    public MentorDetailResponseDTO getDetail(Long mentorIdx, TokenUserInfo tokenUserInfo) {

        validateToken.validateToken(tokenUserInfo);

        Mentor mentor  = mentorRepository.findById(mentorIdx).orElseThrow(
                () -> {throw new NotFoundBoardException(NOT_FOUND_BOARD, mentorIdx);});

        List<Mentee> menteeList = menteeRepository.findByMentorMentorIdx(mentorIdx);

        log.info("Mentor : {}", mentor);

        List<MenteeResponseDTO> menteeResponseDTOList = menteeList.stream().map(
                        mentee -> {
                            MenteeResponseDTO dto = new MenteeResponseDTO();
                            dto.setMenteeIdx(mentee.getMenteeIdx());
                            dto.setRoomId(mentee.getChatRoom().getRoomId());

                            return dto;
                        })
                .collect(toList());


        MentorDetailResponseDTO dto = new MentorDetailResponseDTO();
        dto.setIdx(mentor.getMentorIdx());
        dto.setTitle(mentor.getMentorTitle());
        dto.setContent(mentor.getMentorContent());
        dto.setSubject(mentor.getMentorSubject());
        dto.setCurrent(mentor.getMentorCurrent());
        dto.setDate(mentor.getMentorDate());
        dto.setMentee(mentor.getMentorMentee());
        dto.setMenteeList(menteeResponseDTOList);
        dto.setCompleteMentee(menteeList.size());
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
        if (user.getUserCareer() == 0){
            mentor.setMentorCareer("신입");
        }else {
            mentor.setMentorCareer(String.valueOf(user.getUserCareer()));
        }
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
    public int menteeSave(Long mentorIdx, Long roomId , TokenUserInfo tokenUserInfo){

        validateToken.validateToken(tokenUserInfo);

        Long enterUserIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        // 멘티 몇 명 확정했는지
        List<Mentee> findByMenteeList = menteeRepository.findByMentorMentorIdx(mentorIdx);

        // 해당 멘티와 같이 있는 채팅방
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> {
                    throw new NotFoundBoardException(NOT_FOUND_BOARD, roomId);
                }
        );

        // 멘티 유저
        User user = userRepository.findById(chatRoom.getSender().getUserIdx()).orElseThrow(
                () -> {throw  new NotFoundUserException(NOT_FOUND_USER, enterUserIdx);}
        );

        // 멘티 제한 인원이 다 찼는지 확인하기
        Mentor mentor = mentorRepository.findByMentorIdxAndUserUserIdx(mentorIdx,enterUserIdx)
                .orElseThrow( () -> { throw  new NotFoundBoardException(NOT_FOUND_BOARD,mentorIdx);}
                );

        // 이미 멘티로 확정된 클라이언트인지 체크하기
        for (Mentee mentee : findByMenteeList) {
            if (mentee.getUser().getUserIdx() == user.getUserIdx()) {
                return findByMenteeList.size();
            }
        }


        if (findByMenteeList.size() == mentor.getMentorMentee()){
            return mentor.getMentorMentee();
        }
        // 제한인원이 다 안 찼으면 멘티 확정하기
        else if (findByMenteeList.size() < mentor.getMentorMentee()){

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

    public MenteeListResponseDTO getMenteeList(Long mentorIdx, TokenUserInfo tokenUserInfo) {

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

//        Long userIdx = 6L;


        List<Mentee> menteeList = menteeRepository.findByMentorMentorIdx(mentorIdx);

        List<MenteeResponseDTO> menteeResponseDTOList = menteeList.stream().map(
                mentee -> {
                    MenteeResponseDTO dto = new MenteeResponseDTO();
                    dto.setRoomId(mentee.getChatRoom().getRoomId());
                    dto.setMenteeIdx(mentee.getMenteeIdx());
                    dto.setMenteeNickname(mentee.getUser().getUserNickname());
                    dto.setMentorIdx(mentee.getMentor().getMentorIdx());
                    dto.setMenteeUserIdx(mentee.getUser().getUserIdx());

                    return dto;
                }
        ).collect(toList());

        Mentor mentor = mentorRepository.findById(mentorIdx).orElseThrow(
                () -> {
                    throw new NotFoundBoardException(NOT_FOUND_BOARD, mentorIdx);
                }
        );


        return MenteeListResponseDTO.builder()
                .mentorNickname(mentor.getUser().getUserNickname())
                .menteeResponseDTOList(menteeResponseDTOList)
                .build();
    }
}
