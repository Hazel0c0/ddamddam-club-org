package kr.co.ddamddam.user.service;

import kr.co.ddamddam.config.security.TokenProvider;
import kr.co.ddamddam.common.exception.custom.LoginException;
import kr.co.ddamddam.user.dto.request.LoginRequestDTO;
import kr.co.ddamddam.user.dto.response.LoginResponseDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

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

        // TODO : 비밀번호 암호화 처리 후 matches 로 변경
        // if (!encoder.matches(rawUserPassword, encodedUserPassword)) {
        if (!rawUserPassword.equals(encodedUserPassword)) {
            throw new LoginException(INVALID_PASSWORD, rawUserPassword);
        }

        // 이메일, 비밀번호 검증을 통과 시, 로그인 성공 로그 출력
        log.info("Login success user: {}", user.getUserName());

        // 로그인 성공 -> 클라이언트에게 JWT 발급
        String token = tokenProvider.createToken(user);

        return new LoginResponseDTO(user, token);

    }
}
