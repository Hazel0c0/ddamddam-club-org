package kr.co.ddamddam.login.service;

import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@DynamicInsert // 변경되지 않은 컬럼은 동적 SQL 쿼리에 포함되지 않게 해주는 역할
public class LoginService {

    private final UserRepository userRepository;

}
