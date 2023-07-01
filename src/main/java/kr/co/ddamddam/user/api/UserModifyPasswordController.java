package kr.co.ddamddam.user.api;

import kr.co.ddamddam.common.exception.custom.LoginException;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.user.dto.request.UserPasswordRequestDTO;
import kr.co.ddamddam.user.service.UserModifyPasswordService;
import kr.co.ddamddam.useremail.dto.UserEmailCheckRequestDTO;
import kr.co.ddamddam.useremail.dto.request.UserCodeRequestDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeCheckResponseDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeResponseDTO;
import kr.co.ddamddam.useremail.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.INVALID_PARAMETER;
import static kr.co.ddamddam.common.exception.custom.ErrorCode.INVALID_PASSWORD;
import static kr.co.ddamddam.common.response.ResponseMessage.*;

/**
 * 1. 로그인 중인 유저의 비밀번호 변경 처리
 * 2. 비밀번호 찾기
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/auth")
public class UserModifyPasswordController {

    private final UserEmailService emailService;
    private final UserModifyPasswordService userModifyPasswordService;

    /**
     * 패스워드 변경 요청을 처리
     *
     * @param tokenUserInfo - 로그인 중인 회원의 정보
     * @param requestDTO    - newUserPassword : 새 비밀번호
     * @return 변경 성공시 SUCCESS
     *          입력한 기존 비밀번호가 유저의 비밀번호와 다를 경우 401 상태코드 리턴
     *          입력한 새 비밀번호가 유저의 비밀번호와 같을 경우 400 상태코드 리턴
     */
    @PostMapping("/modify-password")
    public ResponseEntity<?> modifyPassword(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @Valid @RequestBody UserPasswordRequestDTO requestDTO
    ) {
        log.info("POST : /auth/modify-password, userIdx : {}, new password : {}"
                , tokenUserInfo.getUserIdx(), requestDTO);

        try {
            userModifyPasswordService.modifyPassword(tokenUserInfo, requestDTO);
        } catch (LoginException e) {
            if (e.getErrorCode().equals(INVALID_PASSWORD)) {
                return ResponseEntity.status(401).build();
            }
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok().body(SUCCESS);
    }

    /**
     * 비밀번호 찾기 시, 발급한 인증코드와 클라이언트에서 입력한 인증코드가 동일한지 확인합니다.
     *
     * @param requestDTO - 인증코드
     * @return - 일치할 시 SUCCESS, 일치하지 않을 시 FAIL
     */
    @PostMapping("/check")
    public ResponseEntity<ResponseMessage> codeCheck(
            @RequestBody UserCodeRequestDTO requestDTO
    ) {
        UserCodeCheckResponseDTO userCodeCheckResponseDTO
                = emailService.checkCode(requestDTO.getCode());

        // 인증코드가 일치하지 않는 경우
        if (!userCodeCheckResponseDTO.getCheckResult()) {
            return ResponseEntity.badRequest().body(FAIL);
        }

        return ResponseEntity.ok().body(SUCCESS);
    }

}
