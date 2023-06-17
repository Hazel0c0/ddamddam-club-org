import axios from "axios";
import { useNavigate } from "react-router-dom";

/**
 * Back 단에 카카오 로그인 요청을 보내고 토큰을 설정합니다.
 */
export const useKakaoLogin = () => {
  return useNavigate();
};

export const kakaoLogin = async (navigate, code) => {
  try {
    const response = await axios.get(
      `http://localhost:8181/api/ddamddam/oauth/kakao?code=${code}`
    );
    console.log(response); // 넘어온 토큰 확인

    const ACCESS_TOKEN = response.data.token;
    const LOGIN_USER_EMAIL = response.data.userEmail;

    sessionStorage.setItem('ACCESS_TOKEN', ACCESS_TOKEN); // 세션스토리지에 토큰 저장
    sessionStorage.setItem('LOGIN_USER_EMAIL', LOGIN_USER_EMAIL); // 세션스토리지에 토큰 저장

    navigate('/'); // 토큰 받고 로그인 성공했으니 메인으로 돌아가기
  } catch (err) {
    console.log('카카오 로그인 에러', err);
    window.alert('로그인에 실패하였습니다.');
    navigate('/login'); // 로그인 실패시 다시 로그인 화면으로
  }
};