package kr.co.ddamddam.user.controller;


import kr.co.ddamddam.user.dto.EmailCheckDTO;
import kr.co.ddamddam.user.dto.request.EmailAuthRequestDto;
import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;
import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.exception.DuplicatedEmailException;
import kr.co.ddamddam.user.exception.NoRegisteredArgumentsException;
import kr.co.ddamddam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/signup")
public class UserController {

    private final UserService userService;

    // 이메일 중복확인 요청 처리
    // GET: /api/auth/check?email=zzzz@xxx.com
    @GetMapping("/check")
    public ResponseEntity<?> check(String email) {
        if (email.trim().equals("")) {
            return ResponseEntity.badRequest()
                    .body("이메일이 없습니다!");
        }
        boolean resultFlag = userService.isDuplicate(email);
        log.info("{} 중복?? - {}", email, resultFlag);

        return ResponseEntity.ok().body(resultFlag);
    }

    // 회원가입 요청처리
    // POST: /api/signup
    @PostMapping
    public ResponseEntity<?> signUp(
            @Validated @RequestBody UserRequestSignUpDTO dto
            , BindingResult result
    ) {
        log.info("/api/auth POST! - {}", dto);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        try {
            UserSignUpResponseDTO responseDTO = userService.create(dto);
            return ResponseEntity.ok()
                    .body(responseDTO);
        } catch (NoRegisteredArgumentsException e) {
            log.warn("필수 가입 정보를 전달받지 못했습니다.");
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        } catch (DuplicatedEmailException e) {
            log.warn("이메일 중복입니다!");
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

    }

    //이메일 인증
        @PostMapping("/mail")
        public String mailConfirm(@RequestBody EmailAuthRequestDto emailDto) throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {

            String authCode = userService.sendEmail(emailDto.getEmail());
            return authCode;
        }


}
