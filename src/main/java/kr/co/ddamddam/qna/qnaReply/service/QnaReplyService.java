package kr.co.ddamddam.qna.qnaReply.service;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import kr.co.ddamddam.qna.qnaBoard.entity.Qna;
import kr.co.ddamddam.qna.qnaBoard.repository.QnaRepository;
import kr.co.ddamddam.qna.qnaReply.dto.page.PageDTO;
import kr.co.ddamddam.qna.qnaReply.dto.response.QnaReplyListPageResponseDTO;
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

    public List<QnaReplyListResponseDTO> getList(PageDTO pageDTO, Long boardIdx) {

        PageRequest pageable = getPageable(pageDTO);

        log.info("[Qna/Service] QNA 댓글 전체 조회");

        List<QnaReply> qnaReplyList = qnaRepository.findSortedRepliesByQnaIdx(boardIdx);

//        Qna qna = qnaRepository.findById(boardIdx).orElseThrow();
//
//        List<QnaReply> qnaReplyList = qna.getQnaReply();

//        Page<QnaReply> qnaReplies = qnaReplyRepository.findAll(pageable);
//        List<QnaReplyListResponseDTO> qnaReplyList = getQnaReplyDtoList(qnaReplies);

        List<QnaReplyListResponseDTO> qnaReplyDtoList = qnaReplyList.stream()
                .map(QnaReplyListResponseDTO::new)
                .collect(Collectors.toList());

        return qnaReplyDtoList;

//        return QnaReplyListPageResponseDTO.builder()
//                .count(qnaReplyDtoList.size())
//                .pageInfo(new PageResponseDTO(pageDTO)) // TODO : mentor 꺼 갖다썼음. 수정 필요~
//                .qnaReplyList(qnaReplyDtoList)
//                .build();

/*        if (pageDTO.getPage() == 1) {
            // 첫 페이지인 경우
            PageRequest pageable = getPageable(pageDTO);

            // 데이터베이스에서 QNA 댓글 목록 조회 후 DTO 리스트로 꺼내기
            Page<QnaReply> qnaReplies = qnaReplyRepository.findAll(pageable);
            List<QnaReplyListResponseDTO> qnaReplyDtoList = getQnaReplyDtoList(qnaReplies);

            // 데이터베이스에서 조회한 정보를 JSON 형태에 맞는 DTO 로 변환
            return QnaReplyListPageResponseDTO.builder()
                    .count(qnaReplyDtoList.size())
                    .pageInfo(new PageResponseDTO(qnaReplies)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                    .qnaReplyList(qnaReplyDtoList)
                    .build();
        } else {
            // 첫 페이지가 아닌 경우
            pageDTO.setSize((int) qnaReplyRepository.count());
            PageRequest pageable = getPageable(pageDTO);

            Page<QnaReply> qnaReplies = qnaReplyRepository.findAll(pageable);
            List<QnaReplyListResponseDTO> qnaReplyDtoList = getQnaReplyDtoList(qnaReplies);

            return QnaReplyListPageResponseDTO.builder()
                    .count(qnaReplyDtoList.size())
                    .pageInfo(new PageResponseDTO(qnaReplies)) // TODO : mentors 꺼 갖다썼음. 공용이니까 나중에 리팩터링할때 common 으로 옮길게요.
                    .qnaReplyList(qnaReplyDtoList)
                    .build();
        }*/

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
