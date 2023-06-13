package kr.co.ddamddam.review.dto.response;

import lombok.*;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewTopListResponseDTO {

    //TODO: top3글 보여주기
    private Long  boardIdx;
    private String boardTitle;
    private int boardView;
    private String companyName;
    private Float boardRating; //별점
    private String boardJob; //직무
    private int boardTenure; //경력
    private String boardLocation; //회사위치


}
