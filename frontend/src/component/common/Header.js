import React, {useEffect, useRef, useState} from 'react';
import logo from '../../src_assets/logo.png';
import './scss/Header.scss';
import Common from "./Common";
import {Link} from "react-router-dom";
import {getToken} from "./util/login-util";
import profileImg from "../../src_assets/IMG_4525.JPG"
import {BASE_URL, AUTH} from "../../component/common/config/HostConfig";

const Header = () => {

    //프로필 이미지 url 상태변수
    const [profileUrl, setProfileUrl] = useState(null); //기본값은 null
    const profileRequestURL = `${BASE_URL}${AUTH}/load-profile`;
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [animating, setAnimating] = useState(false);
    const [background, setBackground] = useState('rgba(0, 0, 0, 0)');
    const navigationRef = useRef(null);
    const categoryRef = useRef(null);
    // const [token, setToken] = useState(null);

    const ACCESS_TOKEN = getToken();

    //프로필사진 이미지 패치
    const fetchProfileImage = async() => {
        const res = await fetch(profileRequestURL,{
            method: 'GET',
            headers: {'Authorization': 'Bearer'}
          }

        );
        if(res.status === 200){
            //서버에서 직렬화된 이미지가 응답된다.
            const profileBlob = await res.blob();
            //해당 이미지를 imgUrl로 변경
            const imgUrl = window.URL.createObjectURL(profileBlob);
            setProfileUrl(imgUrl);
        } else{
            const err = await res.text();
            setProfileUrl(null);
        }
    } ;

    useEffect(() => {
        fetchProfileImage();

    }, [profileUrl]);

    //로그아웃
    const logoutHandler = () => {
        const confirmBtn  = window.confirm("정말 로그아웃 하시겠습니까?")
        if (confirmBtn){
        sessionStorage.removeItem('ACCESS_TOKEN');
        sessionStorage.removeItem('LOGIN_USER_IDX');
        sessionStorage.removeItem('LOGIN_USER_EMAIL');
        sessionStorage.removeItem('LOGIN_USER_NAME');
        sessionStorage.removeItem('LOGIN_USER_NICKNAME');
        sessionStorage.removeItem('LOGIN_USER_REGDATE');
        sessionStorage.removeItem('LOGIN_USER_BIRTH');
        sessionStorage.removeItem('LOGIN_USER_POSITION');
        sessionStorage.removeItem('LOGIN_USER_CAREER');
        sessionStorage.removeItem('LOGIN_USER_POINT');
        sessionStorage.removeItem('LOGIN_USER_PROFILE');
        sessionStorage.removeItem('LOGIN_USER_ROLE');

        window.location.href='/';
        }else {
            return;
        }
    }

    const handleMouseEnter = () => {
        if (!dropdownOpen) {
            setDropdownOpen(true);
            setAnimating(true);
            if (navigationRef.current) {
                navigationRef.current.style.animation = 'slide-in 0.3s ease-in';
            }
        }
        setBackground('rgba(0,0,0,0.5');
    }
    const handleMouseLeave = (event) => {
        const relatedTarget = event.relatedTarget;
        if (
            (!relatedTarget || (relatedTarget !== categoryRef.current && relatedTarget !== navigationRef.current)) &&
            navigationRef.current &&
            categoryRef.current &&
            categoryRef.current instanceof Node &&
            (!relatedTarget || !(relatedTarget instanceof Node) || !categoryRef.current.contains(relatedTarget))
        ) {
            setAnimating(true);
            navigationRef.current.style.animation = 'slide-out 0.2s ease-out forwards';
            setTimeout(() => {
                setDropdownOpen(false);
                setAnimating(false);
                if (navigationRef.current) {
                    navigationRef.current.style.animation = '';
                    setBackground('rgba(0,0,0,0');
                }
            }, 200);
        }
    };

    return (
        <Common className={'header-background'}>
            <div className={'header-wrapper'}>
                <Link to={'/'}>
                    <img className={'logo'} src={logo} alt="logo"/>
                </Link>
                <ul className={'category-wrapper'}
                    onMouseEnter={handleMouseEnter}
                    onMouseLeave={handleMouseLeave}
                    ref={categoryRef}
                >
                    <li>모집</li>
                    <li>취업게시판</li>
                    <li>프로젝트 공유</li>
                    <li>Q&A</li>
                </ul>


                <div className="login-wrapper">
                    {/*{ACCESS_TOKEN ? 'isLoginEnd의 값 true' : 'isLoginEnd의 값 false'}*/}
                    {ACCESS_TOKEN === null || ACCESS_TOKEN === ''
                        ? <>
                            <Link to={'/login'} className={'login'}>로그인</Link>
                            <Link to={'/join'} className={'sign-in'}>회원가입</Link>
                        </>
                        :
                        <>
                            <div className={'logout'} onClick={logoutHandler}>LOGOUT</div>
                            <Link to={'/myPage'} className={'myPage'}><img src={profileUrl ? profileUrl : require(profileImg)} alt={'profileImg'}
                                                                           className={'profile-img'}/></Link>
                        </>

                    }
                </div>
            </div>

            {/* 네비게이션 바 */}
            {dropdownOpen && (
                <nav
                    className={`navigation-bar ${animating ? 'animating' : ''}`}
                    onMouseLeave={handleMouseLeave}
                    ref={navigationRef}
                >
                    <ul>
                        <li><Link to={'/mentors'}>멘토・멘티</Link></li>
                        <li><Link to={'/projects'}>프로젝트 모집</Link></li>
                    </ul>
                    <ul>
                        <li><Link to={'/reviews'}>취업 후기</Link></li>
                        <li><Link to={'/'}>채용공고</Link></li>
                    </ul>
                    <ul>
                        <li><Link to={'/'}>프로젝트 공유</Link></li>
                    </ul>
                    <ul>
                        <li><Link to={'/qna'}>Q&A</Link></li>
                    </ul>
                </nav>
            )}
        </Common>

    );
};

export default Header;