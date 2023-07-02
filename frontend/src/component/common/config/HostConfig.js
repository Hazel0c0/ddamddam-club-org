// 브라우저가 현재 클라이언트 호스트 이름 얻어오기
const hostname = window.location.hostname;

let backendHost; // 백엔드 호스트 이름

if(hostname === 'localhost') {
  backendHost = 'http://localhost:8181';    // 로컬 테스트용
}else if(hostname === 'ddamddamclub.shop') {
  backendHost = 'http://3.39.47.226';  // 배포 테스트용
}
console.log('client hostname: ', hostname);
console.log('server hostname: ', backendHost);

//host주소
export const CRIENT_HOST = hostname;
export const MENTOR = backendHost + '/api/ddamddam/mentors'
export const CHAT = backendHost + '/api/ddamddam/chat'
export const QNA = backendHost + '/api/ddamddam/qna'
// http://localhost:8181/api/mentors?page=1&size=9&sort=mentorDate
// GET http://localhost:8181/api/mentors/list
// http://localhost:8181/api/mentors?page=1&size=9&sort='프론트엔드'
export const QNAREPLY = backendHost + '/api/ddamddam/qna-reply'

export const PROJECT = backendHost + '/api/ddamddam/project'

export const BASE_URL = backendHost;
export const AUTH = "/api/ddamddam/auth";
export const JOININ = "/api/ddamddam/auth"
export const EMAIL = backendHost + "/api/ddamddam/email"
export const REVIEW = backendHost + "/api/ddamddam/reviews"

export const MYPAGE = "/api/ddamddam/mypage";

export const COMPANY = backendHost + "/api/ddamddam/companies";

export const FIND_PASSWORD = "/auth/find-password";
export const SOCKET_URL = "3.39.47.226";
