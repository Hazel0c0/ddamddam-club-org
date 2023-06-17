package kr.co.ddamddam.kakaologin.api;

import kr.co.ddamddam.kakaologin.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/ddamddam/oauth")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    /**
     * 클라이언트에서 보낸 인가코드로 카카오 액세스 토큰을 발급받고
     * 액세스 토큰을 통해 카카오 로그인 및 우리 서비스의 회원가입을 처리합니다.
     * @return 카카오 로그인 응답 DTO (상태코드, 우리 서비스의 토큰, 유저 이메일)
     */
    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoCallBack(
            @RequestParam("code") String code
    ) {
        log.info("GET : Kakao Login Controller - code : {}", code);

        String accessToken = kakaoLoginService.getKakaoAccessToken(code);

        return ResponseEntity.ok().body(
                kakaoLoginService.kakaoLogin(accessToken));
    }

}
