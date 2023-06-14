package kr.co.ddamddam.common.auth;

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
    private String SECRET_KEY;

    /**
     * JWT 토큰 생성 메서드
     * @param user - 토큰의 클레임에 포함될 유저 정보
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
                )
                .setSubject(String.valueOf(user.getUserIdx())) // 토큰 식별자
                .setIssuer("ddamddamclub app") // 토큰 발급자 정보
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(expiryDate) // 토큰 만료 시간
                .setClaims(claims)
                .compact();

    }

}
