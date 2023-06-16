import React, {useState, useEffect} from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(basic).png";
import {Link, useNavigate} from "react-router-dom";
import './scss/UserLogin.scss';
import {BASE_URL, AUTH} from "../common/config/HostConfig";

const UserLogin = () => {

  const REQUEST_URL = BASE_URL + AUTH + '/signin';

  const redirection = useNavigate();

  // 엔터 키 눌렀을 때 동작하는 핸들러
  const onKeyPress = (e) => {
    if (e.key === 'Enter') {
      loginHandler();
    }
  }

  const [email, setEmail] = useState(''); // email 필드의 상태를 관리
  const [password, setPassword] = useState(''); // password 필드의 상태를 관리

  // 로그인 핸들러
  const loginHandler = async () => {
    // ID와 비밀번호 상태 값 사용
    console.log('ID:', email);
    console.log('Password:', password);

    const res = await fetch(`${REQUEST_URL}`, {
      method: 'POST',
      headers: {'content-type':'application/json'},
      body: JSON.stringify({
        userEmail: email,
        userPassword: password
      })
    });

    // 가입 실패 or 비밀번호 오류
    if (res.status === 400) {
      const text = await res.text();
      alert(text);
      return;
    }

    const result = await res.json();
    const final = result.payload;
    console.log(result);
    console.log(`payload의 값 : ${JSON.stringify(final)}`)
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
      userPoint,
      userProfile,
      userRole
    } = final;
    console.log(final.token)
    console.log(userEmail);

    /**
     * 브라우저가 제공하는 로컬 스토리지에 발급받은 토큰 & 회원 정보를 저장합니다.
     * 세션스토리지(브라우저가 종료되면 사라짐) - 서버X 로컬O
     */
    sessionStorage.setItem('ACCESS_TOKEN', token);
    sessionStorage.setItem('LOGIN_USER_IDX', userIdx);
    sessionStorage.setItem('LOGIN_USER_EMAIL', userEmail);
    sessionStorage.setItem('LOGIN_USER_NAME', userName);
    sessionStorage.setItem('LOGIN_USER_NICKNAME', userNickname);
    sessionStorage.setItem('LOGIN_USER_REGDATE', userRegdate);
    sessionStorage.setItem('LOGIN_USER_BIRTH', userBirth);
    sessionStorage.setItem('LOGIN_USER_POSITION', userPosition);
    sessionStorage.setItem('LOGIN_USER_CAREER', userCareer);
    sessionStorage.setItem('LOGIN_USER_POINT', userPoint);
    sessionStorage.setItem('LOGIN_USER_PROFILE', userProfile);
    sessionStorage.setItem('LOGIN_USER_ROLE', userRole);

    // redirection('/');
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
            type={"text"}
            placeholder={"비밀번호 입력"}
            className={'pw-input'}
            name={'pw'}
            onKeyDown={onKeyPress}
            onChange={(e) => setPassword(e.target.value)}
          />
        </section>

        <div className={'auto-login-wrapper'}>
          <input type={"checkbox"} name={'autoLogin'} className={'autoLogin'}/>
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
          <button className={'kakao-login'}>카카오로 계속하기</button>
        </section>

        <section className={'join-wrapper'}>
          <div className={'join-btn'}>아직 회원이 아니신가요?<Link to={'/join'}>회원가입</Link></div>
          <div className={'find-wrapper'}>
            <Link to={'/'} className={'find-id'}>아이디 찾기</Link>
            <Link to={'/'} className={'find-pw'}>비밀번호 찾기</Link>
          </div>
        </section>
      </section>
    </Common>
  );
};

export default UserLogin;