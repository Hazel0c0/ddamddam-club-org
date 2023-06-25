import React, {useEffect, useState} from 'react';
import './scss/ReviewDetail.scss';
import Common from "../common/Common";
import {Link, useParams} from "react-router-dom";
import {QNA, REVIEW} from "../common/config/HostConfig";
import {getToken} from "../common/util/login-util";
import ReviewStar from "./StartRating/ReviewStar";
import back from "../../src_assets/back.png";

const ReviewDetail = () => {
    const {reviewIdx} = useParams();
    const [detailReview, setDetailReview] = useState([]);

    const ACCESS_TOKEN = getToken();
    const requestHeader = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    };

    const asyncDetail = async () => {
        console.log(reviewIdx);
        const res = await fetch(`${REVIEW}/detail?reviewIdx=${reviewIdx}`, {
            method: 'GET',
            headers: requestHeader
        })

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        const result = await res.json();
        console.log(`result = `,result)
        // console.log(`resultJSON = ${JSON.stringify(result)}`)
        setDetailReview(result);

    }


    useEffect(()=>{
        asyncDetail();
    },[]);

    // /api/ddamddam/mentors/detail?mentorIdx={}
    ///api/ddamddam/qna/{boardId}
    return (
        <Common className={'review-detail-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>취업후기</p>
                <p className={'main-sub-title'}>근무했던 기업에 대한 정보를 공유해보세요.</p>
                <Link to={'/reviews'} className={'back-btn'}>
                    <img src={back} alt={'back-icon'} className={'back-icon'}/>
                    {/*<span className={'back-text'}>Back</span>*/}
                </Link>
            </div>

            <section className={'main-text-wrapper'}>
                <h1 className={'review-title'}>
                    " {detailReview.reviewTitle} "
                </h1>
                <section className={'info-detail-container'}>
                    <div className={'info-wrapper'}>
                        <div className={'category'}>
                            <span className={'sub-title'}>직무</span>
                            <span className={'sub-content'}>{detailReview.reviewJob}</span>
                        </div>
                        <div className={'category'}>
                            <span className={'sub-title'}>근속연수</span>
                            <span className={'sub-content'}>{detailReview.reviewTenure}년</span>
                        </div>
                        <div className={'category'}>
                            <span className={'sub-title'}>위치</span>
                            <span className={'sub-content'}>{detailReview.reviewLocation}</span>
                        </div>
                    </div>
                    <div className={'rating'}>
                        <span className={'rating-title'}>{detailReview.reviewCompanyName}</span>
                        {/*{detailReview.reviewRating}*/}
                        <ReviewStar starCount={detailReview.reviewRating}/>
                    </div>
                    <div className={'detail-wrapper'}>
                        <div className={'category right-category'}>
                            <span className={'sub-title'}>작성일자</span>
                            <span className={'sub-content'}>{detailReview.reviewDate}</span>
                        </div>
                        <div className={'category'}>
                            <span className={'sub-title'}>조회수</span>
                            <span className={'sub-content'}>{detailReview.reviewView}</span>
                        </div>
                    </div>
                </section>
                <section className={'main-content'}>
                    {detailReview.reviewContent}
                </section>
            </section>
        </Common>
    );
};

export default ReviewDetail;