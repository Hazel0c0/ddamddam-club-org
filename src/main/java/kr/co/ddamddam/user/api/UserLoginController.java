package kr.co.ddamddam.user.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.user.dto.request.LoginRequestDTO;
import kr.co.ddamddam.user.dto.response.LoginResponseDTO;
import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원가입, 로그인 요청을 처리하는 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/auth")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/signin")
    public ApplicationResponse<?> signIn(
            @Validated @RequestBody LoginRequestDTO requestDTO,
            BindingResult result
    ) {
        log.info("POST : /auth/signup {} 로그인 요청", requestDTO);

        if(result.hasErrors()) {
            log.warn(result.toString());
            return ApplicationResponse.bad(
                    LoginResponseDTO.builder()
                            .message(String.valueOf(ResponseMessage.FAIL))
                            .build()
            );
        }

        try {

            LoginResponseDTO userInfo = userLoginService.getByCredentials(
                    requestDTO.getUserEmail(),
                    requestDTO.getUserPassword());

            log.info("userInfo : {}", userInfo);

            return ApplicationResponse.ok(userInfo);

        } catch (RuntimeException e) {

            return ApplicationResponse.bad(
                    LoginResponseDTO.builder()
                            .message(e.getMessage())
                            .build()
            );

        }

    }

}
