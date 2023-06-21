package kr.co.ddamddam.config;

import kr.co.ddamddam.config.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity // 웹보안 관련 설정을 이파일을 기반으로 적용
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // 비밀번호 암호화 객체 빈 등록
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 기본 설정을 처리하는 빈 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 시큐리티 설치시 초기에 뜨는 강제 인증을 해제
        http
//                .csrf()
//                .disable() // csrf 토큰공격 방어기능 해제
//                .authorizeRequests()
//                .antMatchers("/**") // 해당하는 url 패턴은 로그인 없이도 들어갈 수 있도록
//                .permitAll();

                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                // 세션 사용 X
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    // 어떤 요청에서 인증을 안할 것인지 설정
                    // - 회원가입, 로그인, index 페이지, 각 게시판 첫 페이지
                    .antMatchers("/api/ddamddam/**", "/").permitAll()
//                    .antMatchers("/socket/chat/**", "/").permitAll()
                /*
                    .antMatchers("/api/ddamddam/oauth/**").permitAll()
                    .antMatchers("/socket/chat/**").permitAll()
                    .antMatchers("/api/ddamddam/companies/list").permitAll()
                    .antMatchers("/api/ddamddam/mentors/list").permitAll()
                    .antMatchers("/api/ddamddam/mentors/sublist").permitAll()
                    .antMatchers("/api/ddamddam/project").permitAll()
                    .antMatchers("/api/ddamddam/qna").permitAll()
                    .antMatchers("/api/ddamddam/qna/top").permitAll()
                    .antMatchers("/api/ddamddam/qna/adopts").permitAll()
                    .antMatchers("/api/ddamddam/qna/non-adopts").permitAll()
                    .antMatchers("/api/ddamddam/qna/search").permitAll()
                    .antMatchers("/api/ddamddam/reviews/list").permitAll()
                    .antMatchers("/api/ddamddam/reviews/search/**").permitAll()
                    .antMatchers("/api/ddamddam/reviews/viewTop3").permitAll()
                    .antMatchers("/api/ddamddam/reviews/view").permitAll()
                    .antMatchers("/api/ddamddam/reviews/rating").permitAll()

                 */
                    // 어떤 요청에서 인증을 할 것인지 설정 - 그 외의 모든 경로
                    .anyRequest().authenticated();
                ;

        // 토큰 인증 필터 연결
        http.addFilterAfter(
                jwtAuthFilter
                , CorsFilter.class
        );

        return http.build();
    }

}
