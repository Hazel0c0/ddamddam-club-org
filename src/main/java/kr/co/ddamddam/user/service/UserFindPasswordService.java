package kr.co.ddamddam.user.service;

import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.user.dto.request.UserFindPasswordRequestDTO;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kr.co.ddamddam.common.response.ResponseMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFindPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public ResponseMessage findPassword(UserFindPasswordRequestDTO requestDTO) {

        return SUCCESS;
    }
}
