package kr.co.ddamddam.mypage.api;

import io.swagger.v3.oas.annotations.Parameter;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mypage.dto.page.PageDTO;
import kr.co.ddamddam.mypage.dto.request.MypageModifyRequestDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageChatPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageProjectResponseDTO;
import kr.co.ddamddam.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ddamddam/mypage")
public class MypageController {

    private final MypageService myPageService;

    @GetMapping("/board-list")
    public ResponseEntity<?> getBoardList(
        @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
        PageDTO pageDTO
    ) {
        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", tokenUserInfo);
//        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", pageDTO);

        MypageBoardPageResponseDTO boardList = myPageService.getBoardList(tokenUserInfo, pageDTO);

        return ResponseEntity.ok().body(boardList);
    }

    // 채팅방 목록 조회
    @GetMapping("/chat-list")
    public ResponseEntity<?> getChatList(
            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            PageDTO pageDTO
    ){
        MypageChatPageResponseDTO chatRoomList = myPageService.getChatList(pageDTO);

        return ResponseEntity.ok().body(chatRoomList);
    }

    // 회원 정보 수정
    @PostMapping("/modify")
    public ResponseEntity<?> modify(
//            @AuthenticationPrincipal TokenUserInfo tokenUserInfo,
            @RequestBody MypageModifyRequestDTO dto
    ){

        Long userIdx = 44L;

        log.info("modify: {}",dto);

        myPageService.myPageModify(dto, userIdx);

        return ResponseEntity.ok().body("회원정보 수정완료!");
    }

    @GetMapping("/project-list/{userIdx}")
    public ResponseEntity<?> getProjectList(
//        @AuthenticationPrincipal TokenUserInfo tokenUserInfo
        @PathVariable Long userIdx,
        @RequestParam(required = false) String type
    ) {

        log.info("mypage - userIdx {} ", userIdx);
//        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());

        List<MypageProjectResponseDTO> myProjectList;
        if (type != null && type.equals("arrayProject")) {
            myProjectList = myPageService.getArrayProjectList(userIdx);
        } else {
            myProjectList = myPageService.getProjectList(userIdx);
        }
        System.out.println("myProjectList = " + myProjectList);

        return ResponseEntity.ok().body(myProjectList);
    }
}
