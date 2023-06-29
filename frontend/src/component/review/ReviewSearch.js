import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/ReviewSearch.scss';
import searchIcon from '../../src_assets/search-icon.png';
import {Link, useNavigate} from "react-router-dom";
import {getToken} from "../common/util/login-util";
import {GrPowerReset} from "react-icons/gr";
import {debounce} from "lodash";

const ReviewSearch = ({onSearchChange, onSearchKeywordChange}) => {
    const [selectedBtn, setSelectedBtn] = useState('');
    const inputVal = useRef();

    //로그인 검증
    const ACCESS_TOKEN = getToken();
    const redirection = useNavigate();
    const loginCheckHandler = (e) =>{
        // console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
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
        }else if(value === "평점순"){
            onSearchChange("RATING");
            setSelectedBtn("RATING");
        }else if(value === "조회순"){
            onSearchChange("VIEW");
            setSelectedBtn("VIEW");
        }
    }

    //디바운싱
    const searchHandlerChange = debounce((value) => {
        onSearchKeywordChange(value);
    }, 400);

    const searchHandler = (e) => {
        const value = e.target.value;
        // onSearchKeywordChange(value);
        if (e.keyCode === 13) {
            searchHandlerChange.flush(); // 디바운스된 함수 즉시 실행
        } else if (value === '') {
            searchHandlerChange.cancel(); // 디바운스된 함수 취소
            onSearchKeywordChange(''); // 검색 키워드 초기화
        } else {
            searchHandlerChange(value); // 디바운스된 함수 호출
        }
    };

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
        <Common className={'review-search-wrapper'}>
            <ul className={'sort-btn'}>
                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '' ? 'selected' : ''}
                >
                    전체
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === 'RATING' ? 'selected' : ''}
                >
                    평점순
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === 'VIEW' ? 'selected' : ''}
                >
                    조회순
                </li>

            </ul>
            <div className={'search-wrapper'}>
                <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                <input className={'input-btn'} placeholder={'검색창'} name={'search'} onKeyUp={searchHandler} ref={inputVal}></input>
                <button className={'reset-btn'} onClick={resetHandler}><GrPowerReset /></button>
                {/*<button className={'submit-btn'} onClick={submitHandler}>검색</button>*/}
            </div>
            <Link to={'/reviews/write'}>
                <button className={'write-btn'} onClick={loginCheckHandler}>작성하기</button>
            </Link>
        </Common>
    );
};

export default ReviewSearch;