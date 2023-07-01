package kr.co.ddamddam.mypage.api;

import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mypage.dto.MypageProjectPageResponseDTO;
import kr.co.ddamddam.mypage.dto.page.MyPagePageDTO;
import kr.co.ddamddam.mypage.dto.request.MypageModifyRequestDTO;
import kr.co.ddamddam.mypage.dto.response.MypageAfterModifyResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageChatPageResponseDTO;
import kr.co.ddamddam.mypage.service.MypageService;
import kr.co.ddamddam.upload.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/mypage")
public class MypageController {

    private final MypageService myPageService;
    private final UploadService uploadService;

    @GetMapping("/board-list")
    public ResponseEntity<?> getBoardList(
        @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
        MyPagePageDTO myPagePageDTO
    ) {
//        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", tokenUserInfo);

        MypageBoardPageResponseDTO boardList = myPageService.getBoardList(tokenUserInfo, myPagePageDTO);

        return ResponseEntity.ok().body(boardList);
    }

    // 채팅방 목록 조회
    @GetMapping("/chat-list")
    public ResponseEntity<?> getChatList(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            MyPagePageDTO myPagePageDTO
    ){
//        log.info("chat {}", tokenUserInfo);

        MypageChatPageResponseDTO chatRoomList = myPageService.getChatList(tokenUserInfo, myPagePageDTO);

        return ResponseEntity.ok().body(chatRoomList);
    }


    @PostMapping("/modify")
    public ResponseEntity<?> modify(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @Validated @RequestPart("user") MypageModifyRequestDTO dto
            , @RequestPart(value = "profileImage", required = false) MultipartFile profileImg
            ,BindingResult result
    ) throws IOException {
//        log.info("수정요청 발생");
//        log.info("modify: {}",dto);
//        log.info("inputImg : {}",profileImg);

        if (result.hasErrors()) {
            log.warn(result.toString());
            return ResponseEntity.badRequest()
                    .body(result.getFieldError());
        }

        String uploadFilePath = null;
        if(profileImg != null) {
            uploadFilePath = myPageService.uploadProfileImage(profileImg);
        }

        myPageService.myPageModify(dto, tokenUserInfo,uploadFilePath);

        return ResponseEntity.ok().body("회원정보 수정완료!");
    }

    //s3에서 불러온 프로필 사진 저장 처리
    @GetMapping("/load-s3")
    public ResponseEntity<?> loads3(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
//        log.info("/api/auth/load-s3 GET - user: {}", userInfo);
        try {
            String profilePath = myPageService.getProfilePath(Long.valueOf(userInfo.getUserIdx()));
            return ResponseEntity.ok().body(profilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 회원정보 수정 성공 후, 수정된 정보만 DB 에서 가져와서 리턴합니다.
     * @param tokenUserInfo - 로그인 한 유저 정보
     * @return 수정된 유저의 일부 정보
     */
    @GetMapping("/after-modify")
    public ResponseEntity<?> afterModify(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo
    ) {
//        log.info("MypageController - /mypage/after-modify, token : {}, userProfile : {}", tokenUserInfo);

        MypageAfterModifyResponseDTO afterModifyUserInfo
                = myPageService.mypageAfterModify(tokenUserInfo);

        return ResponseEntity.ok().body(afterModifyUserInfo);
    }

    @GetMapping("/project-list")
    public ResponseEntity<?> getProjectList(
        @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
        MyPagePageDTO myPagePageDTO
    ) {
//        log.info("/api/ddamddam/page={}$size={}", myPagePageDTO.getPage(), myPagePageDTO.getSize());

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

//        log.info("mypage - userIdx {} ", userIdx);

        MypageProjectPageResponseDTO arrayProjectList = myPageService.getArrayProjectList(userIdx, myPagePageDTO);

//        System.out.println("myProjectList = " + arrayProjectList);

        return ResponseEntity.ok().body(arrayProjectList);
    }
}
