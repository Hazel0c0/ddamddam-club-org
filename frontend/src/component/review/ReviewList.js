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

    }, [searchValue, searchKeyword]);

    return (
        <Common className={'review-list-wrapper'}>
            {searchValue === '' && <ReviewTotal loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
            {searchValue === 'RATING' && <ReviewRating loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
            {searchValue === 'VIEW' && <ReviewView loginCheck={loginCheck} searchKeyword={searchKeyword} searchValue={searchValue}/>}
        </Common>
    );
};

export default ReviewList;
