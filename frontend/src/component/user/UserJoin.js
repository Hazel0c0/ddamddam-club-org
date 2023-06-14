import React from 'react';
import Common from "../common/Common";
import logo from "../../src_assets/logo(white).png";
import profile from "../../src_assets/IMG_4525.JPG";
import './scss/UserJoin.scss';
const UserJoin = () => {
    return (
        <Common className={'join-wrapper'}>
            <section className={'top-wrapper'}>
                <img src={logo} alt={'logo'} className={'logo'}/>
                <div className={'main-title'}>HI,WE ARE<br/>DDAMDDAM CLUB</div>
            </section>
            <div className={'background'}></div>
            <section className={'form-wrapper'}>
                <img src={profile} alt={'profileImg'} className={'profile-img'}></img>
                <div className={'profile-img-text'}>프로필을 등록해주세요</div>
                <div className={'input-id'}>
                    <input type={"text"} className={'id'} name={'id'} placeholder={'아이디'} />
                    <button className={'check-btn'}>중복확인</button>
                </div>

                <div className={'input-pw'}>
                    <input type={"text"} className={'pw'} name={'pw'} placeholder={'비밀번호'} />
                </div>

                <div className={'input-email'}>
                    <input type={"text"} className={'email-input'} name={'email'} placeholder={'이메일'} />
                        <select className={'email-select'} value={''} >
                            <option value={'gmail.com'}>@gmail.com</option>
                            <option value={'gmail.com'}>@gmail.com</option>
                            <option value={'gmail.com'}>@gmail.com</option>
                        </select>
                    <button className={'check-btn'}>인증하기</button>
                </div>

                <div className={'input-detail'}>
                    <input type={"text"} className={'name'} name={'name'} placeholder={'이름'} />
                    <input type={"text"} className={'birth'} name={'birth'} placeholder={'생년월일 8자리'} />
                    <select className={'position-select'} value={''} >
                        <option value={'백엔드'}>백엔드</option>
                        <option value={'프론트엔드'}>프론트엔드</option>
                    </select>
                </div>

                <button className={'submit-btn'}>가입완료</button>
            </section>
        </Common>
    );
};

export default UserJoin;