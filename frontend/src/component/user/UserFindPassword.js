import React, {useState} from 'react';

// import {useNavigate} from "react-router-dom";
import {BASE_URL, AUTH} from "../common/config/HostConfig";
import '../user/scss/UserFindPassword.scss';
import {getToken} from "../common/util/login-util";

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
    <div className={'form-container'}>
      <form className={'find-form'} onSubmit={handleSubmit}>
        <h1 className={'password-title'}>👻비밀번호 변경👽</h1>
        <br/>
        ** 분실하지 않도록 신중하게 설정하세요 ! **
        <br/><br/><br/>
        <label className={'id'}>
          사용자 아이디(이메일) :
          <input
            type="text"
            name="userIdx"
            className={'email-input'}
            value={userIdx}
            onChange={handleInputChange}
          />
        </label>
        <br/><br/>
        {/*<label className={'oldpw'}>*/}
        {/*  기존 비밀번호:*/}
        {/*  <input*/}
        {/*    type="password"*/}
        {/*    name="newPassword"*/}
        {/*    className={'oldpassword-input'}*/}
        {/*    value={newPassword}*/}
        {/*    onChange={handleInputChange}*/}
        {/*  />*/}
        {/*</label>*/}
        {/*<br/><br/>*/}
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
        <button className={'changebtn'} type="submit">변경하기</button>
      </form>
    </div>
  );
};

export default UserFindPassword;