import React from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(basic).png";
import {Link} from "react-router-dom";
import './scss/UserLogin.scss';

const UserLogin = () => {
    return (
        <Common className={'member-login-wrapper'}>
            <section className={'login-container'}>
                <section className={'login-top-wrapper'}>
                    <img src={logo} alt={'logo'} className={'logo'}/>
                    <div className={'main-title'}>로그인하고 땀땀클럽의 다양한 컨텐츠를 즐겨보세요.</div>
                </section>

                <section className={'id-input-wrapper'}>
                    <div className={'id-title'}>아이디</div>
                    <input type={"text"} placeholder={"아이디 입력"} className={'id-input'} name={'id'}/>
                </section>
                <section className={'pw-input-wrapper'}>
                    <div className={'pw-title'}>비밀번호</div>
                    <input type={"text"} placeholder={"비밀번호 입력"} className={'pw-input'} name={'pw'}/>
                </section>

                <div className={'auto-login-wrapper'}>
                    <input type={"checkbox"} name={'autoLogin'} className={'autoLogin'}/>
                    <div className={'auto-login-title'}>자동로그인</div>
                </div>

                <section className={'keep-wrapper'}>
                    <button className={'normal-login'}>아이디로 계속하기</button>
                    <div className={'line'}></div>
                    <button className={'kakao-login'}>카카오로 계속하기</button>
                </section>

                <section className={'join-wrapper'}>
                    <div className={'join-btn'}>아직 회원이 아니신가요?<Link to={'/join'}>회원가입</Link></div>
                    <div className={'find-wrapper'}>
                        <Link to={'/'} className={'find-id'}>아이디 찾기</Link>
                        <Link to={'/'} className={'find-pw'}>비밀번호 찾기</Link>
                    </div>
                </section>
            </section>
        </Common>
    );
};

export default UserLogin;