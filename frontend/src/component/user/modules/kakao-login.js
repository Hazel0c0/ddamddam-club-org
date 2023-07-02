import axios from "axios";
import { useNavigate } from "react-router-dom";
import {BASE_URL} from "../../common/config/HostConfig";

/**
 * Back 단에 카카오 로그인 요청을 보내고 토큰을 설정합니다.
 */
export const useKakaoLogin = () => {
  return useNavigate();
};

export const kakaoLogin = async (navigate, code) => {
  try {
    const response = await axios.get(
      BASE_URL + `/api/ddamddam/oauth/kakao?code=${code}`
    );

    const result = response.data;

    // 세션스토리지에 토큰 & 회원 정보를 저장 (카카오 로그인 시 자동 로그인 불가하므로)
    sessionStorage.setItem('ACCESS_TOKEN', result.token);
    sessionStorage.setItem('LOGIN_USER_IDX', result.userIdx);
    sessionStorage.setItem('LOGIN_USER_EMAIL', result.userEmail);
    sessionStorage.setItem('LOGIN_USER_NAME', result.userName);
    sessionStorage.setItem('LOGIN_USER_NICKNAME', result.userNickname);
    sessionStorage.setItem('LOGIN_USER_REGDATE', result.userRegdate);
    sessionStorage.setItem('LOGIN_USER_BIRTH', result.userBirth);
    sessionStorage.setItem('LOGIN_USER_POSITION', result.userPosition);
    sessionStorage.setItem('LOGIN_USER_CAREER', result.userCareer);
    sessionStorage.setItem('LOGIN_USER_PROFILE', result.userProfile);
    sessionStorage.setItem('LOGIN_USER_ROLE', result.userRole);

    window.location.href = '/'; // 토큰 받고 로그인 성공 후, 메인페이지로 돌아가기
  } catch (err) {
    // console.log('카카오 로그인 에러', err);
    window.alert('로그인에 실패하였습니다. 잠시 후에 다시 시도해주세요.');
    navigate('/login'); // 로그인 실패시 다시 로그인 화면으로
  }
};