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
public class ValidateTokenUserInfo {

    private final UserRepository userRepository;

    public void validate(Long userIdx, TokenUserInfo tokenUserInfo) {
        // 토큰 인증 실패
        if (tokenUserInfo == null) {
            throw new UnauthorizationException(UNAUTHENTICATED_USER, "❌ 인증되지 않은 사용자이거나, 위조된 토큰입니다.");
        }
        // 회원 조회 실패
        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundUserException(NOT_FOUND_USER, userIdx);
        });
        // 토큰 내 회원 이메일과 작성자의 이메일이 일치하지 않음 -> 작성자가 아님 -> 수정 및 삭제 불가
        if (!user.getUserEmail().equals(tokenUserInfo.getUserEmail())) {
            throw new UnauthorizationException(ACCESS_FORBIDDEN, user.getUserEmail());
        }
    }

}
