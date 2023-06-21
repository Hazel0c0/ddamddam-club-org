package kr.co.ddamddam.user.api;

import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.user.dto.request.UserRequestSignUpDTO;

import kr.co.ddamddam.user.dto.response.UserSignUpResponseDTO;
import kr.co.ddamddam.user.exception.DuplicatedEmailException;
import kr.co.ddamddam.user.exception.NoRegisteredArgumentsException;
import kr.co.ddamddam.user.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * TODO: 회원가입 요청을 처리하는 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/auth")
public class UserApiController {

    private final UserSignUpService userSingUpService;


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
            @Validated @RequestPart("user") UserRequestSignUpDTO dto
            , @RequestPart(value = "profileImage", required = false) MultipartFile profileImg
            , BindingResult result
    ) {
        //값이 잘 들어오는지 확인
        log.info("/api/auth POST! - {}", dto);
        log.info("attached file name; {}", profileImg.getOriginalFilename());

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        try {

            String uploadedFilePath = null;
            if (profileImg != null) {
                log.info("attached file name: {}", profileImg.getOriginalFilename());
                uploadedFilePath = userSingUpService.uploadProfileImage(profileImg);
            }

            UserSignUpResponseDTO responseDTO = userSingUpService.create(dto,uploadedFilePath);
            return ResponseEntity.ok().body(responseDTO);

        } catch (NoRegisteredArgumentsException e) {
            log.warn("필수 가입 정보를 전달받지 못했습니다.");
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DuplicatedEmailException e) {
            log.warn("이메일 중복입니다!");
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            log.warn("기타 예외가 발생했습니다."); //파일업로드 에러처리
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }


    // 프로필 사진 이미지 데이터를 클라이언트에게 응답처리
    @GetMapping("/load-profile")
    public ResponseEntity<?> loadFile(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        log.info("/api/auth/load-profile GET ! - user: {}", userInfo.getUserEmail());

        try {
            // 클라이언트가 요청한 프로필 사진을 응답해야 함
            // 1. 프로필 사진의 경로를 얻어야 함.
            String filePath
                    = userSingUpService.getProfilePath(Long.valueOf(userInfo.getUserIdx()));

            // 2. 얻어낸 파일 경로를 통해서 실제 파일데이터 로드하기
            File profileFile = new File(filePath);

            if (!profileFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 해당 경로에 저장된 파일을 바이트배열로 직렬화해서 리턴
            byte[] fileData = FileCopyUtils.copyToByteArray(profileFile);

            // 3. 응답 헤더에 컨텐츠 타입을 설정
            HttpHeaders headers = new HttpHeaders();
            MediaType contentType = findExtensionAndGetMediaType(filePath);
            if (contentType == null) {
                return ResponseEntity.internalServerError()
                        .body("발견된 파일은 이미지 파일이 아닙니다.");
            }
            headers.setContentType(contentType);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("파일을 찾을 수 없습니다.");
        }

    }

    private MediaType findExtensionAndGetMediaType(String filePath) {

        // 파일경로에서 확장자 추출하기
        // D:/todo_upload/kfdslfjhsdkjhf_abc.jpg
        String ext
                = filePath.substring(filePath.lastIndexOf(".") + 1);
        //. 다음글자 부터 끝까지 자르기

        switch (ext.toUpperCase()) {
            case "JPG":
            case "JPEG":
                return MediaType.IMAGE_JPEG;
            case "PNG":
                return MediaType.IMAGE_PNG;
            case "GIF":
                return MediaType.IMAGE_GIF;
            default:
                return null;
        }

    }

    //s3에서 불러온 프로필 사진 처리
    @GetMapping("/load-s3")
    public ResponseEntity<?> loads3(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        log.info("/api/auth/load-s3 GET - user: {}", userInfo);
        try {
            String profilePath = userSingUpService.getProfilePath(Long.valueOf(userInfo.getUserIdx()));
            return ResponseEntity.ok().body(profilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }



}
