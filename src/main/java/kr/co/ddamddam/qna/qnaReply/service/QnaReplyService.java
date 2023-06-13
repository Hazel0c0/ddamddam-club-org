package kr.co.ddamddam.qna.qnaReply.service;

import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaBoardException;
import kr.co.ddamddam.qna.qnaBoard.exception.custom.NotFoundQnaReplyException;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaReply.dto.request.QnaReplyInsertRequestDTO;
import kr.co.ddamddam.qna.qnaReply.dto.request.QnaReplyModifyRequestDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.qna.qnaReply.repository.QnaReplyRepository;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kr.co.ddamddam.common.response.ResponseMessage.*;
import static kr.co.ddamddam.qna.qnaBoard.entity.QnaAdoption.*;
import static kr.co.ddamddam.qna.qnaBoard.exception.custom.QnaErrorCode.*;

@SuppressWarnings("unchecked")
@Service
@Slf4j
@RequiredArgsConstructor
public class QnaReplyService {

    private final int REPLY_COUNT_UP = 1;
    private final int REPLY_COUNT_DOWN = -1;
    private final QnaReplyRepository qnaReplyRepository;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    public List<QnaReplyListResponseDTO> getList(final Long boardIdx) {

        log.info("[Qna/Service] QNA 댓글 전체 조회");

        List<QnaReply> replyDateAsc = qnaReplyRepository.findByQnaQnaIdxOrderByQnaReplyDateAsc(boardIdx);

        List<QnaReplyListResponseDTO> qnaReplyDtoList = replyDateAsc.stream()
                .map(QnaReplyListResponseDTO::new)
                .collect(Collectors.toList());

        return qnaReplyDtoList;

    }

    public ResponseMessage writeReply(
            final Long userIdx,
            final QnaReplyInsertRequestDTO dto
    ) {

        log.info("[QnaReply/Service] QNA 댓글 작성, index - {}, payload - {}", dto.getBoardIdx(), dto.getReplyContent());

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_USER, userIdx);
        });

        Qna qna = qnaRepository.findById(dto.getBoardIdx()).orElseThrow(() -> {
            throw new NotFoundQnaBoardException(NOT_FOUND_BOARD, dto.getBoardIdx());
        });

        if (qna.getQnaAdoption() == Y) {
            return FAIL;
        }

        QnaReply newQnaReply = QnaReplyInsertRequestDTO.builder()
                .boardIdx(dto.getBoardIdx())
                .replyContent(dto.getReplyContent())
                .build()
                .toEntity(qna, user);

        qna.setReplyCount(qna.getReplyCount() + REPLY_COUNT_UP);
        qnaRepository.save(qna);
        qnaReplyRepository.save(newQnaReply);

        return SUCCESS;
    }

    public ResponseMessage deleteReply(final Long replyIdx) {

        log.info("[Qna/Service] QNA 댓글 삭제, index - {}", replyIdx);

        QnaReply qnaReply = qnaReplyRepository.findById(replyIdx).orElseThrow(() -> {
            throw new NotFoundQnaReplyException(NOT_FOUND_REPLY, replyIdx);
        });

        if (qnaReply.getQnaReplyAdoption() == Y) {
            return FAIL;
        }

        Qna qna = qnaReply.getQna();
        qna.setReplyCount(
                qna.getReplyCount() + REPLY_COUNT_DOWN
        );
        qnaRepository.save(qna);

        qnaReplyRepository.deleteById(replyIdx);

        return SUCCESS;
    }

    public ResponseMessage modifyReply(final QnaReplyModifyRequestDTO dto) {
        
        log.info("[Qna/Service] QNA 댓글 수정, index - {}, payload - {}", dto.getReplyIdx(), dto.getReplyContent());

        QnaReply qnaReply = qnaReplyRepository.findById(dto.getReplyIdx()).orElseThrow(() -> {
            throw new NotFoundQnaReplyException(NOT_FOUND_REPLY, dto.getReplyIdx());
        });

        if (qnaReply.getQna().getQnaAdoption() == Y) {
            return FAIL;
        }

        qnaReply.setQnaReplyContent(dto.getReplyContent());

        qnaReplyRepository.save(qnaReply);

        return SUCCESS;
    }

    public ResponseMessage adoptQnaReply(Long replyIdx) {

        log.info("[Qna/Service] QNA 댓글 채택, index - {}", replyIdx);

        QnaReply qnaReply = qnaReplyRepository.findById(replyIdx).orElseThrow(() -> {
            throw new NotFoundQnaReplyException(NOT_FOUND_REPLY, replyIdx);
        });

        Qna qna = qnaReply.getQna();

        if (qna.getQnaAdoption() == Y) {
            return FAIL;
        }

        // 댓글 1개가 채택되면, 해당 댓글이 달려있는 게시글도 채택완료 처리
        qnaReply.setQnaReplyAdoption(Y);
        qna.setQnaAdoption(Y);

        qnaReplyRepository.save(qnaReply);
        qnaRepository.save(qna);

        return SUCCESS;
    }
}
