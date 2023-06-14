import React, { useEffect, useState } from 'react';
import './scss/QnaDetail.scss';
import Common from "../common/Common";
import { useParams } from "react-router-dom";

const QnaDetail = () => {
    const { boardIdx } = useParams();
    const [detailQna, setDetailQna] = useState(null);

    useEffect(() => {
        console.log(boardIdx);
        fetch(`//localhost:8181/api/ddamddam/qna/${boardIdx}`)
            .then((res) => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then((result) => {
                setDetailQna(result.payload);
                console.log(result.payload);
            });
    }, []);

    // /api/ddamddam/mentors/detail?mentorIdx={}
    ///api/ddamddam/qna/{boardId}
    return (
        <Common className={'qna-detail-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Q&A</p>
                <p className={'main-sub-title'}>땀땀클럽 회원들과 개발 지식을 공유할 수 있는 공간입니다.</p>
            </div>

            <section className={'main-text-wrapper'}>
                {detailQna && (
                    <>
                        <h1 className={'qna-title'}>
                            {detailQna.boardTitle}
                        </h1>
                        <div className={'hashTag-wrapper'}>
                            {detailQna.hashtagList.map((hashTag, index) => (
                                <div key={index} className={'hashTag'}>#{hashTag}</div>
                            ))}
                        </div>
                        <section className={'info-detail-container'}>
                            <div className={'detail-wrapper'}>
                                <div className={'category'}>
                                    <span className={'sub-title'}>작성자</span>
                                    <span className={'sub-content'}>{detailQna.boardWriter}</span>
                                </div>
                                <div className={'category'}>
                                    <span className={'sub-title'}>작성일자</span>
                                    <span className={'sub-content'}>2023.06.23</span>
                                </div>
                            </div>
                        </section>
                        <section className={'main-content'}>
                            {detailQna.boardContent}
                        </section>
                        <section className={'checked-wrapper'}>
                            {detailQna.boardAdoption === 'Y' ? (
                                <span className={'checked'}>채택완료</span>
                            ) : (
                                <span className={'not-checked'}>미채택</span>
                            )}
                        </section>
                    </>
                )}
            </section>
        </Common>
    );
};

export default QnaDetail;