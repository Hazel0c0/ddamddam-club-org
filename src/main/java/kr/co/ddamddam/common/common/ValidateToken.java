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
     * @param tokenUserInfo - 유저 정보 (유저 식별번호, 유저 이메일, 토큰)
     */
    public void validateToken(TokenUserInfo tokenUserInfo) {
        // 토큰 인증 실패
        if (tokenUserInfo == null) {
            throw new UnauthorizationException(UNAUTHENTICATED_USER, "로그인 후 이용 가능합니다.");
        }
    }

    // TODO : 작성자 일치 검증은 각자 서비스에 만들어서 사용해야함
//    /**
//     * 클라이언트에서 보낸 dto 내의 userIdx 와 토큰을 검사하는 메서드
//     * @param userIdx - dto 에 들어있던 유저 식별번호
//     * @param tokenUserInfo - 유저 정보 (유저 식별번호, 유저 이메일, 토큰)
//     */
//    public void validateDtoAndToken(Long userIdx, TokenUserInfo tokenUserInfo) {
//        // 토큰 인증 실패
//        if (tokenUserInfo == null) {
//            throw new UnauthorizationException(UNAUTHENTICATED_USER, "로그인 후 이용 가능합니다.");
//        }
//        // 회원 조회 실패
//        User user = userRepository.findById(userIdx).orElseThrow(() -> {
//            throw new NotFoundUserException(NOT_FOUND_USER, userIdx);
//        });
//        // 토큰 내 회원 이메일과 작성자의 이메일이 일치하지 않음 -> 작성자가 아님 -> 수정 및 삭제 불가
//        if (!user.getUserEmail().equals(tokenUserInfo.getUserEmail())) {
//            throw new UnauthorizationException(ACCESS_FORBIDDEN, user.getUserEmail());
//        }
//    }

}
