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


  const [userIdx, setUserIdx] = useState('');
  const [newPassword, setNewPassword] = useState('');

  const handleInputChange = (e) => {
    const {name, value} = e.target;
    if (name === 'userIdx') {
      setUserIdx(value);
    } else if (name === 'newPassword') {
      setNewPassword(value);
    }
  };
  const token = getToken()
  const handleSubmit = async (e) => {
    e.preventDefault();
    /*
    console.log(REQUEST_URL)
    const response = await axios.post(`${REQUEST_URL}`, {
      tokenUserInfo: {userIdx},
      requestDTO: {newUserPassword: newPassword},
    });
     */
    const res = await fetch(REQUEST_URL,{
      method : 'POST',
      headers : {
        'content-type' : 'application/json',
        'Authorization' : 'Bearer '+ token
      },
      body : JSON.stringify({
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
            <input type={"text"} className={'oldpw'} id={'oldpw'} name={'oldpw'}
                   onChange={handleInputChange}/>
            {/*<span className={correct.userName ? 'correct' : 'not-correct'}>{message.userName}</span>*/}
            {/*{correct.userName &&*/}
            {/*  <BsCheckLg className={'check'}/>*/}
            {/*}*/}
          </div>
          <br/><br/>
          {/* 새로운 비밀번호*/}
          <div className={'input-newpw'}>
            <input type={"text"} className={'newpw'} id={'newpw'} name={'newpw'}
                   onChange={handleInputChange}/>
            {/*<span className={correct.userName ? 'correct' : 'not-correct'}>{message.userName}</span>*/}
            {/*{correct.userName &&*/}
            {/*  <BsCheckLg className={'check'}/>*/}
            {/*}*/}
          </div>
          <br/><br/>
          {/*새로운 비밀번호 확인*/}
          <div className={'input-checkpw'}>
            <input type={"text"} className={'checkpw'} id={'checkpw'} name={'checkpw'}
                   onChange={handleInputChange}/>
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