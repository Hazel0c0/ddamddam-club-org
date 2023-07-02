/**
 * 세션 스토리지, 로컬 스토리지에서 로그인 유저의 정보를 반환하는 역할
 * @returns 로그인 유저의 특정 정보 (ex. 토큰, 이메일, 이름 ...)
 */
import {useNavigate} from "react-router-dom";

// 세션 스토리지
// 로그인 유저의 토큰을 반환하는 함수
export const getToken = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('ACCESS_TOKEN');
  }
  return sessionStorage.getItem('ACCESS_TOKEN');
};

// 로그인 유저의 식별번호(idx)를 반환하는 함수
export const getUserIdx = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_IDX');
  }
  return sessionStorage.getItem('LOGIN_USER_IDX');
};

// 로그인 유저의 이메일을 반환하는 함수
export const getUserEmail = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_EMAIL');
  }
  return sessionStorage.getItem('LOGIN_USER_EMAIL');
};

// 로그인 유저의 이름을 반환하는 함수
export const getUserName = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_NAME');
  }
  return sessionStorage.getItem('LOGIN_USER_NAME');
};

// 로그인 유저의 닉네임을 반환하는 함수
export const getUserNickname = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_NICKNAME');
  }
  return sessionStorage.getItem('LOGIN_USER_NICKNAME');
};

// 로그인 유저의 가입일자를 반환하는 함수
export const getUserRegdate = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('ACCESS_TOKEN');
  }
  return sessionStorage.getItem('LOGIN_USER_REGDATE');
};

// 로그인 유저의 생년월일을 반환하는 함수
export const getUserBirth = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_BIRTH');
  }
  return sessionStorage.getItem('LOGIN_USER_BIRTH');
};

// 로그인 유저의 포지션을 반환하는 함수
export const getUserPosition = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_POSITION');
  }
  return sessionStorage.getItem('LOGIN_USER_POSITION');
};

// 로그인 유저의 경력을 반환하는 함수 (n년)
export const getUserCareer = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_CAREER');
  }
  return sessionStorage.getItem('LOGIN_USER_CAREER');
};

// 로그인 유저의 프로필 사진 경로를 반환하는 함수
export const getUserProfile = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_PROFILE');
  }
  return sessionStorage.getItem('LOGIN_USER_PROFILE');
};


// 로그인 유저의 권한을 반환하는 함수
export const getUserRole = () => {
  if (localStorage.getItem('ACCESS_TOKEN') !== null) {
    return localStorage.getItem('LOGIN_USER_ROLE');
  }
  return sessionStorage.getItem('LOGIN_USER_ROLE');
};

// 로그인 상태인지 검증해주는 함수
// null 일 시 로그인 X / null 이 아닐 시 로그인 O
export const isLogin = () => {
  return !!getUserEmail();
};

export const deleteSession = () =>{
  //일반로그인 sessionStorage 제거
  sessionStorage.removeItem('ACCESS_TOKEN');
  sessionStorage.removeItem('LOGIN_USER_IDX');
  sessionStorage.removeItem('LOGIN_USER_EMAIL');
  sessionStorage.removeItem('LOGIN_USER_NAME');
  sessionStorage.removeItem('LOGIN_USER_NICKNAME');
  sessionStorage.removeItem('LOGIN_USER_REGDATE');
  sessionStorage.removeItem('LOGIN_USER_BIRTH');
  sessionStorage.removeItem('LOGIN_USER_POSITION');
  sessionStorage.removeItem('LOGIN_USER_CAREER');
  sessionStorage.removeItem('LOGIN_USER_POINT');
  sessionStorage.removeItem('LOGIN_USER_PROFILE');
  sessionStorage.removeItem('LOGIN_USER_ROLE');
  // 자동로그인 localStorage 제거
  localStorage.removeItem('ACCESS_TOKEN');
  localStorage.removeItem('LOGIN_USER_IDX');
  localStorage.removeItem('LOGIN_USER_EMAIL');
  localStorage.removeItem('LOGIN_USER_NAME');
  localStorage.removeItem('LOGIN_USER_NICKNAME');
  localStorage.removeItem('LOGIN_USER_REGDATE');
  localStorage.removeItem('LOGIN_USER_BIRTH');
  localStorage.removeItem('LOGIN_USER_POSITION');
  localStorage.removeItem('LOGIN_USER_CAREER');
  localStorage.removeItem('LOGIN_USER_POINT');
  localStorage.removeItem('LOGIN_USER_PROFILE');
  localStorage.removeItem('LOGIN_USER_ROLE');
}
