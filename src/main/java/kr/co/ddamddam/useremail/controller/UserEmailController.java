package kr.co.ddamddam.useremail.controller;

import kr.co.ddamddam.useremail.dto.UserEmailCheckRequestDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeCheckResponseDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeResponseDTO;
import kr.co.ddamddam.useremail.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
@RestController
@RequestMapping("/api/ddamddam/email")
@RequiredArgsConstructor
public class UserEmailController {

    private final UserEmailService emailService;

    @PostMapping("/send")        // 이 부분은 각자 바꿔주시면 됩니다.
    public ResponseEntity<UserCodeResponseDTO> EmailCheck(@Valid @RequestBody UserEmailCheckRequestDTO emailCheckReq) throws MessagingException, UnsupportedEncodingException {
        UserCodeResponseDTO memberCodeResponseDTO = emailService.sendEmail(emailCheckReq.getEmail());

        return ResponseEntity.ok().body/**/(memberCodeResponseDTO);    // Response body에 값을 반환해줄게요~
    }

    @PostMapping("/check")
    public ResponseEntity<UserCodeCheckResponseDTO> codeCheck(@RequestBody UserCodeResponseDTO dto) {
        UserCodeCheckResponseDTO memberCodeCheckResponseDTO = emailService.checkCode(dto.getCode());

        return ResponseEntity.ok().body(memberCodeCheckResponseDTO);
    }
}
