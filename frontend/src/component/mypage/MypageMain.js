import React from 'react';
import Common from "../common/Common";
import {Link} from "react-router-dom";
import {MYPAGE} from "../common/config/HostConfig";
import {
  getToken,
  getUserNickname,
  getUserProfile,
  getUserPosition,
  getUserEmail,
  getUserCareer, getUserRole
} from "../common/util/login-util";
import MypageBoardList from "./MypageBoardList";

/**
 * [               타이틀                ]
 * [ 유저 정보 ] [        콘텐츠          ]
 */
const MypageMain = props => {

  const USER_NICKNAME = getUserNickname();
  const USER_PROFILE = getUserProfile();
  const USER_POSITION = getUserPosition();
  const USER_EMAIL = getUserEmail();
  const USER_CAREER = getUserCareer();
  const USER_ROLE = getUserRole();

  return (
    <Common className={'mypage-top-wrapper'}>
      <div className={'title-wrapper'}>
        <p className={'main-title'}>마이페이지</p>
      </div>

      {/* TODO : 인포 랩퍼 css 작성 */}
      {/* 상단에 내 정보 보여주기 */}
      <div className={'mypage-info-wrapper'}>
        <div className={'profile-img'}>
          {
            USER_PROFILE === null
              ? <img src="/assets/img/anonymous.jpg" alt="프로필 이미지"/>
              : <img src={USER_PROFILE} alt="프로필 이미지"/>
          }
        </div>
        <div className={'nickname'}>
          <b>{USER_NICKNAME}</b> 님
        </div>
        <div className={'role'}>
          {USER_ROLE}
        </div>
        <div className={'email'}>
          {USER_EMAIL}
        </div>
        <div className={'career'}>
          <b>{USER_POSITION} {USER_CAREER}년째</b> 개발중!
        </div>
      </div>

      {/* TODO : 콘텐츠 랩퍼 CSS 작성 */}
      {/* 하단에 참여 채팅, 프로젝트 정보, 작성 게시글 목록 보여주기 */}
      <div className={'mypage-content-wrapper'}>
        {/* 멘토 채팅방 목록 불러오기 */}
        <div className={'mentor-wrapper'}>
          <div className={'mypage-sub-title'}>참여중인 멘토링</div>

        </div>

        {/* 참여중인 프로젝트 목록 불러오기 */}
        <div className={'project-wrapper'}>
          <div className={'mypage-sub-title'}>참여중인 프로젝트</div>
        </div>


        {/* 내가 작성한 전체 게시글 불러오기 */}
        <div className={'board-wrapper'}>
          <div className={'mypage-sub-title'}>내가 쓴 게시글</div>
          <MypageBoardList />
        </div>
      </div>
    </Common>
  );
};

MypageMain.propTypes = {};

export default MypageMain;
