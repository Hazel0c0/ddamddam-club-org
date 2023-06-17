package kr.co.ddamddam.user.api;

import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;

import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.exception.DuplicatedEmailException;
import kr.co.ddamddam.user.exception.NoRegisteredArgumentsException;
import kr.co.ddamddam.user.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 회원가입, 로그인 요청을 처리하는 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/auth")
public class UserApiController {

//    private final UserService userService;
    private final UserSignUpService userSingUpService;

//    @PostMapping("/signin")
//    public ApplicationResponse<?> signIn(
//            @Validated @RequestBody LoginRequestDTO dto
//    ) {
//        LoginResponseDTO responseDTO = userService.authenticate(dto);
//
//        return ApplicationResponse.ok(responseDTO);
//    }


    // 이메일 중복확인 요청 처리
    // GET: /api/auth/check?email=zzzz@xxx.com
    @GetMapping("/check")
    public ResponseEntity<?> check(String email) {

        if (email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("이메일이 없습니다!");
        }
        boolean resultFlag = userSingUpService.isDuplicate(email);
        log.info("{} 중복?? - {}", email, resultFlag);

        return ResponseEntity.ok().body(resultFlag);
    }

    // 회원가입 요청처리
    // POST: /api/signup
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Validated @RequestBody UserRequestSignUpDTO dto
            , BindingResult result
    ) {
        //값이 잘 들어오는지 확인
        log.info("/api/auth POST! - {}", dto);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        try {
            UserSignUpResponseDTO responseDTO = userSingUpService.create(dto);
            return ResponseEntity.ok().body(responseDTO);
        } catch (NoRegisteredArgumentsException e) {
            log.warn("필수 가입 정보를 전달받지 못했습니다.");
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DuplicatedEmailException e) {
            log.warn("이메일 중복입니다!");
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
