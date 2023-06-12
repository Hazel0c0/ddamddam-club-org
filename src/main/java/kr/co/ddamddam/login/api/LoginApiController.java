package kr.co.ddamddam.login.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
//import kr.co.ddamddam.login.dto.request.UserLoginRequestDTO;
import kr.co.ddamddam.login.dto.response.LoginResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class LoginApiController {

    /**
     * 로그인 검증 요청을 처리합니다.
     * @param dto: 로그인 페이지에서 사용자가 입력하는 내용 (아이디, 비밀번호, 자동로그인 여부)
     * @return
     */
    @PostMapping("/login")
    public ApplicationResponse<?> login(
            @Validated @RequestBody UserLoginRequestDTO dto
            , BindingResult result
    ) {

        log.info("/api/users/login POST - {}", dto);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ApplicationResponse.bad("아이디 또는 비밀번호를 잘못 입력했습니다.");
        }

        try {
            LoginResponseDTO userInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
