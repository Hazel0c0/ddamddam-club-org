package kr.co.ddamddam.user.api;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

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
     * @param tokenUserInfo - 로그인 중인 회원의 정보
     * @param requestDTO - newUserPassword : 새 비밀번호
     * @return 변경 성공시 SUCCESS, 변경 실패시 FAIL
     */
    @PostMapping("/modify-password")
    public ResponseEntity<?> modifyPassword(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @Valid @RequestBody UserPasswordRequestDTO requestDTO
    ) {
        log.info("POST : /auth/modify-password, userIdx : {}, new password : {}"
                , tokenUserInfo.getUserIdx(), requestDTO);

        ResponseMessage result
                = userModifyPasswordService.modifyPassword(tokenUserInfo, requestDTO);

        return ResponseEntity.ok().body(result);
    }


//    // TODO : 아래는 미완성
//
//    /**
//     * 비밀번호 찾기 요청 시 이메일을 입력받고, 해당 이메일로 인증코드를 발송합니다.
//     *
//     * @param requestDTO - 유저 이메일
//     * @return 발송된 인증코드
//     * @throws MessagingException - 메일 전송 관련 예외
//     */
//    @PostMapping("/find-password")
//    public ResponseEntity<UserCodeResponseDTO> findPassword(
//            @Valid @RequestBody UserEmailCheckRequestDTO requestDTO
//    ) throws MessagingException {
//        log.info("POST : findPassword - userEmail : {}", requestDTO);
//
//        UserCodeResponseDTO userCodeResponseDTO
//                = emailService.sendEmailByFindPassword(requestDTO.getEmail());
//
//        return ResponseEntity.ok().body(userCodeResponseDTO);
//    }

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
