import ReviewTotal from "./Pagination/ReviewTotal";

import Common from "../common/Common";
import './scss/ReviewList.scss'
import {getToken} from "../common/util/login-util";
import {useEffect, useState} from "react";

const ReviewList = ({searchValue, searchKeyword}) => {
    const [pageTrue, setPageTrue] = useState({
        total: true,
        rating: false,
        view: false,
        search: false
    })

    const [loginCheck, setLoginCheck] = useState(false);

    // 전체 목록 리스트 출력
    useEffect(() => {
        //로그인 검증
        const ACCESS_TOKEN = getToken();
        if (ACCESS_TOKEN !== '' && ACCESS_TOKEN !== null) {
            setLoginCheck(true);
        }
        console.log(`searchValue = ${searchKeyword}`)
        if (searchKeyword !== null && searchKeyword !== '') {
            setPageTrue({
                total: false,
                rating: false,
                view: false,
                search: true
            });
            return;
        }

        if (searchValue === '' || searchValue === '전체') {
            searchKeyword = '';
            setPageTrue({
                total: true,
                rating: false,
                view: false,
                search: false
            });
        } else if (searchValue === '평점순') {
            searchKeyword = '';
            setPageTrue({
                total: false,
                rating: true,
                view: false,
                search: false
            });
        } else if (searchValue === '조회순') {
            searchKeyword = '';
            setPageTrue({
                total: false,
                rating: false,
                view: true,
                search: false
            });
        }
    }, [searchValue, searchKeyword]);

    return (
        <Common className={'review-list-wrapper'}>
            {/*{pageTrue.search ? 'pageTrue.search의 값 true' : 'pageTrue.search의 값 false'}*/}
            {pageTrue.total && <ReviewTotal loginCheck={loginCheck}/>}
            {/*{pageTrue.rating && <QnaNoAdoption loginCheck={loginCheck}/>}*/}
            {/*{pageTrue.view && <QnaAdoption loginCheck={loginCheck}/>}*/}
            {/*{pageTrue.search && <QnaSearchKeyword loginCheck={loginCheck} searchKeyword={searchKeyword}/>}*/}
        </Common>
    );
};

export default ReviewList;