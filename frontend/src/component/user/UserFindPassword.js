import React, {useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {BASE_URL, AUTH} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";

const UserFindPassword = () => {

  const REQUEST_URL = BASE_URL + AUTH + '/modify-password';
  const redirection = useNavigate();


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

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await axios.post(`${REQUEST_URL}`, {
      tokenUserInfo: {userIdx},
      requestDTO: {newUserPassword: newPassword},
    });

    console.log(response.date);

  };


  return (
    <form onSubmit={handleSubmit}>
      <label>
        User Index:
        <input
          type="text"
          name="userIdx"
          value={userIdx}
          onChange={handleInputChange}
        />
      </label>
      <br/>
      <label>
        New Password:
        <input
          type="password"
          name="newPassword"
          value={newPassword}
          onChange={handleInputChange}
        />
      </label>
      <br/>
      <button type="submit">Change Password</button>
    </form>
  );
};

export default UserFindPassword;