package kr.co.ddamddam.mypage.dto.response;

import kr.co.ddamddam.mypage.dto.page.PageMaker;
import lombok.*;

import java.util.List;

/**
 * 마이페이지의 <내가 쓴 게시글> 페이지 처리를 하는 DTO
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageBoardPageResponseDTO {

    private int count;
    private PageMaker pageInfo;
    private List<MypageBoardResponseDTO> boardList;
}
