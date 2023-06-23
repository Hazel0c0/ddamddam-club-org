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
  const tokne = getToken()
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
        'Authorization' : 'Bearer '+ tokne
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
      <div className={'form-container'}>
        <section className={'form-wrapper'} onSubmit={handleSubmit}>
          <br/>
          ** 분실하지 않도록 신중하게 설정하세요 ! **
          <br/><br/><br/>
          <div className={'input-nickname'}>
            <label className={'id'}>
              사용자 아이디(이메일) :
              <input
                type="text"
                name="nickname"
                className={'nickname'}
                value={userIdx}
                onChange={handleInputChange}
              />
              <span className={correct.userNickName ? 'correct' : 'not-correct'}>{message.userNickName}</span>
            </label>
          </div>
          <br/><br/>
          <label className={'oldpw'}>
            기존 비밀번호 :
            <input
              type="password"
              name="newPassword"
              className={'oldpassword-input'}
              value={newPassword}
              onChange={handleInputChange}
            />
          </label>
          <br/><br/>
          <label className={'newpw'}>
            새로운 비밀번호 :
            <input
              type="password"
              name="newPassword"
              className={'newpassword-input'}
              value={newPassword}
              onChange={handleInputChange}
            />
          </label>
          <br/><br/>
          <label className={'newpw-check'}>
            비밀번호 확인 :
            <input
              type="password"
              name="newPassword"
              className={'passwordcheck-input'}
              value={newPassword}
              onChange={handleInputChange}
            />
          </label>
          <br/><br/>
          <button className={'changebtn'} type="submit">변경하기</button>
        </section>
      </div>
    </Common>

  );
};

export default UserFindPassword;