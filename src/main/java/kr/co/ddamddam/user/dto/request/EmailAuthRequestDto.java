package kr.co.ddamddam.user.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailAuthRequestDto {

    @NotEmpty
    public String email;
}
