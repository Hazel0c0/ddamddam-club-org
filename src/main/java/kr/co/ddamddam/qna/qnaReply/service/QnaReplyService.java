package kr.co.ddamddam.qna.qnaReply.service;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.common.exception.custom.NotFoundReplyException;
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
import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

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
    private final ValidateToken validateToken;

    public List<QnaReplyListResponseDTO> getList(final Long boardIdx) {

        log.info("[Qna/Service] QNA 댓글 전체 조회");

        List<QnaReply> replyDateAsc = qnaReplyRepository.findByQnaQnaIdxOrderByQnaReplyAdoptionDescQnaReplyDateAsc(boardIdx);

        List<QnaReplyListResponseDTO> qnaReplyDtoList = replyDateAsc.stream()
                .map(QnaReplyListResponseDTO::new)
                .collect(Collectors.toList());

        return qnaReplyDtoList;

    }

    public ResponseMessage writeReply(
            final TokenUserInfo tokenUserInfo,
            final QnaReplyInsertRequestDTO dto
    ) {
        log.info("[QnaReply/Service] QNA 댓글 작성, index - {}, payload - {}", dto.getBoardIdx(), dto.getReplyContent());

        validateToken.validateToken(tokenUserInfo); // 로그인 안 한 경우 걸러내기

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundBoardException(NOT_FOUND_USER, userIdx);
        });

        Qna qna = qnaRepository.findById(dto.getBoardIdx()).orElseThrow(() -> {
            throw new NotFoundBoardException(NOT_FOUND_BOARD, dto.getBoardIdx());
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

    public ResponseMessage deleteReply(
            final TokenUserInfo tokenUserInfo,
            final Long replyIdx
    ) {
        log.info("[Qna/Service] QNA 댓글 삭제, index - {}", replyIdx);

        QnaReply qnaReply = validateDTO(tokenUserInfo, replyIdx);

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

    public ResponseMessage modifyReply(
            final TokenUserInfo tokenUserInfo,
            final QnaReplyModifyRequestDTO dto
    ) {
        log.info("[Qna/Service] QNA 댓글 수정, index - {}, payload - {}", dto.getReplyIdx(), dto.getReplyContent());

        QnaReply qnaReply = validateDTO(tokenUserInfo, dto.getReplyIdx());

        if (qnaReply.getQna().getQnaAdoption() == Y) {
            return FAIL;
        }

        qnaReply.setQnaReplyContent(dto.getReplyContent());

        qnaReplyRepository.save(qnaReply);

        return SUCCESS;
    }

    public ResponseMessage adoptQnaReply(
            final TokenUserInfo tokenUserInfo,
            final Long replyIdx
    ) {
        log.info("[Qna/Service] QNA 댓글 채택, index - {}", replyIdx);

        validateToken.validateToken(tokenUserInfo);

        QnaReply qnaReply = qnaReplyRepository.findById(replyIdx).orElseThrow(() -> {
            throw new NotFoundReplyException(NOT_FOUND_REPLY, replyIdx);
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

    /**
     * 댓글의 작성자와 토큰의 유저가 동일한지 검사하는 기능
     * @param tokenUserInfo - 로그인 중인 유저의 정보
     * @param replyIdx - 클라이언트에서 요청한 댓글 번호
     */
    private QnaReply validateDTO(TokenUserInfo tokenUserInfo, Long replyIdx) {
        // 토큰 인증 실패
        if (tokenUserInfo == null) {
            throw new UnauthorizationException(UNAUTHENTICATED_USER, "로그인 후 이용 가능합니다.");
        }

        // 댓글 존재 여부 확인
        QnaReply qnaReply = qnaReplyRepository.findById(replyIdx).orElseThrow(() -> {
            throw new NotFoundBoardException(NOT_FOUND_BOARD, replyIdx);
        });

        // 토큰 내 회원 이메일과 댓글 작성자의 이메일이 일치하지 않음 -> 작성자가 아님 -> 수정 및 삭제 불가
        if (!qnaReply.getUser().getUserEmail().equals(tokenUserInfo.getUserEmail())) {
            throw new UnauthorizationException(ACCESS_FORBIDDEN, tokenUserInfo.getUserEmail());
        }

        return qnaReply;
    }
}
