import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import viewIcon from "../../src_assets/view-icon.png";
import './scss/ReviewList.scss'
import {IoIosArrowForward} from 'react-icons/io';
import {QNA, REVIEW} from "../common/config/HostConfig";
import {Link} from "react-router-dom";
import ReviewStarRating from "./StartRating/ReviewStarRating";
import ReviewStar from "./StartRating/ReviewStar";

const QnaList = ({searchValue}) => {
    const [reviewList, setReviewList] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    //상세보기 이동
    const [qnaDetailBoardIdx, setqnaDetailBoardIdx] = useState([]);


    //조회순, 평점순 처리중
    const asyncReviewList = async () => {
        let responseUrl;
        if (searchValue === '' || searchValue === '전체') {
            responseUrl = '/list?page=1&size=10'
        } else if (searchValue === '평점순') {
            responseUrl = '/rating';
        } else if (searchValue === '조회순') {
            responseUrl = '/view';
        }

        console.log(`searchValue = ${searchValue}`)
        console.log(`responseUrl = ${responseUrl}`)
        const res = await fetch(`${REVIEW}${responseUrl}`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }
        const reivewList = await res.json();
        console.log(reivewList)
        setReviewList(reivewList.responseList);
        setPageNation(reivewList.pageInfo);
    }

    // 전체 목록 리스트 출력
    useEffect(() => {
        asyncReviewList();
    }, [searchValue]);


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
                        <Link to={`/reviews/detail/${review.reviewIdx}`} className={'text'}>
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

        </Common>
    );
};

export default QnaList;