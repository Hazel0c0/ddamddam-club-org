package kr.co.ddamddam.qna.qnaBoard.service;

import kr.co.ddamddam.common.common.TruncateString;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.qna.qnaBoard.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.page.PageResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.request.QnaInsertRequestDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.request.QnaModifyRequestDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaDetailResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaListResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaListPageResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.dto.response.QnaTopListResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaHashtag.entity.Hashtag;
import kr.co.ddamddam.qna.qnaHashtag.repository.HashtagRepository;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.repository.QnaReplyRepository;
import kr.co.ddamddam.qna.qnaReply.service.QnaReplyService;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static kr.co.ddamddam.common.response.ResponseMessage.*;
import static kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption.*;
import static kr.co.ddamddam.qna.qnaBoard.exception.custom.QnaErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaService {

    private final int VIEW_COUNT_UP = 1;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;
    private final QnaReplyRepository qnaReplyRepository;
    private final HashtagRepository hashtagRepository;
    private final QnaReplyService qnaReplyService;

    public QnaListPageResponseDTO getList(PageDTO pageDTO) {

        PageRequest pageable = getPageable(pageDTO);

        // 데이터베이스에서 QNA 게시글 목록 조회 후 DTO 리스트로 꺼내기
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        List<QnaListResponseDTO> qnaList = getQnaDtoList(qnas);

        // 데이터베이스에서 조회한 정보를 JSON 형태에 맞는 DTO 로 변환
        return QnaListPageResponseDTO.builder()
                .count(qnaList.size())
                .pageInfo(new PageResponseDTO(qnas))
                .qnas(qnaList)
                .build();
    }

    public List<QnaTopListResponseDTO> getListTop3ByView() {

        log.info("[Qna/Service] QNA 게시글 조회순 TOP3 정렬");

        List<Qna> qnaListTop3 = qnaRepository.findTop3ByOrderByViewCountDesc();

        return qnaListTop3.stream()
                .map(qna -> QnaTopListResponseDTO.builder()
                        .boardIdx(qna.getQnaIdx())
                        .boardTitle(TruncateString.truncate(qna.getQnaTitle(), 15))
                        .boardContent(TruncateString.truncate(qna.getQnaContent(), 40))
                        .qnaAdoption(qna.getQnaAdoption())
                        .boardViewCount(qna.getViewCount())
                        .boardReplyCount(qna.getReplyCount())
                        .build()
                ).collect(Collectors.toList());
    }

    public QnaDetailResponseDTO getDetail(Long boardIdx) {

        log.info("[Qna/Service] QNA 게시글 상세보기 boardIdx - {}", boardIdx);

        if (boardIdx == null) {
            throw new NotFoundQnaBoardException(INVALID_PARAMETER, boardIdx);
        }

        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        User user = userRepository.findById(qna.getUser().getUserIdx()).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_USER, qna.getUser().getUserIdx());
        });

        List<QnaReplyListResponseDTO> replyList = qnaReplyService.getList(qna.getQnaIdx());

        QnaDetailResponseDTO qnaDetailResponseDTO = new QnaDetailResponseDTO(
                qna,
                user,
                hashtagToString(qna.getHashtagList()),
                replyList
        );

        return qnaDetailResponseDTO;
    }

    public Long writeBoard(Long userIdx, QnaInsertRequestDTO dto) {

        log.info("[Qna/Service] QNA 게시글 작성 - {}", dto);

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_USER, userIdx);
        });

        Qna savedQna = qnaRepository.save(
                dto.toEntity(user)
        );

        if (dto.getHashtagList().size() > 0) {

            // 해시태그 저장
            for (String tag : dto.getHashtagList()) {
                Hashtag newHashtag = Hashtag.builder()
                        .hashtagContent(tag)
                        .qna(savedQna)
                        .build();
                Hashtag saved = hashtagRepository.save(newHashtag);
                savedQna.getHashtagList().add(saved);
            }
        }

        return savedQna.getQnaIdx();
    }

    public ResponseMessage deleteBoard(Long boardIdx) {

        log.info("[Qna/Service] QNA 게시글 삭제 - {}", boardIdx);

        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        if (qna.getQnaAdoption() == Y) {
            return FAIL;
        }

        qnaRepository.deleteById(boardIdx);

        return SUCCESS;
    }
    
    public ResponseMessage modifyBoard(Long boardIdx, QnaModifyRequestDTO dto) {

        log.info("[Qna/Service] QNA 게시글 수정 - {}, payload - {}", boardIdx, dto);

        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        if (qna.getQnaAdoption() == Y) {
            return FAIL;
        }

        qna.clearHashtagList();

        qna.setQnaTitle(dto.getBoardTitle());
        qna.setQnaContent(dto.getBoardContent());
        qna.setHashtagList(stringToHashtag(dto.getHashtagList()));

        qnaRepository.save(qna);

        return SUCCESS;
    }

    public ResponseMessage updateViewCount(Long boardIdx) {

        log.info("[Qna/Service] QNA 게시글 조회수 상승 - {}", boardIdx);

        Qna qna = qnaRepository.findById(boardIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, boardIdx);
        });

        qna.setViewCount(qna.getViewCount() + VIEW_COUNT_UP);

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
                .pageInfo(new PageResponseDTO(qnas))
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
                .pageInfo(new PageResponseDTO(qnas))
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
                .map(qna ->
                    new QnaListResponseDTO(qna,
                            hashtagToString(qna.getHashtagList())
                    )
                )
                .collect(Collectors.toList());
    }

    private List<QnaListResponseDTO> getQnaDtoListByAdoption(Page<Qna> qnas) {

        return qnas.getContent().stream()
                .filter(qna -> qna.getQnaAdoption() == Y)
                .map(qna ->
                        new QnaListResponseDTO(qna,
                                hashtagToString(qna.getHashtagList())
                        )
                )
                .collect(Collectors.toList());
    }

    private List<QnaListResponseDTO> getQnaDtoListByNonAdoption(Page<Qna> qnas) {

        return qnas.getContent().stream()
                .filter(qna -> qna.getQnaAdoption() == N)
                .map(qna ->
                        new QnaListResponseDTO(qna,
                                hashtagToString(qna.getHashtagList())
                        )
                )
                .collect(Collectors.toList());
    }

    public List<String> hashtagToString(List<Hashtag> hashtagList) {
        List<String> StringList = new ArrayList<>();
        for (Hashtag foundHashtag : hashtagList) {
            StringList.add(foundHashtag.getHashtagContent());
        }
        return StringList;
    }

    public List<Hashtag> stringToHashtag(List<String> strHashtagList) {
        List<Hashtag> hashtagList = new ArrayList<>();
        for (String tag : strHashtagList) {
            hashtagList.add(
                    Hashtag.builder()
                            .hashtagContent(tag)
                            .build()
            );
        }
        return hashtagList;
    }

}
