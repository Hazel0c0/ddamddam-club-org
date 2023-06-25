// 브라우저가 현재 클라이언트 호스트 이름 얻어오기
const hostname = window.location.hostname;

let backendHost; // 백엔드 호스트 이름

if(hostname === 'localhost') {
  backendHost = '//localhost:8181/api/ddamddam';    // 로컬 테스트용

}else if(hostname === 'newcen.co.kr') {
  backendHost = '';  // 배포 테스트용
}

//host주소
export const MENTOR = '//localhost:8181/api/ddamddam/mentors'
export const CHAT = '//localhost:8181/api/ddamddam/chat'
export const QNA = '//localhost:8181/api/ddamddam/qna'
// http://localhost:8181/api/mentors?page=1&size=9&sort=mentorDate
// GET http://localhost:8181/api/mentors/list
// http://localhost:8181/api/mentors?page=1&size=9&sort='프론트엔드'
export const QNAREPLY = '//localhost:8181/api/ddamddam/qna-reply'

export const PROJECT = '//localhost:8181/api/ddamddam/project'

export const BASE_URL = backendHost;
export const AUTH = "/auth";
export const JOININ = "//localhost:8181/api/ddamddam/auth"
export const EMAIL = "//localhost:8181/api/ddamddam/email"
export const REVIEW = "//localhost:8181/api/ddamddam/reviews"

export const MYPAGE = "/mypage";

export const COMPANY = "//localhost:8181/api/ddamddam/companies"

export const FIND_PASSWORD = "/auth/find-password";