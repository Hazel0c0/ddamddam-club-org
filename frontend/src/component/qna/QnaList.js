import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubble from "../../src_assets/speech-bubble.png";
import './scss/QnaList.scss'
import {IoIosArrowForward} from 'react-icons/io';
import {QNA} from "../common/config/HostConfig";
import {Link} from "react-router-dom";

const QnaList = ({searchValue}) => {
    const [qnaList, setQnaList] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    //상세보기 이동
    const [qnaDetailBoardIdx, setqnaDetailBoardIdx] = useState([]);

    //전체 목록 렌더링 필터 async
    const asyncQnaList = async () => {
        let responseUrl;
        if (searchValue === '' || searchValue === '전체') {
            responseUrl = '?page=1&size=9'
        } else if (searchValue === '미채택') {
            responseUrl = '/non-adopts';
        } else if (searchValue === '채택완료') {
            responseUrl = '/adopts';
        }
        console.log(`searchValue = ${searchValue}`)
        console.log(`responseUrl = ${responseUrl}`)
        const res = await fetch(`${QNA}${responseUrl}`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        const qnaList = await res.json();
        console.log(qnaList)
        console.log(`qnaList = ${qnaList.payload}`);
        setQnaList(qnaList.payload.qnas);
        setPageNation(qnaList.payload.pageInfo);

    }

    // 전체 목록 리스트 출력
    useEffect(() => {
        asyncQnaList();
    }, [qnaDetailBoardIdx, searchValue]);



    return (
        <Common className={'qna-list-wrapper'}>
            {qnaList.map((qna) => (
                <section className={'qna-list'} key={qna.boardIdx}>
                    {qna.boardAdoption === 'Y'
                        ? <div className={'checked'} key={qna.boardAdoption}>
                            채택완료</div>
                        : <div className={'not-checked'} key={qna.boardAdoption}>
                            미채택</div>
                    }

                    <section className={'text-wrapper'}>
                        <div className={'text-title'} key={qna.boardTitle}>{qna.boardTitle}</div>
                        <div className={'text-content'} key={qna.boardContent}>{qna.boardContent}</div>
                        <ul className={'text-hashTag'}>
                            {qna.hashtagList.map((hashTag, index) => (
                                <li key={index}>#{hashTag}</li>
                            ))}
                        </ul>
                    </section>
                    <section className={'qna-info'}>
                        <div className={'qna-writer'} key={qna.boardWriter}>{qna.boardWriter}</div>
                        <div className={'icon-wrapper'}>
                            <div className={'view-count-wrapper'}>
                                <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/><span>{qna.viewCount}</span>
                            </div>
                            <div className={'speech-count-wrapper'}>
                                <img src={speechBubble} alt={'view-count'}
                                     className={'speech-count-icon'}/><span>{qna.replyCount}</span>
                            </div>
                        </div>
                        <div className={'write-date'} key={qna.boardDate}>{qna.boardDate}</div>
                    </section>

                    <Link to={`/api/ddamddam/qna/${qna.boardIdx}`}>
                        {/*<div className={'go-detail'} onClick={() => detailHandler(qna.boardIdx)}>*/}
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