package kr.co.ddamddam.config.security;

import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

/**
 * 클라이언트가 전송한 토큰을 검사하는 Filter
 * ❗ 모든 요청마다 한 번씩 검사
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // 요청 정보 받아오기
            HttpServletResponse response, // 응답 정보 생성
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);

            log.info("JWT TOKEN FILTER IS RUNNING.... - token : {}", token);

            // 토큰 위조검사 및 인증 완료 처리
            if (token != null) {

                // 토큰 서명 위조 검사 & 토큰을 파싱해서 클레임 얻기
                TokenUserInfo userInfo
                        = tokenProvider.validateAndGetTokenUserInfo(token);

                // 인가 정보 리스트
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                authorityList.add(new SimpleGrantedAuthority(userInfo.getUserRole().toString()));

                // 인증 완료 처리 - 스프링 시큐리티에 인증정보를 전달해서 전역적으로 앱에서 인증 정보를 활용할 수 있게 설정
                AbstractAuthenticationToken auth
                        = new UsernamePasswordAuthenticationToken(
                        userInfo, // 컨트롤러에서 활용할 유저 정보
                        null, // 인증된 사용자의 비밀번호
                        authorityList // 인가 정보
                );

                // 인증 완료 처리 시 클라이언트의 요청정보 세팅
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 스프링 시큐리티 컨테이너에 인증정보 객체 등록
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        } catch (Exception e) {

            throw new UnauthorizationException(UNAUTHENTICATED_USER, parseBearerToken(request));

        }

        // 필터 체인에 내가 만든 필터 실행 명령
        filterChain.doFilter(request, response);

    }

    /**
     * 요청 헤더에서 받아온 토큰 값 앞에 붙어있는 'Bearer' 을 제거해주는 메서드
     * @param request - 순수 토큰 값
     */
    protected String parseBearerToken(
            HttpServletRequest request
    ) {
        // 요청 헤더에서 토큰 가져오기 - "Authorization" : "Bearer {token}"
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
