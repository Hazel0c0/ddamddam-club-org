import React, {useCallback, useEffect, useRef, useState} from 'react';
import './scss/QnaDetail.scss';
import Common from "../common/Common";
import {json, Link, useParams} from "react-router-dom";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubble from "../../src_assets/speech-bubble.png";
import {QNA, QNAREPLY} from "../common/config/HostConfig";
import qnaDetail from "./QnaDetail";

const QnaDetail = () => {
    const inputRef = useRef();
    const {boardIdx} = useParams();
    const [detailQna, setDetailQna] = useState(null);
    const $clickMore = useRef();
    const [replyList, setReplyList] = useState([]);
    const [chekedReplyAdoption, setChekedReplyAdoption] = useState([]);

    useEffect(() => {
        console.log(boardIdx);
        fetch(`${QNA}/${boardIdx}`)
            .then((res) => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then((result) => {
                setDetailQna(result.payload);
                // console.log(result.payload.replyList);
                const modifiedReplyList = result.payload.replyList.map((reply) => {
                    // const showMore = reply.replyContent.length > 120 ? false : true;
                    const showMore = false;
                    // console.log(reply.replyContent.length)
                    return {...reply, showMore};
                });

                setReplyList(modifiedReplyList);

                //1.댓글 길이만큼 채택 버튼 배열 만들어놓기 ex) {[index : 1, replyAdoption : 'N']}
                const modifiedCheckBtn=result.payload.replyList.map((reply,index) => {
                    return {
                        replyIdx : reply.replyIdx,
                        replyAdoption : reply.replyAdoption
                    }
                });
                setChekedReplyAdoption(modifiedCheckBtn);

                console.log(`modifiedCheckBtn : ${JSON.stringify(modifiedCheckBtn)}`);

                // console.log(modifiedReplyList);
                console.log(result.payload);
            });
    }, []);

    //댓글 작성
    const writeReplyHandler = async () => {
        if (detailQna.boardAdoption === 'Y'){
            alert('이미 채택된 글은 댓글을 작성하실 수 없습니다.');
            return;
        }
        const inputContent = document.querySelector('.reply-input').value;
        // console.log(`inputContent의 값 ${inputContent}`);
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
        const replyData = await res.json();
        const replyList = replyData.payload;

        const modifiedReplyList = replyList.map((reply) => {
            const showMore = false;
            return {...reply, showMore};
        });
        setReplyList(modifiedReplyList);
        inputRef.current.value = '';
        alert("댓글이 등록 되었습니다.");
    }


    //더보기 버튼 만드는 중 개같네..
    //안된다시발비ㅓㅂ리버라ㅣㄸ라ㅣ리ㅣㅓㄷ바ㅣ디랃ㅂ

    const showMoreHandler = (reply, index) => {
        if (replyList[index].showMore) {
            const updateReplyList = [...replyList];
            updateReplyList[index] = {...replyList[index], showMore: !replyList[index].showMore};
            setReplyList(updateReplyList);

            console.log(`showMore : false로 변경`);
        } else {
            const test = replyList[index].replyContent
            const strTest = subStr(test, 0, test.length - 1)
            console.log(`strTest : ${strTest}`)
            // console.log(e.target.closest('.reply-content'))
            const updateReplyList = [...replyList];
            updateReplyList[index] = {...replyList[index], replyContent: strTest, showMore: !replyList[index].showMore};
            setReplyList(updateReplyList);
            console.log(updateReplyList[index])
            console.log(`showMore : true로 변경`);

        }

    };

    //첫 랜더링 될 때 글자 잘라주기
    const subStr = (replyContent, start, end) => {
        const subStrResult = replyContent.substr(start, end);
        return subStrResult;
    }


    //채택완료 처리
    const adoptHandler = async (e) => {
        const confirmBtn = window.confirm("정말 채택하시겠습니까? 채택 완료 후 수정이 불가합니다.");
        console.log(`replyIdx : ${e.target.closest('.reply-content-wrapper').querySelector('.replyIdx').value}`)
        const replyIdxValue = e.target.closest('.reply-content-wrapper').querySelector('.replyIdx').value;

        const res = await fetch(`${QNAREPLY}/adopts/${replyIdxValue}`, {
            method: 'PATCH',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                replyIdx: replyIdxValue,
            })
        })

        if (res.status === 400) { // 가입이 안되어있거나, 비번틀린 경우
            const text = await res.text(); // 서버에서 온 문자열 읽기
            alert(text);
            return;
        }

        // console.log(`replyData : ${JSON.stringify(replyData)}`);
        // console.log(`replyData : ${(replyData.payload)}`);

        const replyData = await res.json();

        if (replyData.payload === "SUCCESS") {
            const replyIndex = chekedReplyAdoption.findIndex((reply) => reply.replyIdx === +replyIdxValue);
            console.log(`replyIndex = ${replyIndex}`);
            console.log(`chekedReplyAdoption의 값 : ${JSON.stringify(chekedReplyAdoption)}`);
            if (replyIndex !== -1) {
                const updatedChekedReplyAdoption = [...chekedReplyAdoption];
                updatedChekedReplyAdoption[replyIndex] = {
                    ...updatedChekedReplyAdoption[replyIndex],
                    replyAdoption: 'Y'
                };
                setChekedReplyAdoption(updatedChekedReplyAdoption);
                console.log(`set되고 출력 updatedChekedReplyAdoption 값 : ${JSON.stringify(updatedChekedReplyAdoption)}`);
            }


            // const replyInfo = chekedReplyAdoption.find((reply) => reply.index === index);
            setDetailQna(prevQna => {
                return {
                    ...prevQna,
                    boardAdoption: 'Y'
                };
            });

        } else {
            alert("채택에 실패하였습니다.")
        }


    }

    const replyAdoptionHandler = (boardAdoption) => {
        if (boardAdoption === 'Y') {
            if (chekedReplyAdoption.replyAdoption === 'Y') {
                return <button className={'adoption-btn-checked'}>채택완료</button>;
            } else {
                return <button className={'adoption-btn-disabled'} disabled>채택불가</button>;
            }
        } else {
            return <button className={'adoption-btn'} onClick={adoptHandler}>채택하기</button>;
        }
    };

    //댓글 수정
    const modifyHandler = (event) => {
        if (detailQna.boardAdoption === 'Y'){
            alert('이미 채택된 글은 수정 또는 삭제하실 수 없습니다.');
            event.preventDefault();
        }
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
                                    <Link to={`/api/ddamddam/qna/modify/${boardIdx}`} className={'modify-btn'} onClick={modifyHandler}>수정</Link>
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


                        {/*댓글 영역*/}
                        <section className={'reply-wrapper'}>
                            <p className={'reply-title'}>
                                <img src={speechBubble} className={'reply-icon'}/>댓글
                                <span className={'reply-count'}>{detailQna.replyList.length}</span>
                            </p>
                            {replyList.map((reply, index) => (
                                <div className={'reply-list'} key={index}>
                                    <div className={'reply-top-wrapper'}>
                                        <p className={'reply-writer'}>{reply.replyWriter}</p>
                                        <div className={'reply-date'}>{reply.replyDate}</div>
                                    </div>

                                    <div className={'reply-content-wrapper'}>
                                        <input type={"hidden"} value={reply.replyIdx} name={"replyIdx"}
                                               className={'replyIdx'}/>
                                        {reply.replyContent.length <= 120 ?
                                            <div>{reply.replyContent}</div>
                                            :
                                            <>
                                            <span className={'reply-content'} key={index}>
                                                {subStr(reply.replyContent, 0, 120)}
                                                <span
                                                    className={'showMore-btn'}
                                                    onClick={() => showMoreHandler(reply.replyContent, index)}
                                                    ref={$clickMore}
                                                >
                                                    '[펼치기]'
                                            </span>
                                            </span>

                                            </>

                                        }
                                        {/*{replyAdoptionHandler(detailQna.boardAdoption)}*/}
                                        <div>
                                            {detailQna.boardAdoption === 'Y' ? (
                                                chekedReplyAdoption[index].replyAdoption === 'Y' ? (
                                                    <button className="adoption-btn-checked">채택완료</button>
                                                ) : (
                                                    <button className="adoption-btn-disabled" disabled>채택불가</button>
                                                )
                                            ) : (
                                                <button className="adoption-btn" onClick={adoptHandler}>채택하기</button>
                                            )}
                                        </div>

                                    </div>
                                </div>
                            ))}

                            <div className={'reply-input-wrapper'}>
                                <textarea type={"text"} className={'reply-input'} placeholder={'댓글을 입력해주세요.'}
                                          name={'replyContent'} ref={inputRef}/>
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