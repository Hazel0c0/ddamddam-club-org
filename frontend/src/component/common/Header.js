import React, {useRef, useState} from 'react';
import logo from '../../src_assets/logo.png';
import './scss/Header.scss';
import Common from "./Common";
import {Link} from "react-router-dom";

const Header = () => {
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [animating, setAnimating] = useState(false);
    const [background, setBackground] = useState('rgba(0, 0, 0, 0)');
    const navigationRef = useRef(null);
    const categoryRef = useRef(null);

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
                <Link to={'/'} >
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
                    <Link to={'/login'} className={'login'}>로그인</Link>
                    <Link to={'/join'} className={'sign-in'}>회원가입</Link>
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
                        <li><Link to={'/'}>취업 후기</Link></li>
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