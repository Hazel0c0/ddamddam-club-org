package kr.co.ddamddam.qna.service;

import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import kr.co.ddamddam.qna.dto.page.PageDTO;
import kr.co.ddamddam.qna.dto.request.QnaInsertRequestDTO;
import kr.co.ddamddam.qna.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaListPageResponseDTO;
import kr.co.ddamddam.qna.dto.response.QnaTopListResponseDTO;
import kr.co.ddamddam.qna.entity.Qna;
import kr.co.ddamddam.qna.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.repository.QnaReplyRepository;
import kr.co.ddamddam.qna.repository.QnaRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kr.co.ddamddam.common.response.ResponseMessage.*;
import static kr.co.ddamddam.qna.entity.QnaAdoption.*;
import static kr.co.ddamddam.qna.exception.custom.QnaErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaService {

    private final int VIEW_COUNT_UP = 1;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;
    private final QnaReplyRepository qnaReplyRepository;

    public QnaListPageResponseDTO getList(PageDTO pageDTO) {

        PageRequest pageable = getPageable(pageDTO);

        // 데이터베이스에서 QNA 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        List<QnaListResponseDTO> qnaList = getQnaDtoList(qnas);

        // 데이터베이스에서 조회한 정보를 JSON 형태에 맞는 DTO 로 변환
        return QnaListPageResponseDTO.builder()
                .count(qnaList.size())
                .pageInfo(new PageResponseDTO<Qna>(qnas)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                .qnas(qnaList)
                .build();
    }

    public List<QnaTopListResponseDTO> getListTop3ByView() {

        log.info("[Qna/Service] QNA 게시글 조회순 TOP3 정렬");

        List<Qna> qnaListTop3 = qnaRepository.findTop3ByOrderByQnaViewDesc();

        return qnaListTop3.stream()
                .map(qna -> QnaTopListResponseDTO.builder()
                        .boardIdx(qna.getQnaIdx())
                        .boardTitle(qna.getQnaTitle())
                        .boardWriter(qna.getQnaWriter())
                        .boardView(qna.getQnaView())
                        // TODO : 댓글과 연관관계 매핑할 때 에러나네요... 댓글 구현 후 다시 테스트 필요합니다.
//                        .replyCount(qna.getQnaReply().size())
                        .build()
                ).collect(Collectors.toList());
    }

    public QnaDetailResponseDTO getDetail(Long boardId) {

        log.info("[Qna/Service] QNA 게시글 상세보기 boardId - {}", boardId);

        if (boardId == null) {
            throw new NotFoundQnaBoardException(INVALID_PARAMETER, boardId);
        }

        Qna qna = qnaRepository.findById(boardId).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardId);
        });
        User user = userRepository.findById(qna.getUser().getUserIdx()).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_USER, qna.getUser().getUserIdx());
        });

        return new QnaDetailResponseDTO(qna, user);
    }

    public QnaDetailResponseDTO writeBoard(Long userIdx, QnaInsertRequestDTO dto) {

        log.info("[Qna/Service] QNA 게시글 작성 - {}", dto);

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_USER, userIdx);
        });

        Qna saved = qnaRepository.save(dto.toEntity(user));

        QnaDetailResponseDTO qnaDetail = getDetail(saved.getQnaIdx());

        if (qnaDetail == null) {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, saved.getQnaIdx());
        }

        return qnaDetail;
    }

    public ResponseMessage deleteBoard(Long boardIdx) {

        log.info("[Qna/Service] QNA 게시글 삭제 - {}", boardIdx);

        qnaRepository.deleteById(boardIdx);

        return SUCCESS;
    }

    public ResponseMessage updateViewCount(Long boardIdx) {

        log.info("[Qna/Service] QNA 게시글 조회수 상승 - {}", boardIdx);

        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        qna.setQnaView(qna.getQnaView() + VIEW_COUNT_UP);

        qnaRepository.save(qna);

        return SUCCESS;
    }

    public ResponseMessage adoptQnaBoard(Long boardIdx) {

        log.info("[Qna/Service] QNA 게시글 채택 완료 상태로 변경 - {}", boardIdx);

        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        qna.setQnaAdoption(Y);

        qnaRepository.save(qna);

        return SUCCESS;

    }

    public QnaListPageResponseDTO getListAdoption(PageDTO pageDTO) {

        log.info("[Qna/Service] QNA 채택완료 상태인 게시글들만 조회");

        PageRequest pageable = getPageable(pageDTO);
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        List<QnaListResponseDTO> qnaList = getQnaDtoListByAdoption(qnas);

        return QnaListPageResponseDTO.builder()
                .count(qnaList.size())
                .pageInfo(new PageResponseDTO<Qna>(qnas)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                .qnas(qnaList)
                .build();
    }

    public QnaListPageResponseDTO getListNonAdoption(PageDTO pageDTO) {

        log.info("[Qna/Service] QNA 미채택 상태인 게시글들만 조회");

        PageRequest pageable = getPageable(pageDTO);
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        List<QnaListResponseDTO> qnaList = getQnaDtoListByNonAdoption(qnas);

        return QnaListPageResponseDTO.builder()
                .count(qnaList.size())
                .pageInfo(new PageResponseDTO<Qna>(qnas)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                .qnas(qnaList)
                .build();
    }

    private PageRequest getPageable(PageDTO pageDTO) {

        return PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by("qnaDate").descending()
        );
    }

    private List<QnaListResponseDTO> getQnaDtoList(Page<Qna> qnas) {

        return qnas.getContent().stream()
                .map(QnaListResponseDTO::new)
                .collect(Collectors.toList());
    }

    private List<QnaListResponseDTO> getQnaDtoListByAdoption(Page<Qna> qnas) {

        return qnas.getContent().stream()
                .filter(qna -> qna.getQnaAdoption() == Y)
                .map(QnaListResponseDTO::new)
                .collect(Collectors.toList());
    }

    private List<QnaListResponseDTO> getQnaDtoListByNonAdoption(Page<Qna> qnas) {

        return qnas.getContent().stream()
                .filter(qna -> qna.getQnaAdoption() == N)
                .map(QnaListResponseDTO::new)
                .collect(Collectors.toList());
    }

}
