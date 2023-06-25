import ReviewTotal from "./Pagination/ReviewTotal";
import Common from "../common/Common";
import './scss/ReviewList.scss'
import {getToken} from "../common/util/login-util";
import {useEffect, useState} from "react";
import ReviewSearchKeyword from "./Pagination/ReviewSearchKeyword";
import ReviewView from "./Pagination/ReviewView";
import ReviewRating from "./Pagination/ReviewRating";

const ReviewList = ({searchValue, searchKeyword}) => {
    const [pageTrue, setPageTrue] = useState({
        total: true,
        rating: false,
        view: false
    })

    const [loginCheck, setLoginCheck] = useState(false);

    // 전체 목록 리스트 출력
    useEffect(() => {
        //로그인 검증
        const ACCESS_TOKEN = getToken();
        if (ACCESS_TOKEN !== '' && ACCESS_TOKEN !== null) {
            setLoginCheck(true);
        }
        console.log(`카테고리 : searchValue = ${searchValue}`)
        console.log(`검색어 : searchKeyword = ${searchKeyword}`)
        // console.log(`searchValue = ${searchKeyword}`)
        // // if (searchKeyword !== null && searchKeyword !== '') {
        // //     setPageTrue({
        // //         total: false,
        // //         rating: false,
        // //         view: false,
        // //         search: true
        // //     });
        // //     return;
        // // }
        //
        // if (searchValue === '' || searchValue === '전체') {
        //     searchKeyword = '';
        //     setPageTrue({
        //         total: true,
        //         rating: false,
        //         view: false
        //     });
        // } else if (searchValue === '평점순') {
        //     searchKeyword = '';
        //     setPageTrue({
        //         total: false,
        //         rating: true,
        //         view: false
        //     });
        // } else if (searchValue === '조회순') {
        //     searchKeyword = '';
        //     setPageTrue({
        //         total: false,
        //         rating: false,
        //         view: true
        //     });
        // }
    }, [searchValue, searchKeyword]);

    return (
        <Common className={'review-list-wrapper'}>
            {searchValue === '' && <ReviewTotal loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
            {searchValue === 'RATING' && <ReviewRating loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
            {searchValue === 'VIEW' && <ReviewView loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}

            {/*{pageTrue.rating && <ReviewRating loginCheck={loginCheck} searchKeyword={searchKeyword}/>}*/}
            {/*{pageTrue.view && <ReviewView loginCheck={loginCheck} searchKeyword={searchKeyword}/>}*/}
        </Common>
    );
};

export default ReviewList;
