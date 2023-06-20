package kr.co.ddamddam.mentor.service;

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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final MenteeRepository menteeRepository;

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
    public MentorDetailResponseDTO getDetail(Long mentorIdx) {

        Optional<Mentor> mentorOptional = mentorRepository.findById(mentorIdx);
        Mentor mentor = mentorOptional.get();
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
    public MentorDetailResponseDTO write(MentorWriteRequestDTO dto, Long userIdx) {
        Mentor mentor = dto.toEntity();
        Optional<User> optionalUser = userRepository.findById(userIdx);
        mentor.setUser(optionalUser.get());
        Mentor saved = mentorRepository.save(mentor);
        return getDetail(saved.getMentorIdx());
    }

    // 게시글 수정
    public MentorDetailResponseDTO modify(MentorModifyRequestDTO dto, Long userIdx) throws RuntimeException{


        Mentor findByMentor = mentorRepository.findByMentorIdxAndUserUserIdx(dto.getMentorIdx(), userIdx);



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

        return getDetail(dto.getMentorIdx());
    }

    // 게시판 삭제

    public MentorListResponseDTO delete(Long mentorIdx, Long userIdx) throws RuntimeException{

        Mentor targetMentor = mentorRepository.findByMentorIdxAndUserUserIdx(mentorIdx,userIdx);
        if (targetMentor != null){
            mentorRepository.delete(targetMentor);
        }
        else {
            throw new RuntimeException(mentorIdx+"해당 게시판은 없습니다");
        }

        return null;
    }

    // 멘티 테이블 저장
    public void menteeSave(Long mentorIdx, Long menteeIdx, Long enterUserIdx) {
        Mentor mentor = mentorRepository.findByMentorIdxAndUserUserIdx(mentorIdx,enterUserIdx);
        Optional<User> optionalUser = userRepository.findById(menteeIdx);
        Mentee mentee = new Mentee();
        mentee.setMentor(mentor);
        mentee.setUser(optionalUser.get());
        menteeRepository.save(mentee);
    }
}
