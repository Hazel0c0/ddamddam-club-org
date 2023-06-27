import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/QnaSearch.scss';
import searchIcon from '../../src_assets/search-icon.png';
import {Link, useNavigate} from "react-router-dom";
import {getToken} from "../common/util/login-util";
import {GrPowerReset} from "react-icons/gr";
// import {GrPowerReset} from '/react-icons/gr';
const QnaSearch = ({onSearchChange, onSearchKeywordChange}) => {
    const [selectedBtn, setSelectedBtn] = useState('');
    const inputVal = useRef();

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
    const handleInputChange = (e) => {
        const value = e.target.textContent;
        if (value === "전체"){
            onSearchChange("");
            setSelectedBtn("");
        }else if(value === "미채택"){
            onSearchChange("N");
            setSelectedBtn("N");
        }else if(value === "채택완료"){
            onSearchChange("Y");
            setSelectedBtn("Y");
        }

        // onSearchChange(value);
        // setSelectedBtn(value);
        // onClickCurrentPageChange(1);
    }

    //검색엔터
    const searchHandler = (e) => {
        if (e.keyCode===13){
            onSearchKeywordChange(e.target.value);
        }
        if (e.target.value === ''){
            onSearchKeywordChange('');
        }
        onSearchKeywordChange(e.target.value);
    }

    //검색버튼
    const submitHandler = () =>{
        const inputValue =  inputVal.current.value;
        onSearchKeywordChange(inputValue);
    }
    //리셋버튼
    const resetHandler = () =>{
        inputVal.current.value='';

        onSearchKeywordChange('');
    }

    return (
        <Common className={'qna-search-wrapper'}>
            <ul className={'sort-btn'}>
                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '' ? 'selected' : ''}
                >
                    전체
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === 'N' ? 'selected' : ''}
                >
                    미채택
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === 'Y' ? 'selected' : ''}
                >
                    채택완료
                </li>

            </ul>
            <div className={'search-wrapper'}>
                <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                <input className={'input-btn'} placeholder={'검색창'} name={'search'} onKeyUp={searchHandler} ref={inputVal}></input>
                <button className={'reset-btn'} onClick={resetHandler}><GrPowerReset /></button>
                {/*<button className={'submit-btn'} onClick={submitHandler}>검색</button>*/}
            </div>
            <Link to={'/api/ddamddam/qna/write'}>
                <button className={'write-btn'} onClick={loginCheckHandler}>작성하기</button>
            </Link>
        </Common>
    );
};

export default QnaSearch;