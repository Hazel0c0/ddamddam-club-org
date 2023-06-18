import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubble from "../../src_assets/speech-bubble.png";
import './scss/ReviewList.scss'
import {IoIosArrowForward} from 'react-icons/io';
import {QNA, REVIEW} from "../common/config/HostConfig";
import {Link} from "react-router-dom";

const QnaList = ({searchValue}) => {
    const [reviewList, setReviewList] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    //상세보기 이동
    const [qnaDetailBoardIdx, setqnaDetailBoardIdx] = useState([]);

    //전체 목록 렌더링 필터 async
    const asyncReviewList = async () => {
        const res = await fetch(`${REVIEW}/list`, {
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
    }, []);


    return (
        <Common className={'qna-list-wrapper'}>
            {reviewList.map((review) => (
                <section className={'qna-list'} key={review.reviewIdx}>

                    <section className={'text-wrapper'}>
                        <div className={'title-wrapper'}>
                            <div className={'text-title'} key={review.reviewTitle}>{review.reviewTitle}</div>
                            <div className={'text-reviewJob'} key={review.reviewJob}><span>직무</span> {review.reviewJob}
                            </div>
                            <div className={'text-title'} key={review.reviewTitle}><span>근속년수</span>{review.reviewTitle}
                            </div>
                            <div className={'text-title'} key={review.reviewLocation}>
                                <span>위치</span>{review.reviewLocation}</div>
                        </div>
                        <div className={'text-content'} key={review.reviewContent}>{review.reviewContent}</div>
                    </section>
                    <section className={'qna-info'}>
                        <div className={'icon-wrapper'}>
                            <div className={'view-count-wrapper'}>
                                <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/>
                                <span>{review.reviewView}</span>
                            </div>
                        </div>
                        <div className={'write-date'} key={review.boardDate}>{review.boardDate}</div>
                    </section>

                    <Link to={`/api/ddamddam/reviews/dertail/${review.reviewIdx}`}>
                        <div className={'go-detail'}>
                            <div className={'go-detail-text'}>더보기</div>
                            <i className={'go-detail-icon'}><IoIosArrowForward/></i>
                        </div>
                    </Link>
                </section>
            ))}

        </Common>
    );
};

export default QnaList;