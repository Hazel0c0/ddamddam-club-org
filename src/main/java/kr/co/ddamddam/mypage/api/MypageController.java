package kr.co.ddamddam.mypage.api;

import io.swagger.v3.oas.annotations.Parameter;
import kr.co.ddamddam.config.security.TokenUserInfo;
import kr.co.ddamddam.mypage.dto.page.PageDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardPageResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageBoardResponseDTO;
import kr.co.ddamddam.mypage.dto.response.MypageProjectResponseDTO;
import kr.co.ddamddam.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ){
//        log.info("GET : MypageController/getBoardList - tokenUserInfo : {}", tokenUserInfo);

        MypageBoardPageResponseDTO boardList = myPageService.getBoardList(pageDTO);

        return ResponseEntity.ok().body(boardList);
    }
    @GetMapping("/project-list/{userIdx}")
    public ResponseEntity<?> getProjectList(
//        @AuthenticationPrincipal TokenUserInfo tokenUserInfo
        @PathVariable Long userIdx

    ){
//        Long userIdx = Long.valueOf(tokenUserInfo.getUserIdx());
        List<MypageProjectResponseDTO> myProjectList = myPageService.getProjectList(userIdx);

        return ResponseEntity.ok().body(myProjectList);
    }
}
