import React, {useEffect, useState} from 'react';
import viewIcon from "../../../src_assets/view-icon.png";
import {Link, useNavigate} from "react-router-dom";
import {IoIosArrowForward} from "react-icons/io";
import {REVIEW} from "../../common/config/HostConfig";
import ReviewStar from "../StartRating/ReviewStar";
import PageNation from "../../common/pageNation/PageNation";

const ReviewTotal = ({loginCheck, searchKeyword, searchValue}) => {
    const [reviewList, setReviewList] = useState([]);
    const [pageNation, setPageNation] = useState([]);
    const [clickCurrentPage, setClickCurrentPage] = useState(1);

    useEffect(()=>{
        asyncReviewTotalList();
    },[clickCurrentPage, searchKeyword, searchValue])

    const asyncReviewTotalList = async () => {
        const responseUrl = `/search?keyword=${searchKeyword}&page=${clickCurrentPage}&size=10&sort=${searchValue}`
        console.log(`total의 responseUrl : `,responseUrl)
        const res = await fetch(`${REVIEW}${responseUrl}`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        const reviewList = await res.json();
        console.log(`전체 reviewList : `,reviewList)
        setReviewList(reviewList.responseList);
        setPageNation(reviewList.pageInfo);
    }


    const currentPageHandler = (clickPageNum) => {
        console.log(`페이지 클릭 시 현재 페이지 번호 : ${clickPageNum}`)
        setClickCurrentPage(clickPageNum);
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
        <>
            {reviewList.map((review) => (
                <section key={review.reviewIdx} className={'review-list'}>

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
                <PageNation
                    pageNation={pageNation}
                    currentPageHandler={currentPageHandler}
                    clickCurrentPage={clickCurrentPage} />
            </ul>
        </>
    );
};

export default ReviewTotal;