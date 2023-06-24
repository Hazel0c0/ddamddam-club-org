import React, {useState} from 'react';
import axios from "axios";
// import {useNavigate} from "react-router-dom";
import {BASE_URL, AUTH} from "../common/config/HostConfig";
import '../user/scss/UserFindPassword.scss';
import {getToken} from "../common/util/login-util";
import Common from "../common/Common";
import logo from "../../src_assets/logo(white).png";
import {BsCheckLg} from "react-icons/bs";

const UserFindPassword = () => {

  const REQUEST_URL = BASE_URL + AUTH + '/modify-password';
  // const redirection = useNavigate();


  const [newPassword, setNewPassword] = useState('');
  const [oldpw, setOldPw] = useState('');

  //검증 메세지에 대한 상태변수 관리
  const [message, setMessage] = useState({
    checkpw: ''
  });

  // 검증 완료 체크에 대한 상태변수 관리(새 비밀번호 체크하기)
  const [correct, setCorrect] = useState({
    oldPw: false,
    newpw: false,
    checkpw: false
  });

  //상태변수로 회원값 입력 관리
  const [userValue, setUserValue] = useState({
    oldpw:'',
    newpw:''
  });

  //검증 데이터를 상태변수에 저장
  const saveInputState = ({key,inputVal, flag, msg}) => {
    inputVal !== 'pass' && setUserValue({
      ...userValue,
      [key]: inputVal
    });

    setMessage({
      ...message,
      [key]: msg
    });

    setCorrect({
      ...correct,
      [key]: flag
    });
  } ;

  // 패스워드 입력창 체인지 이벤트 핸들러
  const passwordHandler = e => {

    // 패스워드가 변동되면 확인란을 비우기
    document.getElementById('newpw').value = '';
    // document.getElementById('check-span').textContent = '';

    setMessage({...message, newpw: ''});
    setCorrect({...correct, newpw: false});

    const inputVal = e.target.value;

    const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/;

    // 검증 시작
    let msg, flag;
    if (!e.target.value) { // 패스워드 안적었을때
      msg = '비밀번호는 필수값입니다!';
      flag = false;
    } else if (!pwRegex.test(e.target.value)) {
      msg = '8글자 이상의 영문,숫자,특수문자를 포함해주세요!';
      flag = false;
    } else {
      msg = '사용 가능한 비밀번호입니다.';
      flag = true;
    }

    saveInputState({
      key: 'newpw',
      inputVal,
      msg,
      flag
    });

  };

  // 비밀번호 확인란 검증 이벤트 핸들러
  const pwcheckHandler = e => {
    // 검증 시작
    let msg, flag;
    if (!e.target.value) { // 패스워드 안적은거
      msg = '비밀번호 확인란은 필수값입니다!';
      flag = false;
    } else if (userValue.newpw !== e.target.value) {
      msg = '패스워드가 일치하지 않습니다.';
      flag = false;
    } else {
      msg = '패스워드가 일치합니다.';
      flag = true;
    }

    saveInputState({
      key: 'checkpw',
      inputVal: 'pass',
      msg,
      flag
    });

  };


  const handleInputChange = (e) => {
    const {name, value} = e.target;
    if (name === 'userIdx') {
      setUserIdx(value);
    } else if (name === 'newpw') {
      setNewPassword(value);
    }
  };



  const token = getToken()
  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await fetch(REQUEST_URL,{
      method : 'POST',
      headers : {
        'content-type' : 'application/json',
        'Authorization' : 'Bearer '+ token
      },
      body : JSON.stringify({
        userPassword : oldpw,
        newUserPassword: newPassword
      })
    })
    console.log(`비밀번호 변경 ,`,res)
    // console.log(response.date);

  };




  return (
    <Common className={'change-wrapper'}>
      <section className={'top-wrapper'}>
        <img src={logo} alt={'logo'} className={'logo'}/>
        <div className={'main-title'}>HI,WE ARE<br/>DDAMDDAM CLUB</div>
      </section>
      <div className={'background'}></div>
      <section className={'form-wrapper'}>
        <h1 className={'title'}>비밀번호 변경</h1>
        <br/><br/>
        <div className={'input-detail'}>
          {/*기존 비밀번호*/}
          <div className={'input-oldpw'}>
            <input type={"text"} className={'oldpw'} id={'oldpw'} name={'oldpw'} placeholder={'기존 비밀번호'}
                   onChange={handleInputChange}/>
            {/*<span className={correct.userName ? 'correct' : 'not-correct'}>{message.userName}</span>*/}
            {/*{correct.userName &&*/}
            {/*  <BsCheckLg className={'check'}/>*/}
            {/*}*/}
          </div>
          <br/><br/>
          {/* 새로운 비밀번호*/}
          <div className={'input-newpw'}>
            <input type={"text"} className={'newpw'} id={'newpw'} name={'newpw'} placeholder={'새로운 비밀번호'}
                   onChange={handleInputChange}/>
            {/*<span className={correct.userName ? 'correct' : 'not-correct'}>{message.userName}</span>*/}
            {/*{correct.userName &&*/}
            {/*  <BsCheckLg className={'check'}/>*/}
            {/*}*/}
          </div>
          <br/><br/>
          {/*새로운 비밀번호 확인*/}
          <div className={'input-checkpw'}>
            <input type={"text"} className={'checkpw'} id={'checkpw'} name={'checkpw'} placeholder={'비밀번호 확인'}
                   onChange={pwcheckHandler}/>
            {/*<span className={correct.userNickName ? 'correct' : 'not-correct'}>{message.userNickName}</span>*/}
            {/*{correct.userNickName &&*/}
            {/*  <BsCheckLg className={'check'}/>*/}
            {/*}*/}
          </div>
        </div>

        <button type={'submit'} className={'submit-btn'} onClick={handleSubmit}>변경하기</button>
      </section>
    </Common>

  );
};

export default UserFindPassword;