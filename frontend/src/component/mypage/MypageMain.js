import React from 'react';
import Common from "../common/Common";
import "./scss/MypageMain.scss";
import {Link} from "react-router-dom";
import {MYPAGE} from "../common/config/HostConfig";
import profileImg from "../../src_assets/ProfileLogo.png"
import {
  getToken,
  getUserNickname,
  getUserProfile,
  getUserPosition,
  getUserEmail,
  getUserCareer, getUserRole
} from "../common/util/login-util";
import MypageBoardList from "./MypageBoardList";
import MypageProjectList from "./MypageProjectList";
import MypageChatRoom from "./MypageChatRoomList";

const MypageMain = props => {

  const ACCESS_TOKEN = getToken(); // 토큰
  const USER_NICKNAME = getUserNickname();
  const USER_PROFILE = getUserProfile();
  const USER_POSITION = getUserPosition();
  const USER_EMAIL = getUserEmail();
  const USER_CAREER = getUserCareer();
  const USER_ROLE = getUserRole();

  // headers
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  return (
    <>
      <Common className={'mypage-top-wrapper'}>

        <div className={'title-wrapper'}>
          <p className={'main-title'}>My page</p>
        </div>

        <div className={'mypage-info-wrapper'}>
          <div className={'profile-img'}>
            {
              USER_PROFILE === 'null'
                ? <img src={profileImg} alt="프로필 이미지"/>
                : <img src={USER_PROFILE} alt="프로필 이미지"/>
            }
          </div>
          <div className={'nickname'}>
            <p className={'bold-text'}>{USER_NICKNAME}</p>
          </div>
          <div className={'role text-box'}>
            <p className={'info-category'}>등급</p>
            <p className={'info-text'}>
              {
              USER_ROLE === 'COMMON' || USER_ROLE === 'SNS'
                ? '일반회원'
                : '관리자'
              }
            </p>
          </div>
          <div className={'email text-box'}>
            <p className={'info-category'}>메일</p>
            <p className={'info-text'}>{USER_EMAIL}</p>
          </div>
          <div className={'career text-box'}>
            <p className={'info-category'}>경력</p>
            <p className={'info-text'}>
              {USER_POSITION === 'BACKEND'
                ? '백엔드'
                : '프론트엔드'
              } {USER_CAREER}년차
            </p>
          </div>
          <div className={'writer-wrapper'}>
                    <Link to={'/mypage/modify'} className={'modify-btn'}>
                    회원 정보 수정
                    </Link>
                  </div>
                  <div>
                    <Link to={'/mypage/password'} className={'modify-btn'}>
                      비밀번호 변경
                    </Link>
                  </div>
        </div>

        {/* 하단에 참여 채팅, 프로젝트 정보, 작성 게시글 목록 보여주기 */}
        <div className={'mypage-content-wrapper'}>
          {/* 멘토 채팅방 목록 불러오기 */}
          <div className={'mentor-wrapper'}>
            <div className={'mypage-sub-title'}>참여중인 멘토링</div>
            <MypageChatRoom/>
          </div>

          {/* 참여중인 프로젝트 목록 불러오기 */}
          <div className={'project-wrapper'}>
            <div className={'mypage-sub-title'}>참여중인 프로젝트</div>
            <MypageProjectList/>
          </div>


          {/* 내가 작성한 전체 게시글 불러오기 */}
          <div className={'board-wrapper'}>
            <div className={'mypage-sub-title'}>내가 쓴 게시글</div>
            <MypageBoardList/>
          </div>
        </div>

      </Common>
    </>
  );
};

MypageMain.propTypes = {};

export default MypageMain;
