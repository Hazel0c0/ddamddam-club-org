package kr.co.ddamddam.useremail.controller;

import kr.co.ddamddam.useremail.dto.UserEmailCheckRequestDTO;
import kr.co.ddamddam.useremail.dto.response.UserCodeCheckResponseDTO;
import kr.co.ddamddam.useremail.dto.request.UserCodeRequestDTO;
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

    @PostMapping("/send")
    public ResponseEntity<UserCodeRequestDTO> EmailCheck(@Valid @RequestBody UserEmailCheckRequestDTO emailCheckReq) throws MessagingException, UnsupportedEncodingException {
        UserCodeRequestDTO userCodeRequestDTO = emailService.sendEmail(emailCheckReq.getEmail());
        return ResponseEntity.ok().body/**/(userCodeRequestDTO);
    }

    @PostMapping("/check")
    public ResponseEntity<?> codeCheck(@RequestBody UserCodeRequestDTO dto) {
        UserCodeCheckResponseDTO memberCodeCheckResponseDTO = emailService.checkCode(dto.getCode());

        return ResponseEntity.ok().body(memberCodeCheckResponseDTO.getCheckResult());
    }
}
