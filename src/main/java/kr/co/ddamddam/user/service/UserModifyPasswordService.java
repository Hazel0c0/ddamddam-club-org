package kr.co.ddamddam.user.service;

import kr.co.ddamddam.common.common.ValidateToken;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.user.dto.request.UserPasswordRequestDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;
import static kr.co.ddamddam.common.response.ResponseMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserModifyPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ValidateToken validateToken;

    public ResponseMessage modifyPassword(
            TokenUserInfo tokenUserInfo,
            UserPasswordRequestDTO requestDTO
    ) {
        log.info("[UserModifyPasswordService] modifyPassword request, userIdx : {}", tokenUserInfo.getUserIdx());

        validateToken.validateToken(tokenUserInfo);

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        User user = userRepository.findById(userIdx).orElseThrow(() -> {
            throw new NotFoundUserException(NOT_FOUND_USER, userIdx);
        });

        // 패스워드 인코딩
        String encodedNewPassword = encoder.encode(requestDTO.getNewUserPassword());

        // 입력한 새 비밀번호가 현재 비밀번호와 같은지 검증
        if (user.getUserPassword().equals(encodedNewPassword)) {
            return FAIL;
        }

        user.setUserPassword(encodedNewPassword);

        User saved = userRepository.save(user);

        log.info("[UserModifyPasswordService] modifyPassword request SUCCESS");

        return SUCCESS;
    }



}
