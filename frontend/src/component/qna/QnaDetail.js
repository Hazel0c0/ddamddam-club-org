import React, {useCallback, useEffect, useState} from 'react';
import './scss/QnaDetail.scss';
import Common from "../common/Common";
import {useParams} from "react-router-dom";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubble from "../../src_assets/speech-bubble.png";
import {QNAREPLY} from "../common/config/HostConfig";

const QnaDetail = () => {
    const {boardIdx} = useParams();
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
                // result.payload.replyList
                console.log(result.payload);
            });
    }, []);

    //더보기 버튼 만드는 중 개같네..
    const showMoreHandler = (reply, index) => {
        // setReplyList((prevReplyList) => {
        //     const updatedReplyList = [...prevReplyList];
        //     const currentReply = updatedReplyList[index];
        //     currentReply.subStr = !currentReply.subStr;
        //     return updatedReplyList;
        // });
        // console.log(replyList);
    };

    const writeReplyHandler = async () => {
        const inputContent = document.querySelector('.reply-input').value;
        console.log(`inputContent의 값 ${inputContent}`);
        const res = await fetch(`${QNAREPLY}/write`, {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                replyContent: inputContent,
                boardIdx: boardIdx
            })
        })

        if (res.status === 400) { // 가입이 안되어있거나, 비번틀린 경우
            const text = await res.text(); // 서버에서 온 문자열 읽기
            alert(text);
            return;
        }

        alert("댓글이 등록 되었습니다.");
    }

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
                                    <span className={'modify-btn'}>수정</span>
                                    <span className={'delete-btn'}>삭제</span>
                                </div>


                                <div className={'category'}>
                                    <span className={'sub-title'}>작성자</span>
                                    <span className={'sub-content'}>{detailQna.boardWriter}</span>
                                </div>

                                <div className={'category'}>
                                    <span className={'sub-title'}>작성일자</span>
                                    <span className={'sub-content'}>2023.06.23</span>
                                </div>

                                <div className={'icon-wrapper'}>
                                    <div className={'view-count-wrapper'}>
                                        <img src={viewIcon} alt={'view-count'}
                                             className={'view-count-icon'}/><span>{detailQna.viewCount}</span>
                                    </div>
                                    <div className={'speech-count-wrapper'}>
                                        <img src={speechBubble} alt={'view-count'}
                                             className={'speech-count-icon'}/><span>{detailQna.replyCount}</span>
                                    </div>
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

                        <section className={'reply-wrapper'}>
                            <p className={'reply-title'}>
                                <img src={speechBubble} className={'reply-icon'}/>댓글
                                <span className={'reply-count'}>{detailQna.replyList.length}</span>
                            </p>
                            {detailQna.replyList.map((reply, index) => (
                                <div className={'reply-list'} key={index}>
                                    <div className={'reply-top-wrapper'}>
                                        <p className={'reply-writer'}>{reply.replyWriter}</p>
                                        <div className={'reply-date'}>{reply.replyDate}</div>
                                    </div>
                                    <div className={'reply-content-wrapper'}>
                                        <span className={'reply-content'} key={index}>
                                                <span className={'reply-content'}>{reply.replyContent}</span>
                                                <span
                                                    className={'showMore-btn'}
                                                    onClick={() => showMoreHandler(reply, index)}
                                                >
                                                [줄이기]
                                            </span>
                                        </span>

                                        {reply.replyAdoption === 'Y' ? (
                                            <button className={'adoption-btn-checked'}>채택완료</button>
                                        ) : (
                                            <button className={'adoption-btn'}>채택하기</button>
                                        )}
                                    </div>
                                </div>
                            ))}

                            <div className={'reply-input-wrapper'}>
                                <textarea type={"text"} className={'reply-input'} placeholder={'댓글을 입력해주세요.'}
                                          name={'replyContent'}/>
                                <button className={'input-submit-btn'} onClick={writeReplyHandler}>등록</button>
                            </div>
                        </section>
                    </>
                )}
            </section>
        </Common>
    );
};

export default QnaDetail;