import React, {useState} from 'react';
import "./scss/UserFindInfo.scss";
import Common from "../common/Common";
import {BASE_URL, FIND_PASSWORD} from "../common/config/HostConfig";
import {redirect, useNavigate} from "react-router-dom";

// TODO : 컴포넌트명 변경 필요합니다. UserFindPassword 로...
/**
 * 비밀번호 찾기 컴포넌트
 */
const UserFindInfo = () => {

  // ddamddam/auth/find-password
  const API_BASE_URL = BASE_URL + FIND_PASSWORD;
  const redirection = useNavigate();

  // 사용자에게 입력받은 이메일, 이름
  const [textInput, setTextInput] = useState(
    {
      userEmail: '',
      userName: ''
    }
  )

  // 입력창 상태관리 핸들러
  const handleSelect = (e) => {
    const {name, value} = e.target;
    let parseValue = value;

    setTextInput((prevTextInput) => ({
      ...prevTextInput,
      [name]: parseValue
    }));
  };

  const handleSubmit = async () => {
    console.log(textInput);

    // 디스트럭쳐링
    const {
      userEmail,
      userName
    } = textInput

    // 공백 및 정규식 검증
    // 이메일 형식 검증을 위한 정규식
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    if (!emailRegex.test(userEmail)) {
      alert('유효한 이메일 형식이 아닙니다.');
      return;
    }

    if (userEmail.length === 0 || userName.length === 0) {
      alert('공백 없이 입력해주세요.');
      return;
    }

    // POST 요청 보내기
    const data = {
      userEmail: userEmail,
      userName: userName
    };

    console.log(data);

    const res = await fetch(API_BASE_URL, {
      method: 'POST',
      headers: {'content-type': 'application/json'},
      body: JSON.stringify(data)
    });

    // 오류 발생시 알람
    if (res.status === 400) {
      alert('회원을 찾을 수 없습니다.');
      setTextInput((prevTextInput) => ({
        ...prevTextInput,
        userEmail: '',
        userName: ''
      }));
      return;
    } else if (res.status === 403) {
      alert('권한이 없습니다.');
      window.location.href = "/";
    } else if (res.status === 404) {
      alert('요청을 찾을 수 없습니다.');
    } else if (res.status === 500) {
      alert('잠시 후 다시 접속해주세요.[서버오류]');
      return;
    } else if (res.status === 200) {
      // 잘 전송되었을 시 로그인 창으로 이동
      alert('입력하신 이메일로 임시비밀번호가 전송되었습니다.');
      redirection('/login');
    }

  };

  return (
    <Common className={'find-wrapper'}>
      {/* 타이틀 */}
      <div className={'title-wrapper'}>
        <p className={'main-title'}>비밀번호 찾기</p>
        <p className={'main-sub-title'}>가입한 이메일로 임시 비밀번호를 발급받으실 수 있습니다.</p>
      </div>
      {/* 메인컨텐츠 */}
      <section className={'find-form-wrapper'}>
        <h1 className={'find-form-title'}>비밀번호를 찾고자하는 이메일과 이름을 입력해주세요.</h1>
        <div className={'email-input-wrapper input-wrapper'}>
          <input
            type="text"
            placeholder={'이메일을 입력하세요'}
            className={'input-text'}
            name={'userEmail'}
            value={textInput.userEmail}
            onChange={handleSelect}
          />
        </div>
        <div className={'name-input-wrapper input-wrapper'}>
          <input
            type="text"
            placeholder={'이름을 입력하세요'}
            className={'input-text'}
            name={'userName'}
            value={textInput.userName}
            onChange={handleSelect}
          />
        </div>
        {/*임시비밀번호 발급 버튼*/}
        <div className={'btn-wrapper input-wrapper'}>
          <button className={'submit-btn'} onClick={handleSubmit}>임시비밀번호 발급</button>
        </div>
      </section>
    </Common>
  );
};

export default UserFindInfo;
