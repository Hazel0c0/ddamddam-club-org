package kr.co.ddamddam.qna.dto.response;

import kr.co.ddamddam.mentor.dto.page.PageResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaListResponseDTO {

    private int count;
    private PageResponseDTO pageInfo;
    private List<QnaDetailResponseDTO> qnas;
}
