package kr.co.ddamddam.qna.qnaReply.service;

import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaReply.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListResponseDTO;
import kr.co.ddamddam.qna.qnaReply.entity.QnaReply;
import kr.co.ddamddam.qna.qnaReply.repository.QnaReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Service
@Slf4j
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;
    private final QnaRepository qnaRepository;

    public List<QnaReplyListResponseDTO> getList(Long boardIdx) {

        log.info("[Qna/Service] QNA 댓글 전체 조회");

        List<QnaReply> replyDateAsc = qnaReplyRepository.findByQnaQnaIdxOrderByQnaReplyDateAsc(boardIdx);

        List<QnaReplyListResponseDTO> qnaReplyDtoList = replyDateAsc.stream()
                .map(QnaReplyListResponseDTO::new)
                .collect(Collectors.toList());

        return qnaReplyDtoList;

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
}
