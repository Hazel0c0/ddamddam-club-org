import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubble from "../../src_assets/speech-bubble.png";
import './scss/QnaList.scss'
import {IoIosArrowForward} from 'react-icons/io';
import {QNA} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import PageNation from "../common/pageNation/PageNation";
import {getToken} from "../common/util/login-util";
import QnaTotal from "./Paginatrion/QnaTotal";
import QnaNoAdoption from "./Paginatrion/QnaNoAdoption";
import QnaAdoption from "./Paginatrion/QnaAdoption";
import QnaSearchKeyword from "./Paginatrion/QnaSearchKeyword";

const QnaList = ({searchValue, searchKeyword}) => {

    const [loginCheck, setLoginCheck] = useState(false);


    // 전체 목록 리스트 출력
    useEffect(() => {
        //로그인 검증
        const ACCESS_TOKEN = getToken();
        if (ACCESS_TOKEN !== '' && ACCESS_TOKEN !== null) {
            setLoginCheck(true);
        }
        // console.log(`카테고리 : searchValue = ${searchValue}`)
        // console.log(`검색어 : searchKeyword = ${searchKeyword}`)



    }, [searchValue, searchKeyword]);

    return (
        <Common className={'qna-list-wrapper'}>
            {searchValue === '' && <QnaTotal loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
            {searchValue === 'N' && <QnaNoAdoption loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
            {searchValue === 'Y' && <QnaAdoption loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
        </Common>
    );
};

export default QnaList;