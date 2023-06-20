import React, {useState} from 'react';
import Common from "../common/Common";
import './scss/QnaSearch.scss';
import searchIcon from '../../src_assets/search-icon.png';
import {Link, useNavigate} from "react-router-dom";
import {getToken} from "../common/util/login-util";

const QnaSearch = ({onSearchChange}) => {
    const [selectedBtn, setSelectedBtn] = useState('전체');
    const [valueChange, setValueChange] = useState(false);
    const handleInputChange = (e) => {
        const value = e.target.textContent;
        // console.log(value);
        onSearchChange(value);
        setSelectedBtn(value);
    }

    //로그인 검증
    const ACCESS_TOKEN = getToken();
    const redirection = useNavigate();
    const loginCheckHandler = (e) =>{
        console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
        if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null){
            alert('로그인 후 이용가능합니다.')
            e.preventDefault();
            redirection('/login');
            // return;
        }
    }

    return (
        <Common className={'qna-search-wrapper'}>
            <ul className={'sort-btn'}>
                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '전체' ? 'selected' : ''}
                >
                    전체
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '미채택' ? 'selected' : ''}
                >
                    미채택
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '채택완료' ? 'selected' : ''}
                >
                    채택완료
                </li>

            </ul>
            <div className={'search-wrapper'}>
                <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                <input className={'input-btn'} placeholder={'검색창'} name={'search'}></input>
            </div>
            <Link to={'/api/ddamddam/qna/write'}>
                <button className={'write-btn'} onClick={loginCheckHandler}>작성하기</button>
            </Link>
        </Common>
    );
};

export default QnaSearch;