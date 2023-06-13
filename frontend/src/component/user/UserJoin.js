import React from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo.png";
import './scss/UserJoin.scss'
const UserJoin = () => {
    return (
        <Common className={'join-wrapper'}>
            <section color={'top-wrapper'}>
                <img src={logo} alt={'logo'}/>
                <div className={'main-title'}>HI,WE ARE DDAMDDAM CLUB</div>
            </section>
            <section className={'form-wrapper'}>
                <div className={'profile-img'}></div>
                <div className={'input-id'}>
                    <textarea className={'id'} name={'id'} placeholder={'아이디'} />
                    <button className={'check-btn'}>중복확인</button>
                </div>

                <div className={'input-pw'}>
                    <textarea className={'id'} name={'pw'} placeholder={'비밀번호'} />
                    <button className={'check-btn'}>중복확인</button>
                </div>

                <div className={'input-email'}>
                    <textarea className={'email-input'} name={'email'} placeholder={'이메일'} />
                        <select className={'email-select'} value={''} >
                            <option value={'gmail.com'}>@gmail.com</option>
                            <option value={'gmail.com'}>@gmail.com</option>
                            <option value={'gmail.com'}>@gmail.com</option>
                        </select>
                    <button className={'check-btn'}>인증하기</button>
                </div>

                <div className={'input-detail'}>
                    <textarea className={'name'} name={'name'} placeholder={'이름'} />
                    <textarea className={'birth'} name={'birth'} placeholder={'생년월일 8자리'} />
                    <select className={'position-select'} value={''} >
                        <option ></option>
                        <option value={'gmail.com'}>@gmail.com</option>
                        <option value={'gmail.com'}>@gmail.com</option>
                        <option value={'gmail.com'}>@gmail.com</option>
                    </select>
                </div>
            </section>
        </Common>
    );
};

export default UserJoin;