package kr.co.ddamddam.user.api;

import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.user.dto.request.UserFindPasswordRequestDTO;
import kr.co.ddamddam.user.service.UserFindPasswordService;
import kr.co.ddamddam.useremail.dto.UserEmailCheckRequestDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static kr.co.ddamddam.common.response.ResponseMessage.*;

/**
 * 로그인하지 않은 익명 유저의 비밀번호 찾기
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/auth")
public class UserFindPasswordController {

    private final UserFindPasswordService userFindPasswordService;

    /**
     * 가입 시 사용한 이메일과 이름을 입력받고,
     * 유저가 존재하는지 검증 후 이메일로 임시비밀번호를 발송합니다.
     *
     * @param requestDTO - 익명사용자가 입력한 유저이메일, 유저이름
     * @return - 발송 성공시 SUCCESS, 실패시 FAIL
     */
    @PostMapping("/find-password")
    public ResponseEntity<ResponseMessage> findPassword(
            @Valid @RequestBody UserFindPasswordRequestDTO requestDTO
    ) {
        log.info("POST : /auth/find-password, dto : {}", requestDTO);

        ResponseMessage result = userFindPasswordService.findPassword(requestDTO);

        if (result == FAIL) {
            return ResponseEntity.ok().body(FAIL);
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

}
