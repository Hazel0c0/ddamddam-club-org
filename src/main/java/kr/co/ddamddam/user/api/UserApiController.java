package kr.co.ddamddam.user.api;

import kr.co.ddamddam.common.response.ApplicationResponse;
import kr.co.ddamddam.user.dto.request.LoginRequestDTO;
import kr.co.ddamddam.user.dto.response.LoginResponseDTO;
import kr.co.ddamddam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class UserApiController {

    private final UserService userService;

    @PostMapping("/signin")
    public ApplicationResponse<?> signIn(
            @Validated @RequestBody LoginRequestDTO dto
    ) {
        LoginResponseDTO responseDTO = userService.authenticate(dto);

        return ApplicationResponse.ok(responseDTO);
    }

}
