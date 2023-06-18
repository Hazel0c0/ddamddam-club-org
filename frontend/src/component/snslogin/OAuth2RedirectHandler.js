import React, { useEffect } from 'react';
import { useKakaoLogin, kakaoLogin } from "../user/modules/kakao-login";
import Spinner from './Spinner';

/**
 * 카카오 로그인 처리 핸들러
 */
const OAuth2RedirectHandler = (props) => {
  let code = new URL(window.location.href).searchParams.get("code");
  const navigate = useKakaoLogin();

  useEffect(() => {
    kakaoLogin(navigate, code);
  }, [code, navigate]);

  return <Spinner />;
};

export default OAuth2RedirectHandler;