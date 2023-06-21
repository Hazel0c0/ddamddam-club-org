import logo from './logo.svg';
import './App.css';
import {useState} from "react";
import {Route, Routes} from "react-router-dom";
import {Reset} from "styled-reset";
import Header from "./component/common/Header";
import MentorsMain from "./component/mentors/MentorsMain";
import MainTemplate from "./component/common/MainTemplate";
import UserLogin from "./component/user/UserLogin";
import UserJoin from "./component/user/UserJoin";
import ProjectsTemplate from "./component/projects/mainpage/ProjectsTemplate";
import ProjectsWrite from "./component/projects/ProjectsWrite";
import ProjectsDetail from "./component/projects/ProjectsDetail";
import QnaMain from "./component/qna/QnaMain";
import Footer from "./component/common/Footer";
import MentorsTemplate from "./component/mentors/MentorsTemplate";
import MentorsWrite from "./component/mentors/MentorsWrite";
import MentorsModify from "./component/mentors/MentorsModify";
// import MentorsDetail from "./component/mentors/MentorsDetail";
import MentorsChat from "./component/mentors/MentorsChat";
import QnaTemplate from "./component/qna/QnaTemplate";
import QnaDetail from "./component/qna/QnaDetail";
import QnaWrite from "./component/qna/QnaWrite";
import QnaModify from "./component/qna/QnaModify";
import ProjectsModify from "./component/projects/ProjectsModify";
import ReviewList from "./component/review/ReviewList";
import ReviewTemplate from "./component/review/ReviewTemplate";
import ReviewDetail from "./component/review/ReviewDetail";
import ReviewWrite from "./component/review/ReviewWrite";
import OAuth2RedirectHandler from "./component/snslogin/OAuth2RedirectHandler";


function App() {
    //로그인 상태 관리(session완료 시 )
    const [isLogin, setIsLogin] = useState(true);
    return (
        <>
            <Reset/>
            <Header/>
            <Routes>
                {/*메인*/}
                <Route path={'/'} element={<MainTemplate/>}></Route>
                {/*<Route path="/board/:idx" element={<BoardDetail/>}/>*/}

                {/*멘토,멘티*/}
                <Route path={'/mentors'} element={<MentorsTemplate/>}></Route>
                <Route path={'/mentors/write'} element={<MentorsWrite/>}></Route>
                <Route path={'/mentors/modify/:idx'} element={<MentorsModify/>}></Route>

                {/*<Route path="/mentors/detail/mentorIdx/:idx" element={<MentorsDetail />} />*/}
                {/*멘토 멘티 채팅방*/}
                <Route path={'/mentors/detail/chat/:chatPageIdx/:roomId'} element={<MentorsChat/>}></Route>

                {/*프로젝트 모집*/}
                <Route path={'/projects'} element={<ProjectsTemplate/>}></Route>
                <Route path={'/projects/write'} element={<ProjectsWrite/>}></Route>
                <Route path={'/projects/detail'} element={<ProjectsDetail/>}></Route>
                <Route path={'/projects/modify'} element={<ProjectsModify/>}></Route>

                {/*채용공고 - not yet */}
                <Route></Route>

                {/*프로젝트 공유  - not yet*/}
                <Route></Route>

                {/*Q&A*/}
                <Route path={'/qna'} element={<QnaTemplate/>}></Route>
                {/*Q&A/상세보기*/}
                <Route path={'/api/ddamddam/qna/:boardIdx'} element={<QnaDetail/>}></Route>
                {/*Q&A/글작성*/}
                <Route path={'/api/ddamddam/qna/write'} element={<QnaWrite/>}></Route>
                {/*Q&A/글수정*/}
                <Route path={'api/ddamddam/qna/modify/:boardIdx'} element={<QnaModify/>}></Route>

                {/*REVIEW*/}
                <Route path={'/reviews'} element={<ReviewTemplate/>}></Route>
                {/*Reiview/상세보기*/}
                <Route path={'/reviews/detail/:reviewIdx'} element={<ReviewDetail/>}></Route>
                {/*Review/글작성*/}
                <Route path={'/reviews/write'} element={<ReviewWrite/>}></Route>

                {/*카카오 로그인*/}
                <Route path={'/oauth/callback/kakao'} element={<OAuth2RedirectHandler/>}></Route>

                {/*로그인*/}
                <Route path={'/login'} element={<UserLogin/>}></Route>

                {/*회원가입*/}
                <Route path={'/join'} element={<UserJoin/>}></Route>

            </Routes>
            <Footer/>
        </>
    );
}

export default App;

