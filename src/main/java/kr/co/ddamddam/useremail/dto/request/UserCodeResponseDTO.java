package kr.co.ddamddam.useremail.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCodeResponseDTO {

    private String code;
}
