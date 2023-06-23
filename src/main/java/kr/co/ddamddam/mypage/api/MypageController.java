package kr.co.ddamddam.mypage.api;

import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mypage.dto.MypageProjectPageResponseDTO;
import kr.co.ddamddam.mypage.dto.page.MyPagePageDTO;
import kr.co.ddamddam.mypage.dto.request.MypageModifyRequestDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageChatPageResponseDTO;
import kr.co.ddamddam.mypage.service.MypageService;
import kr.co.ddamddam.upload.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", tokenUserInfo);
//        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", pageDTO);

        MypageBoardPageResponseDTO boardList = myPageService.getBoardList(tokenUserInfo, myPagePageDTO);

        return ResponseEntity.ok().body(boardList);
    }

    // 채팅방 목록 조회
    @GetMapping("/chat-list")
    public ResponseEntity<?> getChatList(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            MyPagePageDTO myPagePageDTO
    ){
        log.info("chattttttttttt {}", tokenUserInfo);
        // {?page=1&size=3}
        MypageChatPageResponseDTO chatRoomList = myPageService.getChatList(tokenUserInfo, myPagePageDTO);

        log.info("chatRoomList!!!!! {}", chatRoomList);

        return ResponseEntity.ok().body(chatRoomList);
    }


    @PostMapping("/modify")
    public ResponseEntity<?> modify(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @RequestBody MypageModifyRequestDTO dto
    ){


        log.info("modify: {}",dto);

        myPageService.myPageModify(dto, tokenUserInfo);

        return ResponseEntity.ok().body("회원정보 수정완료!");
    }

    @GetMapping("/project-list")
    public ResponseEntity<?> getProjectList(
        @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
        MyPagePageDTO myPagePageDTO
    ) {
        log.info("/api/ddamddam/page={}$size={}", myPagePageDTO.getPage(), myPagePageDTO.getSize());

        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        log.info("mypage - userIdx {} ", userIdx);

        MypageProjectPageResponseDTO arrayProjectList = myPageService.getArrayProjectList(userIdx, myPagePageDTO);

        System.out.println("myProjectList = " + arrayProjectList);

        return ResponseEntity.ok().body(arrayProjectList);
    }
}
