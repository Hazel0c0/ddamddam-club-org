package kr.co.ddamddam.user.service;

import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.MessageException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserByEmailException;
import kr.co.ddamddam.common.exception.custom.NotFoundUserException;
import kr.co.ddamddam.common.response.ResponseMessage;
import kr.co.ddamddam.user.dto.request.UserFindPasswordRequestDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.repository.UserRepository;
import kr.co.ddamddam.useremail.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;
import static kr.co.ddamddam.common.response.ResponseMessage.*;
import static kr.co.ddamddam.user.service.PasswordGenerator.generateRandomPassword;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFindPasswordService {

    private final UserRepository userRepository;
    private final UserEmailService emailService;
    private final JavaMailSender emailSender;
    private final PasswordEncoder encoder;
    private String temporaryPassword; // 임시비밀번호

    public ResponseMessage findPassword(
            final UserFindPasswordRequestDTO requestDTO
    ) {
        log.info("[UserFindPasswordService] findPassword, dto : {}", requestDTO);

        Optional<User> foundUser = userRepository.findByUserEmail(requestDTO.getUserEmail());

        // 1차 검증
        if (foundUser.isEmpty()) return FAIL;

        // 2차 검증
        if (!foundUser.get().getUserName().equals(requestDTO.getUserName())) return FAIL;

        User user = foundUser.get();

        // 임시 비밀번호 생성
        temporaryPassword = generateRandomPassword();
        
        // 메일 생성 및 발송
        sendEmail(user.getUserEmail(), temporaryPassword);

        // 회원의 현재 비밀번호를 암호화된 임시 비밀번호로 변경
        user.setUserPassword(encoder.encode(temporaryPassword));
        userRepository.save(user);

        return SUCCESS;
    }

    /**
     * 임시 비밀번호가 작성된 이메일을 발송합니다.
     * @param userEmail - 수신자
     * @param temporaryPassword - 임시비밀번호
     */
    private void sendEmail(String userEmail, String temporaryPassword) {
        try {
            log.info("[UserFindPasswordService] sendEmail, userEmail : {}, temporaryPassword : {}", userEmail, temporaryPassword);

            // 임시비밀번호 메일 작성
            MimeMessage emailForm = emailService.createEmailFormByFindPassword(temporaryPassword, userEmail);
            // 메일 발송
            emailSender.send(emailForm);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace(); // TODO : 테스트 후 삭제
            throw new MessageException(MESSAGE_SEND_ERROR, userEmail);
        }
    }

}
