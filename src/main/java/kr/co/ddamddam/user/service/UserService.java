package kr.co.ddamddam.user.service;

import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;
import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.exception.DuplicatedEmailException;
import kr.co.ddamddam.user.exception.NoRegisteredArgumentsException;
import kr.co.ddamddam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
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
@Transactional
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

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final AtomicReference<JavaMailSender> emailSender = new AtomicReference<JavaMailSender>();

    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;


    private String authNum; //랜덤 인증 코드

    //회원가입 처리
    public UserSignUpResponseDTO create(final UserRequestSignUpDTO dto)
            throws RuntimeException {

        if (dto == null) {
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다.");
        }
        String email = dto.getUserEmail();

        if (userRepository.existsByEmail(dto.getUserEmail())) {
            log.warn("이메일이 중복되었습니다. - {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }

        // 패스워드 인코딩(유저 엔티티로 변환하기전에 해야할일 !)
        String encoded = encoder.encode(dto.getUserPw());
        dto.setUserPw(encoded);

        // 유저 엔터티로 변환
        User user = dto.toEntity();
        //엔티티를 저장하고 리턴값 반환
        User saved = userRepository.save(user);

        log.info("회원가입 정상 수행됨! - saved user - {}", saved);


        return new UserSignUpResponseDTO(saved);

    }


    public boolean isDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }




    //랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {

        createCode(); //인증 코드 생성
        String setFrom = "yellowyj39@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "ddamddam 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.get().createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.get().send(emailForm);

        return authNum; //인증 코드 반환
    }

    //타임리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

}
