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
     * 클라이언트에서 보낸 카카오 인가 코드를 받고 카카오에서 토큰 정보를 받아오는 기능
     * @return
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
