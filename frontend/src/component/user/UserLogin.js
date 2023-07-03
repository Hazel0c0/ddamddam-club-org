import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(basic).png";
import {Link, useNavigate} from "react-router-dom";
import './scss/UserLogin.scss';
import {BASE_URL, AUTH} from "../common/config/HostConfig";
import {KAKAO_AUTH_URL} from "../common/OAuth";
import {isLogin} from "../common/util/login-util";

const UserLogin = () => {

  const REQUEST_URL = BASE_URL + AUTH + '/signin';
  const redirection = useNavigate();

  // 카카오로 계속하기 버튼 클릭 시 동작하는 핸들러
  const kakaoLoginHandler = () => {
    window.location.href = KAKAO_AUTH_URL;
  }

  // 엔터 키 눌렀을 때 동작하는 핸들러
  const onKeyPress = (e) => {
    if (e.key === 'Enter') {
      loginHandler();
    }
  }

  const [email, setEmail] = useState(''); // email 필드의 상태를 관리
  const [password, setPassword] = useState(''); // password 필드의 상태를 관리
  const [isChecked, setIsChecked] = useState(false); // 자동로그인 체크 여부 상태를 관리


  // 로그인 핸들러
  const loginHandler = async () => {
    // ID와 비밀번호 상태 값 사용
    // console.log('ID:', email);
    // console.log('Password:', password);

    const res = await fetch(`${REQUEST_URL}`, {
      method: 'POST',
      headers: {'content-type':'application/json'},
      body: JSON.stringify({
        userEmail: email,
        userPassword: password
      })
    });

    const result = await res.json();
    const payload = result.payload;
    const finalResult = result.result;

    // 비밀번호 오류
    if (finalResult.code !== 200) {
      const resultMessage = await finalResult.message;
      alert('아이디 또는 비밀번호가 틀렸습니다. 다시 확인해주세요.');
      // document.querySelector('.id-input').value = '';
      document.querySelector('.pw-input').value = '';
      return;
    }

    // console.log(`payload 의 값 : ${JSON.stringify(payload)}`);

    const {
      token,
      userIdx,
      userEmail,
      userName,
      userNickname,
      userRegdate,
      userBirth,
      userPosition,
      userCareer,
      userProfile,
      userRole
    } = payload;

    /**
     * 브라우저가 제공하는 로컬 스토리지에 발급받은 토큰 & 회원 정보를 저장합니다.
     * 자동로그인 체크 안 한 경우 : 세션스토리지에 저장 (브라우저가 종료되면 사라짐)
     */
    if (isChecked === false) {
      // console.log('자동로그인 안함!!');
      sessionStorage.setItem('ACCESS_TOKEN', token);
      sessionStorage.setItem('LOGIN_USER_IDX', userIdx);
      sessionStorage.setItem('LOGIN_USER_EMAIL', userEmail);
      sessionStorage.setItem('LOGIN_USER_NAME', userName);
      sessionStorage.setItem('LOGIN_USER_NICKNAME', userNickname);
      sessionStorage.setItem('LOGIN_USER_REGDATE', userRegdate);
      sessionStorage.setItem('LOGIN_USER_BIRTH', userBirth);
      sessionStorage.setItem('LOGIN_USER_POSITION', userPosition);
      sessionStorage.setItem('LOGIN_USER_CAREER', userCareer);
      sessionStorage.setItem('LOGIN_USER_PROFILE', userProfile);
      sessionStorage.setItem('LOGIN_USER_ROLE', userRole);
    }
    /**
     * 자동 로그인 체크한 경우 : 로컬스토리지에 저장 (브라우저가 종료되어도 클라이언트에 보관됨)
     */
    if (isChecked === true) {
    // console.log('자동로그인 함!!');
      localStorage.setItem('ACCESS_TOKEN', token);
      localStorage.setItem('LOGIN_USER_IDX', userIdx);
      localStorage.setItem('LOGIN_USER_EMAIL', userEmail);
      localStorage.setItem('LOGIN_USER_NAME', userName);
      localStorage.setItem('LOGIN_USER_NICKNAME', userNickname);
      localStorage.setItem('LOGIN_USER_REGDATE', userRegdate);
      localStorage.setItem('LOGIN_USER_BIRTH', userBirth);
      localStorage.setItem('LOGIN_USER_POSITION', userPosition);
      localStorage.setItem('LOGIN_USER_CAREER', userCareer);
      localStorage.setItem('LOGIN_USER_PROFILE', userProfile);
      localStorage.setItem('LOGIN_USER_ROLE', userRole);
    }
    // window.location.href = '/';
    redirection('/');
  }

  // 비밀번호 찾기 버튼 클릭 시
  const onFindPwPage = () => {
    window.location.href = "/findinfo";
  };

  // 회원가입 버튼 클릭 시
  const onJoinPage = () => {
    window.location.href = "/signup";
  };

  return (
    <Common className={'member-login-wrapper'}>
      <section className={'login-container'}>
        <section className={'login-top-wrapper'}>
          <img src={logo} alt={'logo'} className={'logo'}/>
          <div className={'main-title'}>로그인하고 땀땀클럽의 다양한 컨텐츠를 즐겨보세요.</div>
        </section>

        <section className={'id-input-wrapper'}>
          <div className={'id-title'}>아이디</div>
          <input
            type={"text"}
            placeholder={"아이디 입력"}
            className={'id-input'}
            name={'id'}
            onKeyDown={onKeyPress}
            onChange={(e) => setEmail(e.target.value)}
          />
        </section>
        <section className={'pw-input-wrapper'}>
          <div className={'pw-title'}>비밀번호</div>
          <input
            type={"password"}
            placeholder={"비밀번호 입력"}
            className={'pw-input'}
            name={'pw'}
            onKeyDown={onKeyPress}
            onChange={(e) => setPassword(e.target.value)}
          />
        </section>

        <div className={'auto-login-wrapper'}>
          <input
            type={"checkbox"}
            name={'autoLogin'}
            className={'autoLogin'}
            onChange={e => setIsChecked(e.target.checked)}
          />
          <div className={'auto-login-title'}>자동로그인</div>
        </div>

        <section className={'keep-wrapper'}>
          <button
            className={'normal-login'}
            onClick={loginHandler}
          >
            아이디로 계속하기
          </button>
          <div className={'line'}></div>
          <button
            className={'kakao-login'}
            onClick={kakaoLoginHandler}
          >
            카카오로 계속하기
          </button>
        </section>

        <section className={'join-wrapper'}>
          <div className={'join-btn'}>아직 회원이 아니신가요?<Link to={'/join'} className={'btn-hover'}>회원가입</Link></div>
          <div className={'find-pw-wrapper'}>
            <Link to={'/find-password'} className={'find-pw'}>비밀번호 찾기</Link>
          </div>
        </section>
      </section>
    </Common>
  );
};

export default UserLogin;