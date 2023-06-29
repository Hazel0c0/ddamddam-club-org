import React, {useEffect, useState} from 'react';
import viewIcon from "../../../src_assets/view-icon.png";
import speechBubble from "../../../src_assets/speech-bubble.png";
import {Link, useNavigate} from "react-router-dom";
import {IoIosArrowForward} from "react-icons/io";
import PageNation from "../../common/pageNation/PageNation";
import {QNA} from "../../common/config/HostConfig";
import {getToken} from "../../common/util/login-util";
import {httpStateCatcher} from "../../common/util/HttpStateCatcherWrite";

const QnaTotal =({loginCheck, searchKeyword, searchValue}) => {
    const [qnaList, setQnaList] = useState([]);
    const [pageNation, setPageNation] = useState([]);
    const [clickCurrentPage, setClickCurrentPage] = useState(1);

    useEffect(()=>{
        asyncQnaTotalList();
    },[clickCurrentPage, searchKeyword, searchValue])
    const asyncQnaTotalList = async () => {
        // const responseUrl = `?page=${clickCurrentPage}&size=10`
        const responseUrl = `/search?keyword=${searchKeyword}&page=${clickCurrentPage}&size=10&sort=${searchValue}`
        const res = await fetch(`${QNA}${responseUrl}`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        httpStateCatcher(res.status);
        // if (res.status === 500) {
        //     alert('잠시 후 다시 접속해주세요.[서버오류]');
        //     return;
        // }

        const qnaList = await res.json();
        // console.log(qnaList)
        console.log(`total qnaList = `,qnaList);

        setQnaList(qnaList.payload.qnas);
        setPageNation(qnaList.payload.pageInfo);
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
                                <img src={viewIcon} alt={'view-count'}
                                     className={'view-count-icon'}/><span>{qna.viewCount}</span>
                            </div>
                            <div className={'speech-count-wrapper'}>
                                <img src={speechBubble} alt={'view-count'}
                                     className={'speech-count-icon'}/><span>{qna.replyCount}</span>
                            </div>
                        </div>
                        <div className={'write-date'} key={qna.boardDate}>{qna.boardDate}</div>
                    </section>

                    <Link to={`/qna/${qna.boardIdx}`} onClick={loginCheckHandler}>
                        {/*<div className={'go-detail'} onClick={() => detailHandler(qna.boardIdx)}>*/}
                        <div className={'go-detail'}>
                            <div className={'go-detail-text'}>더보기</div>
                            <i className={'go-detail-icon'}><IoIosArrowForward/></i>
                        </div>
                    </Link>
                </section>
            ))}
            {pageNation.totalCount === 0 &&
                <div className={'no-search-result'}>검색 결과가 없습니다.</div>
            }
            <ul>
                <PageNation
                    pageNation={pageNation}
                    currentPageHandler={currentPageHandler}
                    clickCurrentPage={clickCurrentPage}/>
            </ul>
        </>
    );
};

export default QnaTotal;