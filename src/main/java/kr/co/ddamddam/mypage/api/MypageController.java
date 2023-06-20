package kr.co.ddamddam.mypage.api;

import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mypage.dto.page.PageDTO;
import kr.co.ddamddam.mypage.dto.request.MypageModifyRequestDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageChatPageResponseDTO;
import kr.co.ddamddam.mypage.service.MypageService;
import kr.co.ddamddam.upload.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            PageDTO pageDTO
    ) {
//        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", tokenUserInfo);

        MypageBoardPageResponseDTO boardList = myPageService.getBoardList(pageDTO);

        return ResponseEntity.ok().body(boardList);
    }

    // 채팅방 목록 조회
    @GetMapping("/chat-list")
    public ResponseEntity<?> getChatList(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            PageDTO pageDTO
    ) {
        MypageChatPageResponseDTO chatRoomList = myPageService.getChatList(pageDTO);

        return ResponseEntity.ok().body(chatRoomList);
    }

    // 회원 정보 수정
    @PostMapping("/modify")
    public ResponseEntity<?> modify(
//            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @RequestBody MypageModifyRequestDTO dto,
            @RequestPart(value = "userProfile", required = false) MultipartFile userProfile
    ) {

        try {
            // 파일 업로드
            String uploadedFilePath = null;
            String boardType = "USER";

            if (userProfile != null) {
                log.info("user file name: {}", userProfile.getOriginalFilename());
                uploadedFilePath = uploadService.uploadFileImage(userProfile, boardType);
            }
            Long userIdx = 44L;

            log.info("modify: {}", dto);

            myPageService.myPageModify(dto, userIdx,uploadedFilePath);

            return ResponseEntity.ok().body("회원정보 수정완료!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
