import Common from "../common/Common";
import './scss/ReviewList.scss'
import {getToken} from "../common/util/login-util";
import React, {useEffect, useState} from "react";
import {REVIEW} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import ReviewStar from "./StartRating/ReviewStar";
import {IoIosArrowForward} from "react-icons/io";
import viewIcon from "../../src_assets/view-icon.png";
import PageNation from "../common/pageNation/PageNation";
import {httpStateCatcher} from "../common/util/HttpStateCatcherWrite";

const ReviewList = ({searchValue, searchKeyword}) => {
    const [pageTrue, setPageTrue] = useState({
        total: true,
        rating: false,
        view: false,
        search: false
    })


    const [reviewList, setReviewList] = useState([]);
    const [pageNation, setPageNation] = useState([]);
    // const [clickCurrentPage, setClickCurrentPage] = useState(1);
    const [clickTotalCurrentPage, setClickTotalCurrentPage] = useState(1);
    const [clickRatingCurrentPage, setClickRatingCurrentPage] = useState(1);
    const [clickViewCurrentPage, setClickViewCurrentPage] = useState(1);
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

        asyncReviewTotalList();
    }, [searchValue, searchKeyword, clickTotalCurrentPage, clickRatingCurrentPage, clickViewCurrentPage]);

    //합치기 시작
    const asyncReviewTotalList = async () => {
        let responseUrl;
        if (searchValue === '') responseUrl = `/search?keyword=${searchKeyword}&page=${clickTotalCurrentPage}&size=10&sort=${searchValue}`
        if (searchValue === 'RATING') responseUrl = `/search?keyword=${searchKeyword}&page=${clickRatingCurrentPage}&size=10&sort=${searchValue}`
        if (searchValue === 'VIEW') responseUrl = `/search?keyword=${searchKeyword}&page=${clickViewCurrentPage}&size=10&sort=${searchValue}`
        // const responseUrl = `/search?keyword=${searchKeyword}&page=${clickCurrentPage}&size=10&sort=${searchValue}`

        // console.log(`responseUrl의 값 : `,responseUrl)
        const res = await fetch(`${REVIEW}${responseUrl}`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        httpStateCatcher(res.status);
        // if (res.status === 500) {
        //     alert('잠시 후 다시 접속해주세요.[서버오류]');
        //     return;
        // }

        // if (searchValue === '') setClickTotalCurrentPage(1);
        // if (searchValue === 'RATING') setClickRatingCurrentPage(1)
        // if (searchValue === 'VIEW') setClickViewCurrentPage(1)

        const reviewList = await res.json();
        // console.log(`전체 reviewList : `, reviewList)
        setReviewList(reviewList.responseList);
        setPageNation(reviewList.pageInfo);
    }


    const currentPageHandler = (clickPageNum) => {
        // console.log(`페이지 클릭 시 현재 페이지 번호 : ${clickPageNum}`)
        if (searchValue === '') setClickTotalCurrentPage(clickPageNum);
        if (searchValue === 'RATING') setClickRatingCurrentPage(clickPageNum)
        if (searchValue === 'VIEW') setClickViewCurrentPage(clickPageNum)
    }

    const redirection = useNavigate();
    const loginCheckHandler = (e) => {
        if (!loginCheck) {
            alert('로그인 후 이용가능합니다.')
            e.preventDefault();
            redirection('/login');
            // return;
        }
    }

    return (
        <Common className={'review-list-wrapper'}>
            {reviewList.map((review) => (
                <section className={'review-list'}>

                    <div className={'company-info-wrapper'}>
                        <span className={'company'}>{review.reviewCompanyName}</span>
                        <ReviewStar starCount={review.reviewRating}/>
                    </div>
                    <section className={'text-wrapper'}>
                        <div className={'main-content'}>
                            <div className={'text-title'}>{review.reviewTitle}</div>
                            <div className={'detail-wrapper'}>
                                <div className={'detail-reviewJob'}><span
                                    className={'sub-title'}>직무</span> {review.reviewJob}</div>
                                <div className={'detail-reviewJob'}><span
                                    className={'sub-title'}>근속년수</span>{review.reviewTenure}년
                                </div>
                                <div className={'detail-reviewJob'}><span
                                    className={'sub-title'}>위치</span>{review.reviewLocation}</div>
                            </div>

                        </div>
                        <div className={'text-content'}>{review.reviewContent}</div>
                    </section>
                    <div className={'right-section'}>
                        <Link to={`/reviews/detail/${review.reviewIdx}`} className={'text'} onClick={loginCheckHandler}>
                            <div className={'go-detail'}>
                                <div className={'go-detail-text'}>더보기</div>
                                <i className={'go-detail-icon'}><IoIosArrowForward/></i>
                            </div>
                        </Link>

                        <section className={'review-info'}>
                            <div className={'icon-wrapper'}>
                                <div className={'view-count-wrapper'}>
                                    <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/>
                                    <span>{review.reviewView}</span>
                                </div>
                            </div>
                            <div className={'write-date'}>{review.reviewDate}</div>
                        </section>
                    </div>
                </section>
            ))}

            <ul>
                {searchValue === ''
                    ?
                    <PageNation
                        pageNation={pageNation}
                        currentPageHandler={currentPageHandler}
                        clickCurrentPage={clickTotalCurrentPage}/>
                    :
                    searchValue === 'VIEW' ?
                        <PageNation
                            pageNation={pageNation}
                            currentPageHandler={currentPageHandler}
                            clickCurrentPage={clickViewCurrentPage}/>
                        :
                        <PageNation
                            pageNation={pageNation}
                            currentPageHandler={currentPageHandler}
                            clickCurrentPage={clickRatingCurrentPage}/>
                }
            </ul>
        </Common>
    );
};

export default ReviewList;