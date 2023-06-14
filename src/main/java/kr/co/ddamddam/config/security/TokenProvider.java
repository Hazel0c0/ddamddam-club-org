package kr.co.ddamddam.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.co.ddamddam.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 토큰을 발급하고, 서명 위조를 검사하는 클래스
 */
@Service
@Slf4j
public class TokenProvider {
    
    @Value("${jwt.secret}")
    private static String SECRET_KEY; // 토큰 서명에 사용할 불변성을 가진 비밀 키

    /**
     * JWT 토큰 생성 메서드
     * @param user - 토큰의 클레임(내용)에 포함될 로그인 유저 정보
     * @return - 생성된 JSON 을 암호화한 토큰 값
     */
    public String createToken(User user) {

        // 토큰 만료시간 설정 (24시간)
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        // 추가 클레임
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getUserEmail());
        claims.put("role", user.getUserRole());

        // 토큰 생성
        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                ) // 토큰 header 에 들어갈 서명
                .setClaims(claims) // 추가 커스텀 클레임
                .setSubject(String.valueOf(user.getUserIdx())) // 토큰 식별자
                .setIssuer("ddamddamclub app") // 토큰 발급자 정보
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expiryDate) // 토큰 만료 시간
                .compact();
    }

    /**
     * 클라이언트가 보낸 토큰을 디코딩 / 파싱해서 토큰 위조 여부 확인
     * @param token - 클라이언트가 전송한 인코딩 된 토큰
     * @return - 토큰에서 subject(userIdx, 유저 식별자) 를 꺼내서 반환
     */
    public String validateAndGetUserIdx(String token) {
        
        Claims claims = Jwts.parserBuilder()
                // 토큰 발급자의 발급 당시 서명을 넣어줌
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                // 디코딩 서명 기록을 파싱
                // 클라이언트 토큰의 서명과 서버 발급 당시 서명을 비교
                // 위조 X 경우 : 'body'에 payload(Claims) 를 리턴
                // 위조 O 경우 : 예외 발생
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
