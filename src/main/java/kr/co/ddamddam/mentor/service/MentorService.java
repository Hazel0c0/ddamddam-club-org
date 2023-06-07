package kr.co.ddamddam.mentor.service;

import kr.co.ddamddam.mentor.dto.page.PageDTO;
import kr.co.ddamddam.mentor.dto.response.MentorDetailResponseDTO;
import kr.co.ddamddam.mentor.dto.response.MentorListResponseDTO;
import kr.co.ddamddam.mentor.entity.Mentor;
import kr.co.ddamddam.mentor.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MentorService {

    private final MentorRepository mentorRepository;

    public MentorListResponseDTO getMentors(PageDTO pageDTO) {

        // Pageable 객체생성
        Pageable pageable = PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by(pageDTO.getSort()).descending()
        );

        // 게시글 목록 조회
        Page<Mentor> mentors = mentorRepository.findAll(pageable);

        List<Mentor> mentorList = mentors.getContent();
        List<MentorDetailResponseDTO> mentorDetailResponseDTOList = mentorList.stream()
                .map(MentorDetailResponseDTO::new).collect(Collectors.toList());

        return null;
    }
}
