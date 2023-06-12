import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubble from "../../src_assets/speech-bubble.png";
import './scss/QnaList.scss'
import {IoIosArrowForward} from 'react-icons/io';
import {QNA} from "../common/config/HostConfig";
import {Link} from "react-router-dom";

const QnaList = () => {
    const [qnaList, setQnaList] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    // 전체 목록 리스트 출력
    useEffect(() => {
        fetch('//localhost:8181/api/ddamddam/qna?page=1&size=9')
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(
                result => {
                    if (!!result) {
                        setQnaList(result.payload.qnas);
                        setPageNation(result.payload.pageInfo);
                        console.log(result);
                        console.log(`result.qnas = ${result.payload.qnas[0].boardContent}`);
                        console.log(`result.pageInfo = ${result.payload.pageInfo}`);
                    }
                });
    }, []);

    return (
        <Common className={'qna-list-wrapper'}>
            {qnaList.map((qna) => (
                <section className={'qna-list'}>
                    {qna.boardAdoption === 'Y'
                        ? <div className={'checked'} key={qna.boardAdoption}>
                            {qna.boardAdoption}채택완료</div>
                        : <div className={'not-checked'} key={qna.boardAdoption}>
                            미채택</div>
                    }

                    <section className={'text-wrapper'}>
                        <div className={'text-title'} key={qna.boardTitle}>{qna.boardTitle}</div>
                        <div className={'text-content'} key={qna.boardContent}>{qna.boardContent}</div>
                        <ul className={'text-hashTag'}>
                            <li>#자바</li>
                            <li>#자바</li>
                            <li>#자바</li>
                        </ul>
                    </section>
                    <section className={'qna-info'}>
                        <div className={'qna-writer'} key={qna.boardWriter}>{qna.boardWriter}</div>
                        <div className={'icon-wrapper'}>
                            <div className={'view-count-wrapper'}>
                                <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/><span>10</span>
                            </div>
                            <div className={'speech-count-wrapper'}>
                                <img src={speechBubble} alt={'view-count'}
                                     className={'speech-count-icon'}/><span>10</span>
                            </div>
                        </div>
                        <div className={'write-date'} key={qna.boardDate}>{qna.boardDate}</div>
                    </section>
                    <div className={'go-detail'}>
                        <Link to={''}>
                            <div className={'go-detail-text'}>더보기</div>
                            <i className={'go-detail-icon'}><IoIosArrowForward/></i>
                        </Link>
                    </div>
                </section>
            ))}

        </Common>
    );
};

export default QnaList;