package kr.co.ddamddam.qna.qnaReply.service;

import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaReplyException;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaReply.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaReply.dto.request.QnaReplyInsertRequestDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyResponseDTO;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.qna.qnaReply.repository.QnaReplyRepository;
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
import static kr.co.ddamddam.qna.qnaBoard.exception.custom.QnaErrorCode.*;

@SuppressWarnings("unchecked")
@Service
@Slf4j
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    public List<QnaReplyListResponseDTO> getList(Long boardIdx) {

        log.info("[Qna/Service] QNA 댓글 전체 조회");

        List<QnaReply> replyDateAsc = qnaReplyRepository.findByQnaQnaIdxOrderByQnaReplyDateAsc(boardIdx);

        List<QnaReplyListResponseDTO> qnaReplyDtoList = replyDateAsc.stream()
                .map(QnaReplyListResponseDTO::new)
                .collect(Collectors.toList());

        return qnaReplyDtoList;

    }

    public ResponseMessage writeReply(Long userIdx, QnaReplyInsertRequestDTO dto) {

        log.info("[QnaReply/Service] QNA {}번 게시글에 '{}' 댓글 작성 요청", dto.getBoardIdx(), dto.getReplyContent());

        try {
            User user = userRepository.findById(userIdx).orElseThrow(() -> {
                throw new NotFoundQnaBoardException(NOT_FOUND_USER, userIdx);
            });

            Qna qna = qnaRepository.findById(dto.getBoardIdx()).orElseThrow(() -> {
                throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, dto.getBoardIdx());
            });

            QnaReply newQnaReply = QnaReplyInsertRequestDTO.builder()
                    .boardIdx(dto.getBoardIdx())
                    .replyContent(dto.getReplyContent())
                    .build()
                    .toEntity(qna, user);

            qnaReplyRepository.save(newQnaReply);
        } catch (RuntimeException e) {
            return FAIL;
        }

        return SUCCESS;
    }

    private List<QnaReplyListResponseDTO> getQnaReplyDtoList(Page<QnaReply> qnaReplies) {

        return qnaReplies.getContent().stream()
                .map(QnaReplyListResponseDTO::new)
                .collect(Collectors.toList());
    }

    private PageRequest getPageable(PageDTO pageDTO) {

        return PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getSize(),
                Sort.by("qnaDate").descending()
        );
    }

    public ResponseMessage deleteReply(Long replyIdx) {

        log.info("[Qna/Service] QNA 댓글 삭제");

        QnaReply qnaReply = qnaReplyRepository.findById(replyIdx).orElseThrow(() -> {
            throw new NotFoundQnaReplyException(NOT_FOUND_REPLY, replyIdx);
        });

        if (qnaReply.getQnaReplyAdoption() == QnaAdoption.Y) {
            return FAIL;
        }

        qnaReplyRepository.deleteById(replyIdx);

        return SUCCESS;
    }
}
