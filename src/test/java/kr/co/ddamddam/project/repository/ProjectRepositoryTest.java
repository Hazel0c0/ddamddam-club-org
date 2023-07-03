package kr.co.ddamddam.project.repository;

import kr.co.ddamddam.common.exception.custom.ErrorCode;
import kr.co.ddamddam.common.exception.custom.NotFoundBoardException;
import kr.co.ddamddam.project.entity.Project;
import kr.co.ddamddam.user.entity.User;
import kr.co.ddamddam.user.entity.UserPosition;
import kr.co.ddamddam.user.entity.UserRole;
import kr.co.ddamddam.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
class ProjectRepositoryTest {

  @Autowired
  ProjectRepository projectRepository;

  @Autowired
  UserRepository userRepository;
  LocalDateTime currentDateTime = LocalDateTime.now();


  @Test
  @DisplayName("bulk insert")
  void bulkInsert() {
    for (int i = 0; i < 50; i++) {
      int index16 = (int) (Math.random() * 16);
      int index4 = (int) (Math.random() * 4);
      int index3 = (int) (Math.random() * 3);
      int member = (int) (Math.random() * 4 + 1);
      Long randomUser = (long) (Math.random() *2+1);
      int like = (int) (Math.random() * 1000);
      User user = userRepository.findById(randomUser).orElseThrow(() -> {
        throw new NotFoundBoardException(ErrorCode.NOT_FOUND_USER, randomUser);
      });
      int daysToAdd = (int) (Math.random() * 30) + 1;  // 1부터 30까지의 랜덤한 숫자를 선택
      LocalDateTime offerPeriod = currentDateTime.plusDays(daysToAdd);


      List<String> title = new ArrayList<>(
          Arrays.asList(
              "케이크 정보 제공/예약 서비스",
              "강아지 배변 인식기 앱 개발",
              "좌석확인 웹 사이트 제작(시팅나우)",
              "동네모임/데이팅 서비스에 관심있는 팀",
              "1분만에 나와 맞는 친구를 찾아보세요",
              "[경기도] 원데이 클랜의 클랜원을 모집합니다",
              "[서울] 애니메이션 종사자 팀빌딩 서비스",
              "3차원 메타버스 개",
              "통합 향수 플랫폼 프로젝트 나네의 IOS/Android 프론트 개발자를 구인합니다.",
              "(리엑트 네이티브 모집중) 대동빵지도",
              "[서울] 새로운 위치기반 소셜 HERE",
              "[서울] 모임 관리 및 프리미엄 모임 서비스",
              "소비자는 물건을 '파는'사람이다.",
              "노션 연동 블로그 플랫폼",
              "[서울] (iOS 급구)반려견 미용 중개 앱",
              "[MVP 완료] 포트폴리오 공유 서비스"));

      List<String> projectImg = new ArrayList<>(
          Arrays.asList(
              "https://letspl.s3.ap-northeast-2.amazonaws.com/user/7851/images/cake_logo.jpeg", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/5531/images/%E1%84%90%E1%85%A1%E1%86%AB%E1%84%8B%E1%85%B5%20%E1%84%89%E1%85%A1%E1%84%8C%E1%85%B5%E1%86%AB.jpeg", "https://letspl.s3.ap-northeast-2.amazonaws.com/images/project_thumb_03.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/2156/images/Frame%205612.jpg", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/3616/images/1.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/3543/images/%EB%A0%9B%ED%94%8C%20%EA%B3%B5%EA%B3%A0%20%EC%9D%B4%EB%AF%B8%EC%A7%80.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/images/project_thumb_05.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/5885/images/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-05-17%20%EC%98%A4%ED%9B%84%204.12.24.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/images/project_thumb_09.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/1/images/questThumb_1.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/5475/images/Frame%2049.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/1591/images/%E1%84%85%E1%85%A2%E1%86%BA%E1%84%91%E1%85%B3%E1%86%AF%E1%84%8B%E1%85%AD%E1%86%BC%20%E1%84%8F%E1%85%A5%E1%84%87%E1%85%A5.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/4474/images/%EB%AA%A8%EC%9D%B4%EB%B0%8D%20%EA%B7%B8%EB%A6%BC.jpg", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/5885/images/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-05-17%20%EC%98%A4%ED%9B%84%204.12.24.png", "https://letspl.s3.ap-northeast-2.amazonaws.com/user/1363/images/KakaoTalk_Photo_2022-09-18-10-53-53.jpeg", "https://letspl.s3.ap-northeast-2.amazonaws.com/images/project_thumb_06.png"));

      List<String> projectType = new ArrayList<>(
          Arrays.asList("Mobile Web", "Web App", "Native App", "Native App")
      );

      List<String> content = new ArrayList<>(
          Arrays.asList(
              "1. 프로젝트의 시작 동기\n" +
                  " \n" +
                  "\n" +
                  "일기, To-do list 관리 같은 기록 서비스로 감정을 정리하고 하루를 되돌아보는 사람들이 있습니다.\n" +
                  "\n" +
                  "특히 시중에 많은 일기장 앱이 있지만 하루를 기록하는 행위 외엔 별 다른 사용자 경험을 주지 않고 있어서 오랜 기간 동안 지속적으로 사용하는 경우가 많이 없었습니다.\n" +
                  "\n" +
                  "저희 팀은 하루를 기록하는 일기장 서비스의 취지에 맞는 차별화 경험을 제공해 사용자들이 매일 일기를 쓰는 서비스를 만들자는 목표를 이루기 위해\n" +
                  "\n" +
                  "런칭된 하루냥 앱을 운영&고도화 하고 있습니다.\n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  "사용자가 선택한 날씨, 감정, 일기 내용에 맞는 위로&공감의 한 마디를 ChatGPT를 활용하여 사용자에게 제공해주는 일기장 서비스입니다.\n" +
                  "\n" +
                  "향후 사용자 감정 변화 통계, 커뮤니티 기능을 업데이트할 계획을 갖고 있습니다.\n" +
                  "\n" +
                  "현재 2.0 버전 런칭 예정이며, 이후 장기적으로 지속적으로 운영 및 고도화 예정되어 있습니다. 사용자 풀 확보 후 사업화 또한 염두에 두고 있습니다.\n" +
                  "\n" +
                  "플레이스토어 링크 : https://play.google.com/store/apps/details?id=com.beside04.haruNyang\n" +
                  "\n" +
                  "앱스토어 링크 : https://apps.apple.com/us/app/%ED%95%98%EB%A3%A8%EB%83%A5/id6444657575\n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  "주 사용자 : 24세 미 / 여자(및 남자) / 학생\n" +
                  "\n" +
                  "부 사용자 : 25-32 / 여자(및 남자) / 직장인\n" +
                  "\n" +
                  "고민을 털어놓거나 하루를 정리하고 마음을 다잡고 싶어하는 학생 및 직장인을 대상으로 합니다.\n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  "2. 회의 진행/모임 방식 \n" +
                  " \n" +
                  "\n" +
                  "정기회의 : 1주 1회 (수요일 오후 8시 30분 ~ 9시 30분에서 10시쯤 종료)\n" +
                  "\n" +
                  "큰 업데이트 전후로 오프라인 워크샵을 진행할 수도 있습니다 (서울에서 주말 하루 진행)\n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  "정규 온라인 회의는 구글미트에서 진행합니다.\n" +
                  "\n" +
                  "커뮤니케이션은 Slack, 문서 아카이빙은 Notion을 활용합니다.\n" +
                  "\n" +
                  "기획 SB, 디자인 커뮤니케이션은 Figma를 활용합니다.\n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  "3. 현재 팀원 구성\n" +
                  "기획 : 2명 (PM 1명, 기획자 1명)\n" +
                  "\n" +
                  "디자인 : 2명\n" +
                  "\n" +
                  "FE : 1명\n" +
                  "\n" +
                  "BE : 2명\n" +
                  "\n" +
                  "4. 기술 스택\n" +
                  "FE : Flutter\n" +
                  "\n" +
                  "BE : Kotlin, Spring boot, JPA, spring security, Mysql\n" +
                  "\n" +
                  "5. 팀원 합류 이후 경험할 수 있는 것들\n" +
                  "- 하루냥 프론트 구축 및 출시 경험\n" +
                  "\n" +
                  "- 출시 이후 사용자 데이터 기반으로 기능 고도화 및 성장 경험\n" +
                  "\n" +
                  "- 자유롭게 의견 제안, 수용하는 분위기 속에서 사용하고 싶은 기술 스택 사용\n" +
                  "\n" +
                  "- 사용자 친화적인 서비스 개발 가능\n" +
                  "\n" +
                  "- 경쟁력 있는 포트폴리오 제작\n" +
                  "\n" +
                  "6. 그외 자유기재 \n" +
                  " \n" +
                  "\n" +
                  "저희 팀은 개인의 능력 향상 이외에도 하루냥 앱의 성공을 위해 진심으로 프로젝트에 임하고 있습니다.\n" +
                  "\n" +
                  "제가 이제껏 경험한 모든 프로젝트 중에서 사용자의 VOC를 통해 서비스를 고도화하며 얻는 오너십과 뿌듯함이 가장 큰 프로젝트입니다.\n" +
                  "\n" +
                  "그만큼 오너십과 뿌듯함은 강하게 보장할 수 있습니다.\n" +
                  "\n" +
                  "따라서 짧게 합류하실 분 보다는 오랫동안 프로젝트에 참여가 가능하신 분을 우선적으로 찾고 있습니다.\n" +
                  "\n" +
                  "하루냥은 단발성 프로젝트가 아닌, 학생 분들이 매일 일기장을 쓰는 서비스로 키워나가고자 하는 목표가 있는 장기적 프로젝트입니다.\n" +
                  "\n" +
                  "플러터 개발자분들의 많은 지원 바랍니다. 읽어주셔서 감사합니다! \n" +
                  "\n",
              "※ iOS 개발자 1분을 모집 중입니다.\n" +
                  "\n" +
                  "※ 현재 본업이 여유롭고 사이드프로젝트 참여 안 하신 분만 지원 부탁드립니다.\n" +
                  "\n" +
                  "1. 프로젝트의 시작 동기\n" +
                  "트립북은 내 손안의 여행북이라는 컨셉으로\n" +
                  "\n" +
                  "언제, 어디에, 누구와 다녀왔는지 여행기를 차곡차곡 기록하는 서비스로,\n" +
                  "\n" +
                  "바쁜 일상 속 틈틈이 여행을 다녀오는 2535 남녀 타겟 대상으로\n" +
                  "\n" +
                  "잊어버리기 쉬운 여행 추억을 한눈에 살펴볼 수 있으면 좋겠다는 의도로 탄생했습니다.\n" +
                  "\n" +
                  "2. 프로젝트 목적\n" +
                  "서비스 배포 후 운영 예정입니다.\n" +
                  "\n" +
                  "3. 서비스 범위\n" +
                  "- WEB(모바일웹)\n" +
                  "\n" +
                  "- APP(안드로이드)\n" +
                  "\n" +
                  "- APP(iOS)\n" +
                  "\n" +
                  "※ 참여 인원에 따라, 서비스 범위 변경될 수 있습니다.\n" +
                  "\n" +
                  "4. 프로젝트 기간\n" +
                  "기획 초기 단계 재정비 중으로,\n" +
                  "\n" +
                  "4월부터 조금씩 진행중인 팀으로,\n" +
                  "\n" +
                  "2023년 내로 프로토타입 완성 목표로 합니다.\n" +
                  "\n" +
                  "(기간은 변경될 수 있으나, 올해 안으로 출시 목표)\n" +
                  "\n" +
                  "5. 회의 진행/모임 방식\n" +
                  "매주 일요일 오후 9시\n" +
                  "\n" +
                  "온라인으로 진행되고 있지만\n" +
                  "\n" +
                  "필요에 따라 오프라인으로 만날 예정입니다.\n" +
                  "\n" +
                  "*활용 채널\n" +
                  "\n" +
                  "- 디스코드(소통) / 깃허브(팀별 히스토리 관리) / 노션(전체 일정 관리) / 피그마+구글 문서(기획 문서)\n" +
                  "\n" +
                  "6. 멤버 : 11명\n" +
                  "- 기획자 겸 마케터(PM): 1명\n" +
                  "\n" +
                  "- 디자이너: 1명\n" +
                  "\n" +
                  "- 프론트엔드 개발자: 3명\n" +
                  "\n" +
                  "- 안드로이드 개발자: 3명\n" +
                  "\n" +
                  "- iOS 개발자: 1명\n" +
                  "\n" +
                  "- 백엔드 개발자: 2명\n" +
                  "\n" +
                  "[선호하는 기술 스텍]\n" +
                  "\n" +
                  "- 디자인: Figma\n" +
                  "\n" +
                  "- 프론트엔드: ReactJS, NextJS, TypeScript\n" +
                  "\n" +
                  "- 안드로이드: Kotlin\n" +
                  "\n" +
                  "- iOS: UI 레이아웃: SwiftUI / 아키텍쳐: MVVM, Combine / 현재 사용 라이브러리: Alamofire, Realm / 기타 사용 툴: Github, Github Actions\n" +
                  "\n" +
                  "- 백엔드: Java Spring\n" +
                  "\n" +
                  "7. 지원 시 요청 사항\n" +
                  "★프로젝트 비용/수익은 참여인원 전원 1/N로 진행합니다★\n" +
                  "\n" +
                  "지원 시 아래 내용 전달 부탁드리며,\n" +
                  "\n" +
                  "구글미트로 간단한 커피챗(화면X) 진행할 예정입니다.\n" +
                  "\n" +
                  "- 성함\n" +
                  "\n" +
                  "- 지원동기\n" +
                  "\n" +
                  "- 기술 스텍\n" +
                  "\n" +
                  "- 직업\n" +
                  "\n" +
                  "- 경력사항(재직사항, 사이드 프로젝트 경혐 여부 등)\n" +
                  "\n" +
                  "- 일주일에 사이드 프로젝트 작업할 수 있는 시간\n" +
                  "\n" +
                  "- 커피챗 가능한 일정\n" +
                  "\n" +
                  "좋은 인연이 되었으면 좋겠습니다 :)",
              " 맛있는 빵이 있는 ‘대동빵지도' " +
                  "우리는 500만 빵순이 빵돌이들이 사랑하는 대한민국 1등 빵전문 서비스를 만드는 팀이에요!\n" +
                  "\n" +
                  "사이드 프로젝트로 진행하고 있으며, 2021 12월 2일 1차 출시 후 앱을 재정비하여 2023.4월 말 Ios, 5월 초 안드로이드 앱을 출시했어요.\n" +
                  "\n" +
                  " 현재 주요 MVP기능 (랭킹, 피드)을 추가로 개발을 하고있어요.\n" +
                  "\n" +
                  "곧 완성될 대동빵지도와 함께 사용자를 모으고, 빵순이들과 사장님께 대체불가능한 서비스를 만들며 수익화를 이끌 대동빵지도의 새로운 주인들을 모시고있어요!\n" +
                  "\n" +
                  "함께 서비스를 성장시킬 PM/PO, 개발자, 디자이너, 마케터를 모집합니다.\n" +
                  "\n" +
                  "1개의 그로스팀과 3개의 제품팀을 운영하고있어요! 다른 사이트 프로젝트에서는 경험할 수 없는 다양한 직군의 사람들과, 사업화를 목표로 재미있는 운영경험을 하실 수 있습니다.\n" +
                  "\n" +
                  "함께 성장하실분!! 많관부입니당~~~!!\n" +
                  "\n" +
                  "현재 리액트 네이티브 개발자 적극 모집중입니다!!\n" +
                  "\n" +
                  "0 개발 예정 기능들\n" +
                  "개발 완료 기능 : 지도, 빵집 검색, 빵집 상세, 리뷰, 프로필, 일부 알림, 관리자 페이지 등\n" +
                  "\n" +
                  "MVP를 위한 추가 기능 : 빵순이 랭킹, 빵집 랭킹, 푸쉬알림 (파이어베이스 등), 검색 고도화, 사장님용 웹 등\n" +
                  "\n" +
                  " 진행방식\n" +
                  " \n" +
                  "\n" +
                  "- 1주일에 1번 정기회의가 있습니다!\n" +
                  "\n" +
                  "- 90%는 온라인으로 게터타운에서 진행합니다.\n" +
                  "\n" +
                  " \n" +
                  "\n" +
                  " 기타 설명\n" +
                  "1.포지션에 대한 자세한 내용은 노션링크에서 보실 수 있습니다.\n" +
                  "\n" +
                  "https://www.notion.so/b3eda5f5b7b446a6ac31c19e8b6353af\n" +
                  "\n" +
                  "* PM/PO (1명 모집)\n" +
                  "\n" +
                  "기존 PM들과 함께 유효한 가설을 수립하고, 끊임없는 Iteration을 통해 PMF를 찾고 제품을 고도화합니다.\n" +
                  "\n" +
                  "개발자, 디자이너로 구성된 작은 팀을 이끌며 제품의 성장을 드라이브합니다.\n" +
                  "\n" +
                  "* Product Designer (완료)\n" +
                  "\n" +
                  "PM/PO, 개발자와 함께 신규 기능을 개발합니다.\n" +
                  "\n" +
                  "* 리엑트 네이티브(React Native) 개발자 (2명 모집)\n" +
                  "\n" +
                  "PM/PO, 디자이너와 함께 신규 기능을 개발합니다.\n" +
                  "\n" +
                  "* 백엔드 개발자 (완료)\n" +
                  "\n" +
                  "PM/PO, 디자이너와 함께 신규 기능을 개발합니다.\n" +
                  "\n" +
                  "* 마케터 (완료)\n" +
                  "\n" +
                  "SNS 운영 담당 / 퍼포먼스 마케터와 검색엔진 최적화를 통한 오가닉 유저의 앱 유입\n" +
                  "\n" +
                  "PM/PO들과 함께 유효한 가설을 수립하고, 끊임없는 Iteration을 통해 PMF를 찾고 제품을 고도화합니다.\n" +
                  "\n" +
                  "검색엔진 최적화를 통한 오가닉 유저의 앱 유입\n" +
                  "\n" +
                  "앱의 푸쉬 알람 기획 및 집행\n" +
                  "\n" +
                  "추후 유료 광고 기획 및 집행\n" +
                  "\n" +
                  "\n" +
                  "\n" +
                  "2.초기 기획과 디자인은 아래 링크를 통해서 보실 수 있어요~!\n" +
                  "\n" +
                  "https://www.behance.net/gallery/133427239/_\n" +
                  "\n" +
                  "\n" +
                  "3.앱은 앱스토어, 플레이스토어에서 다운받을 수 있습니다. (아직 완성이 아니라서 기능이 별로 없어요!)\n" +
                  "\n" +
                  "IOS : https://apps.apple.com/kr/app/%25EB%258C%2580%25EB%258F%2599%25EB%25B9%25B5%25EC%25A7%2580%25EB%258F%2584/id6445900733\n" +
                  "\n" +
                  "안드로이드 : https://play.google.com/store/apps/details?id=com.daedongbread&hl=ko\n" +
                  "\n" +
                  " 모집중\n" +
                  "곧 완성될 대동빵지도와 함께 사용자를 모으고, 빵순이들과 사장님께 대체불가능한 서비스를 만들며 수익화를 이끌 대동빵지도의 새로운 주인들을 모시고있어요!\n" +
                  "\n" +
                  "함께 서비스를 성장시킬 PM/PO, react native 개발자, 백엔드 개발자, 디자이너, 마케터를 모집합니다."


          ));

      projectRepository.save(
          Project.builder()
              .user(user)
              .userNickname(user.getUserNickname())
              .projectTitle(title.get(index16))
              .projectImg(projectImg.get(index16))
              .projectContent(content.get(index3))
//              .projectContent("text" + i)
              .projectType(projectType.get(index4))
              .maxFront(member)
              .maxBack(member)
              .offerPeriod(offerPeriod)
              .likeCount(like)
              .build()
      );
    }
  }
}