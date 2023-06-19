package kr.co.ddamddam.user.service;

import kr.co.ddamddam.common.exception.custom.LoginException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.config.security.TokenProvider;
import kr.co.ddamddam.user.dto.request.LoginRequestDTO;
import kr.co.ddamddam.user.dto.response.LoginResponseDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    public LoginResponseDTO authenticate(final LoginRequestDTO dto) {

        if (dto.getUserEmail().equals("")) {
            throw new LoginException(INVALID_PARAMETER, dto.getUserEmail());
        }

        // 이메일을 통해 회원 정보 조회
        User user = userRepository.findByUserEmail(dto.getUserEmail()).orElseThrow(() -> {
            throw new LoginException(NOT_FOUND_USER_BY_EMAIL, dto.getUserEmail());
        });

        // 패스워드 검증
        String rawUserPassword = dto.getUserPassword(); // 입력한 비밀번호
        String encodedUserPassword = user.getUserPassword(); // DB 에 저장된 비밀번호

//        if (!rawUserPassword.equals(encodedUserPassword)) {
         if (!encoder.matches(rawUserPassword, encodedUserPassword)) {
            throw new LoginException(INVALID_PASSWORD, rawUserPassword);
        }

        // 이메일, 비밀번호 검증을 통과 시, 로그인 성공 로그 출력
        log.info("Login success user: {}", user.getUserName());

        // 로그인 성공 -> 클라이언트에게 JWT 발급
        String token = tokenProvider.createToken(user);

        return new LoginResponseDTO(user, token);

    }

    /**
     * 로그인 처리 및 검증
     * @param userEmail - 입력받은 이메일
     * @param rawPassword - 입력받은 패스워드
     * @return 로그인 성공시 로그인 응
     */
    public LoginResponseDTO getByCredentials(
            final String userEmail,
            final String rawPassword
    ) {

        log.info("[Login/Service] 로그인 처리 및 검증 - email : {}, password : {}", userEmail, rawPassword);

        User originalUser = userRepository.findByUserEmail(userEmail).orElseThrow(() -> {
            log.warn("EMAIL MATCH ERROR");
            throw new LoginException(NOT_FOUND_USER_BY_EMAIL, userEmail);
        });

        System.out.println("originalUser = " + originalUser);

//        if (!rawPassword.equals(originalUser.getUserPassword())) {
        if (!encoder.matches(rawPassword, originalUser.getUserPassword())) {
            log.warn("PASSWORD MATCH ERROR");
            throw new LoginException(INVALID_PASSWORD, rawPassword);
        }

        log.info("{} LOGIN SUCCESS", originalUser.getUserNickname());

        // 토큰 발급
        String token = tokenProvider.createToken(originalUser);

        log.info("{}'s TOKEN : {}", originalUser.getUserNickname(), token);

        return new LoginResponseDTO(originalUser, token);

    }
}
