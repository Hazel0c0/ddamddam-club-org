import React, {useEffect, useState} from 'react';
import './scss/ReviewDetail.scss';
import Common from "../common/Common";
import {Link, useNavigate, useParams} from "react-router-dom";
import {QNA, REVIEW} from "../common/config/HostConfig";
import {getToken, getUserIdx} from "../common/util/login-util";
import ReviewStar from "./StartRating/ReviewStar";
import back from "../../src_assets/back.png";
import {httpStateCatcherDelete, httpStateCatcherWrite} from "../common/util/HttpStateCatcherWrite";
import {useMediaQuery} from "react-responsive";

const ReviewDetail = () => {
    const redirection = useNavigate();
    const {reviewIdx} = useParams();
    const [detailReview, setDetailReview] = useState([]);
    const enterUserIdx = getUserIdx();

    const ACCESS_TOKEN = getToken();
    const requestHeader = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    };

    const presentationScreen = useMediaQuery({
        query: "(max-width: 414px)",
    });

    const asyncDetail = async () => {
        // console.log(reviewIdx);
        const res = await fetch(`${REVIEW}/detail?reviewIdx=${reviewIdx}`, {
            method: 'GET',
            headers: requestHeader
        })

        httpStateCatcherWrite(res.status);
        // if (res.status === 500) {
        //     alert('잠시 후 다시 접속해주세요.[서버오류]');
        //     return;
        // }

        const result = await res.json();
        // console.log(`디테일 result = `,result)
        setDetailReview(result);
    }

    // 게시글 삭제 핸들러
    const deleteHandler = async () => {
        // console.log("삭제버튼 클릭");


        if (window.confirm('정말 삭제하시겠습니까?')) {
            const res = await fetch(`${REVIEW}/delete/${reviewIdx}`, {
                method: 'DELETE',
                headers: requestHeader,
            });
            httpStateCatcherDelete(res.status);
            if (res.status === 200) {
                alert('삭제가 완료되었습니다.')
                redirection(-1);
            }
        }

    }

    useEffect(()=>{
        asyncDetail();
    },[]);

    return (
        <Common className={'review-detail-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>취업후기</p>
                <p className={'main-sub-title'}>근무했던 기업에 대한 정보를 공유해보세요.</p>
                {presentationScreen
                  ? null
                  : <Link to={'/reviews'} className={'back-btn'}>
                        <img src={back} alt={'back-icon'} className={'back-icon'}/>
                        {/*<span className={'back-text'}>Back</span>*/}
                    </Link>
                }
            </div>

            <section className={'main-text-wrapper'}>
                <h1 className={'review-title'}>
                    " {detailReview.reviewTitle} "
                </h1>
                <section className={'info-detail-container'}>
                    <div className={'info-wrapper'}>
                        {enterUserIdx === String(detailReview.userIdx) &&
                          <div className={'category btn-wrapper'}>
                              <Link to={`/reviews/modify/${reviewIdx}`} className={'modify-btn'}>수정</Link>
                              <span className={'delete-btn'} onClick={deleteHandler}>삭제</span>
                          </div>
                        }
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