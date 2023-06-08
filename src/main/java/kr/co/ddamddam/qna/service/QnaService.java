package kr.co.ddamddam.qna.service;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import kr.co.ddamddam.qna.dto.page.PageDTO;
import kr.co.ddamddam.qna.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListResponseDTO;
import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.repository.QnaRepository;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    private final UserRepository userRepository;

//     QNA 게시글 페이징 조회
    public QnaListResponseDTO getList(PageDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("qnaDate").descending()
        );

        // 데이터베이스에서 QNA 게시글 목록 조회
        Page<Qna> qnas = qnaRepository.findAll(pageable);
                
        // DTO 리스트로 꺼내기
        List<QnaDetailResponseDTO> detailList
                = qnas.getContent().stream()
                    .map(QnaDetailResponseDTO::new)
                .collect(Collectors.toList());

        // 데이터베이스에서 조회한 정보를 JSON 형태에 맞는 DTO 로 변환
        QnaListResponseDTO responseDTO = QnaListResponseDTO.builder()
                .count(detailList.size())
                .pageInfo(new PageResponseDTO<Qna>(qnas)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                .qnas(detailList)
                .build();

        return responseDTO;

    }

}
