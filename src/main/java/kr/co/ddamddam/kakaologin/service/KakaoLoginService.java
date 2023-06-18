package kr.co.ddamddam.kakaologin.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.co.ddamddam.config.security.TokenProvider;
import kr.co.ddamddam.kakaologin.dto.request.KakaoSignUpRequestDTO;
import kr.co.ddamddam.kakaologin.dto.response.KakaoLoginResponseDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;


/**
 * 카카오 로그인 처리
 */
@Service
@Slf4j
public class KakaoLoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    
    @Value("${sns.kakao.app-key}")
    private String kakaoAppKey;
    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRedirectURI;

    /**
     * 카카오 로그인
     * 첫 로그인 시, 회원가입을 수행합니다.
     * @param accessToken - 카카오 액세스 토큰
     * @return 카카오 로그인 응답 DTO
     *          (상태코드, 우리 서비스의 토큰, 비밀번호를 제외한 회원정보)
     */
    public KakaoLoginResponseDTO kakaoLogin (final String accessToken) {

        log.info("[KakaoLoginService] kakaoLogin... - accessToken : {}", accessToken);

        KakaoSignUpRequestDTO kakaoSignUpRequestDTO
                = new KakaoSignUpRequestDTO(getUserKakaoInfo(accessToken));

        User user
                = findByUserEmail(kakaoSignUpRequestDTO.getUserEmail(), kakaoSignUpRequestDTO);

        String token = tokenProvider.createToken(user);

        return new KakaoLoginResponseDTO(HttpStatus.OK, token, user);
    }

    /**
     * 인가코드를 통해 카카오 액세스 토큰을 받아옵니다.
     * @return 카카오 액세스 토큰
     */
    public String getKakaoAccessToken(final String code) {

        String accessToken = "";
        String refreshToken = "";
        final String REQUEST_URL = "https://kauth.kakao.com/oauth/token";

        try {

            URL url = new URL(REQUEST_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false 인 setDoOutput 을 true 로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요한 파라미터 스트링을 통해 전송
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + kakaoAppKey);
            sb.append("&redirect_uri=" + kakaoRedirectURI);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200 인 경우, 성공
            int responseCode = conn.getResponseCode();
            log.info("[KakaoLoginService] getKakaoAccessToken - response code : {}", responseCode);

            // 요청을 통해 얻은 JSON 타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder responseBody = new StringBuilder();

            while((line = br.readLine()) != null) {
                responseBody.append(line);
            }

            log.info("[KakaoLoginService] getKakaoAccessToken - response body : {}", responseBody.toString());

            // Gson 라이브러리에 포함된 클래스로 JSON 파싱 객체 생성
            JsonElement element = JsonParser.parseString(responseBody.toString());

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("[KakaoLoginService] getKakaoAccessToken - access token : {}", accessToken);
            log.info("[KakaoLoginService] getKakaoAccessToken - refresh token : {}", refreshToken);

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    /**
     * 카카오에서 토큰을 통해 유저 정보를 받아옵니다.
     * @param accessToken - 카카오 액세스 토큰
     * @return 유저 정보를 담은 HashMap (id, nickname, email)
     * // TODO : 프로필사진 및 파일유틸 구현 후 프로필 사진 추가 필요
     */
    public HashMap<String, Object> getUserKakaoInfo(final String accessToken) {

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기 때문에 HashMap 타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        final String REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

        try {

            URL url = new URL(REQUEST_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header 에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();

            log.info("[KakaoLoginService] getUserKakaoInfo - responseCode : {}", responseCode);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line = "";
            StringBuilder responseBody = new StringBuilder();

            while ((line = br.readLine()) != null) {
                responseBody.append(line);
            }

            log.info("[KakaoLoginService] getUserKakaoInfo - response body : {}", responseBody);

            JsonElement element = JsonParser.parseString(responseBody.toString());

            String id = element.getAsJsonObject().get("id").getAsString();

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();

            if (kakaoAccount.getAsJsonObject().get("email") != null) {
                String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
                userInfo.put("email", email);
            }

            userInfo.put("nickname", nickname);
            userInfo.put("id", id);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }


    /**
     * 이미 카카오 계정으로 가입되어 있는 지 확인하고 해당 유저의 정보를 찾아 반환합니다.
     * 가입이 되어 있지 않은 경우, 회원가입을 수행합니다.
     * @param userEmail - 회원의 이메일
     * @param kakaoSignUpRequestDTO - 회원가입에 사용할 카카오 회원가입 요청 dto
     * @return 이메일로 찾은 User Entity
     */
    public User findByUserEmail(
            final String userEmail,
            final KakaoSignUpRequestDTO kakaoSignUpRequestDTO
    ) {
        log.info("[KakaoLoginService] findByUserEmailByKakao... - userEmail : {}", userEmail);

        Optional<User> user = userRepository.findByUserEmailByKakaoLogin(userEmail);

        if (user.isEmpty()) {
            String email = signUp(kakaoSignUpRequestDTO);
            user = userRepository.findByUserEmailByKakaoLogin(email);
        }

        return user.orElse(null);
    }

    /**
     * 카카오 계정으로 첫 로그인시 회원가입 수행
     * @param requestDTO - 회원가입 요청 DTO
     * @return 회원가입 성공 한 회원의 이메일
     */
    public String signUp(
            final KakaoSignUpRequestDTO requestDTO
    ) {
        log.info("[KakaoLoginService] signUp- requestDTO : {}", requestDTO);

        String userEmail;
        userEmail = userRepository.save(
                requestDTO.toEntity()).getUserEmail();
        return userEmail;
    }

}
