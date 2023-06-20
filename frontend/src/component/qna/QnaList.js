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
import QnaTotal from "./QnaTotal";
import QnaNoAdoption from "./QnaNoAdoption";
import QnaAdoption from "./QnaAdoption";
import QnaSearchKeyword from "./QnaSearchKeyword";

const QnaList = ({searchValue, searchKeyword}) => {
    const [pageTrue, setPageTrue] = useState({
        total: true,
        notAdoption: false,
        Adoption: false,
        search: false
    })

    const [loginCheck, setLoginCheck] = useState(false);

    // 전체 목록 리스트 출력
    useEffect(() => {
        //로그인 검증
        const ACCESS_TOKEN = getToken();
        if (ACCESS_TOKEN !== '' || ACCESS_TOKEN !== null) {
            setLoginCheck(true);

        }
        console.log(`searchValue = ${searchKeyword}`)
        if (searchKeyword !== null && searchKeyword !== '') {
            setPageTrue({
                total: false,
                notAdoption: false,
                Adoption: false,
                search: true
            });
            return;
        }

        if (searchValue === '' || searchValue === '전체') {
            searchKeyword = '';
            setPageTrue({
                total: true,
                notAdoption: false,
                Adoption: false,
                search: false
            });
        } else if (searchValue === '미채택') {
            searchKeyword = '';
            setPageTrue({
                total: false,
                notAdoption: true,
                Adoption: false,
                search: false
            });
        } else if (searchValue === '채택완료') {
            searchKeyword = '';
            setPageTrue({
                total: false,
                notAdoption: false,
                Adoption: true,
                search: false
            });
        }


    }, [searchValue, searchKeyword]);

    return (
        <Common className={'qna-list-wrapper'}>
            {/*{pageTrue.search ? 'pageTrue.search의 값 true' : 'pageTrue.search의 값 false'}*/}
            {pageTrue.total && <QnaTotal loginCheck={loginCheck}/>}
            {pageTrue.notAdoption && <QnaNoAdoption loginCheck={loginCheck}/>}
            {pageTrue.Adoption && <QnaAdoption loginCheck={loginCheck}/>}
            {pageTrue.search && <QnaSearchKeyword loginCheck={loginCheck} searchKeyword={searchKeyword}/>}
        </Common>
    );
};

export default QnaList;