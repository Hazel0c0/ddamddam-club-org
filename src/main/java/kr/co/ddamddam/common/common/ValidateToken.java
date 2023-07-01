package kr.co.ddamddam.common.common;

import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.common.exception.custom.UnauthorizationException;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateToken {

    private final UserRepository userRepository;

    /**
     * 클라이언트에서 보낸 토큰만 검사하는 메서드
     *
     * @param tokenUserInfo - 유저 정보 (유저 식별번호, 유저 이메일, 토큰)
     */
    public User validateToken(TokenUserInfo tokenUserInfo) {
        // 토큰 인증 실패
        if (tokenUserInfo == null) {
            throw new UnauthorizationException(UNAUTHENTICATED_USER, "로그인 후 이용 가능합니다.");
        }

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        // 회원 조회 실패
        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundUserException(NOT_FOUND_USER, userIdx);
        });

        return user;

    }

}

