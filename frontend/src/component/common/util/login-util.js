// 웹 브라우저 종료 시 토큰을 삭제하기 위해 sessionStorage 사용 //

// 로그인 유저의 토큰을 반환하는 함수
export const getToken = () => {
  return sessionStorage.getItem('ACCESS_TOKEN');
};

// 로그인 유저의 식별번호(idx)를 반환하는 함수
export const getUserIdx = () => {
  return sessionStorage.getItem('LOGIN_USER_IDX');
};


// 로그인 유저의 이메일을 반환하는 함수
export const getUserEmail = () => {
  return sessionStorage.getItem('LOGIN_USER_EMAIL');
};

// 로그인 유저의 이름을 반환하는 함수
export const getUserName = () => {
  return sessionStorage.getItem('LOGIN_USER_NAME');
};

// 로그인 유저의 닉네임을 반환하는 함수
export const getUserNickname = () => {
  return sessionStorage.getItem('LOGIN_USER_NICKNAME');
};

// 로그인 유저의 가입일자를 반환하는 함수
export const getUserRegdate = () => {
  return sessionStorage.getItem('LOGIN_USER_REGDATE');
};

// 로그인 유저의 생년월일을 반환하는 함수
export const getUserBirth = () => {
  return sessionStorage.getItem('LOGIN_USER_BIRTH');
};

// 로그인 유저의 포지션을 반환하는 함수
export const getUserPosition = () => {
  return sessionStorage.getItem('LOGIN_USER_POSITION');
};

// 로그인 유저의 경력을 반환하는 함수 (n년)
export const getUserCareer = () => {
  return sessionStorage.getItem('LOGIN_USER_CAREER');
};

// 로그인 유저의 보유 포인트를 반환하는 함수
export const getUserPoint = () => {
  return sessionStorage.getItem('LOGIN_USER_POINT');
};

// 로그인 유저의 프로필 사진 경로를 반환하는 함수
export const getUserProfile = () => {
  return sessionStorage.getItem('LOGIN_USER_PROFILE');
};


// 로그인 유저의 권한을 반환하는 함수
export const getUserRole = () => {
  return sessionStorage.getItem('LOGIN_USER_ROLE');
};

// 로그인 상태인지 검증해주는 함수
// null 일 시 로그인 X / null 이 아닐 시 로그인 O
export const isLogin = () => {
  return !!getUserEmail();
};
