export function httpStateCatcher (status, successMessage) {
  if (status === 400) {
    alert('잘못된 요청 값 입니다.')
    return;
  } else if (status === 401) {
    alert('로그인이 만료되었습니다.')
    window.location.href = "/";
  } else if (status === 403) {
    alert('권한이 없습니다.')
    window.location.href = "/";
  } else if (status === 404) {
    alert('요청을 찾을 수 없습니다.');
    return;
  } else if (status === 500) {
    alert('잠시 후 다시 접속해주세요.[서버오류]');
    return;
  }
}
