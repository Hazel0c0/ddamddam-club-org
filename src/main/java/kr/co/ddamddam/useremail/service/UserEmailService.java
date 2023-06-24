package kr.co.ddamddam.useremail.service;

import kr.co.ddamddam.common.exception.custom.MessageException;
import kr.co.ddamddam.useremail.dto.response.UserCodeCheckResponseDTO;
import kr.co.ddamddam.useremail.dto.request.UserCodeRequestDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import static kr.co.ddamddam.common.exception.custom.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserEmailService {

    private final JavaMailSender emailSender;

    private String authNum; // 인증 번호

    // 인증번호 8자리 무작위 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);

            switch (idx) {
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

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException {
        createCode();
        String setFrom = "Connect-Dots";
        String toEmail = email;
        String title = "DDAMDDAM CLUB 회원 가입 인증 코드입니다.";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        // 메일 내용
        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 DDAMDDAM CLUB 입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 코드를 입력해주세요<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>감사합니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += authNum + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    // 비밀번호 찾기 시 임시비밀번호 발급 메일 전송
    public MimeMessage createEmailFormByFindPassword(
            String temporaryPassword, String email
    ) throws MessagingException, UnsupportedEncodingException {

        String setFromName = "DDAMDDAM CLUB";
        String setFromEmail = "yellowyj39@gmail.com";

//        String setFrom = "yellowyj39@gmail.com";
        String title = "DDAMDDAM CLUB 임시비밀번호가 발급되었습니다.";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title);

        // 메일 내용
        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 DDAMDDAM CLUB 입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>회원님의 임시 비밀번호가 발급되었습니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>임시 비밀번호를 통해 로그인하신 후, 비밀번호를 변경해주시길 바랍니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>감사합니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>임시 비밀번호</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += temporaryPassword + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        InternetAddress fromAddress = new InternetAddress(setFromEmail, setFromName);

        message.setFrom(fromAddress);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }



    //실제 메일 전송
    public UserCodeRequestDTO sendEmail(String email) throws MessagingException {
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(email);
        //실제 메일 전송
        emailSender.send(emailForm);

        return UserCodeRequestDTO.builder()
                .code(authNum)
                .build(); //인증 코드 반환
    }
    
//    // 비밀번호 찾기 요청 시 인증코드 메일을 전송
//    public UserCodeResponseDTO sendEmailByFindPassword(String email) {
//        //메일전송에 필요한 정보 설정
//        try {
//            MimeMessage emailForm = createEmailFormByFindPassword(email);
//            //실제 메일 전송
//            emailSender.send(emailForm);
//        } catch (MessagingException e) {
//            throw new MessageException(MESSAGE_SEND_ERROR, email);
//        }
//
//        return UserCodeResponseDTO.builder()
//                .code(authNum)
//                .build(); //인증 코드 반환
//    }


    public UserCodeCheckResponseDTO checkCode(String code) {
        return UserCodeCheckResponseDTO.builder()
                .checkResult(authNum.equals(code))
                .build();
    }

}
