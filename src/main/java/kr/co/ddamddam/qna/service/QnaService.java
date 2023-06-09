package kr.co.ddamddam.qna.service;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import kr.co.ddamddam.qna.dto.page.PageDTO;
import kr.co.ddamddam.qna.dto.request.QnaInsertRequestDTO;
import kr.co.ddamddam.qna.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListPageResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaResponseDTO;
import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.exception.custom.CustomException;
import kr.co.ddamddam.qna.exception.custom.QnaErrorCode;
import kr.co.ddamddam.qna.exception.custom.QnaException;
import kr.co.ddamddam.qna.repository.QnaReplyRepository;
import kr.co.ddamddam.qna.repository.QnaRepository;
import kr.co.ddamddam.user.entity.User;
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

import static kr.co.ddamddam.qna.exception.custom.QnaErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;
    private final QnaReplyRepository qnaReplyRepository;

    public QnaListPageResponseDTO getList(PageDTO dto) {

        if (dto == null) {
            throw new RuntimeException();
        }

        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("qnaDate").descending()
        );

        // 데이터베이스에서 QNA 게시글 목록 조회
        Page<Qna> qnas = qnaRepository.findAll(pageable);

        // DTO 리스트로 꺼내기
        List<QnaListResponseDTO> detailList
                = qnas.getContent().stream()
                    .map(QnaListResponseDTO::new)
                .collect(Collectors.toList());

        // 데이터베이스에서 조회한 정보를 JSON 형태에 맞는 DTO 로 변환
        QnaListPageResponseDTO responseDTO = QnaListPageResponseDTO.builder()
                .count(detailList.size())
                .pageInfo(new PageResponseDTO<Qna>(qnas)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                .qnas(detailList)
                .build();

        return responseDTO;

    }

    public QnaDetailResponseDTO getDetail(Long boardId) {

        log.info("[Qna/Service] QNA 게시글 상세보기 boardId - {}", boardId);

        if (boardId == null) {
            throw new QnaException(QnaErrorCode.INVALID_PARAMETER, boardId);
        }

        Qna qna = qnaRepository.findById(boardId).orElseThrow(() -> {
            throw new QnaException(NOT_FOUND_BOARD, boardId);
        });
        log.info("[Qna/Service] QNA 게시글 상세보기 qna - {}", qna);
        User user = userRepository.findById(qna.getUser().getUserIdx()).orElseThrow(() -> {
            throw new QnaException(NOT_FOUND_USER, qna.getUser().getUserIdx());
        });
        log.info("[Qna/Service] QNA 게시글 상세보기 user - {}", user);

        QnaDetailResponseDTO dto = new QnaDetailResponseDTO(qna, user);

        log.info("[Qna/Service] QNA 게시글 상세보기 - {}", dto);

        return dto;
    }

    // TODO : DB 에 저장은 잘 되는데 responseDTO 의 필드값들이 null 로 뜬다...............
    public QnaDetailResponseDTO writeBoard(Long userIdx, QnaInsertRequestDTO dto) {

        log.info("[Qna/Service] QNA 게시글 작성 - {}", dto);

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new QnaException(NOT_FOUND_USER, userIdx);
        });

        Qna saved = qnaRepository.save(dto.toEntity(user));

        QnaDetailResponseDTO responseDTO = getDetail(saved.getQnaIdx());

        if (responseDTO == null) {
            throw new QnaException(FAIL_WRITE_BOARD, saved.getQnaIdx());
        }

        return responseDTO;
    }
}
