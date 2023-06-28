import React, {useEffect, useRef, useState} from 'react';
import './scss/QnaDetail.scss';
import Common from "../common/Common";
import {Link, useNavigate, useParams} from "react-router-dom";
import viewIcon from "../../src_assets/view-icon.png";
import speechBubbleText from "../../src_assets/speech-bubble(text).png";
import speechBubble from "../../src_assets/speech-bubble.png";
import {QNA, QNAREPLY} from "../common/config/HostConfig";
import {getToken, getUserNickname} from "../common/util/login-util";
import {debounce} from "lodash";
import {httpStateCatcher} from "../common/util/HttpStateCatcher";

const QnaDetail = () => {
    const redirection = useNavigate();
    const inputRef = useRef();
    const {boardIdx} = useParams();
    const [detailQna, setDetailQna] = useState(null);
    const [replyList, setReplyList] = useState([]);
    const [checkedReplyAdoption, setCheckedReplyAdoption] = useState([]);

    const enterUserNickName = getUserNickname();

    const ACCESS_TOKEN = getToken();
    const requestHeader = {
      'content-type': 'application/json',
      'Authorization': 'Bearer ' + ACCESS_TOKEN
    };

    //수정버튼 누르면 수정할 수 있게
    const [replyModifyShow, setReplyModifyShow] = useState([]);


    const asyncDetail = async () => {
      console.log(boardIdx);
      const res = await fetch(`${QNA}/${boardIdx}`, {
        method: 'GET',
        headers: requestHeader
      })

      if (res.status === 500) {
        alert('잠시 후 다시 접속해주세요.[서버오류]');
        return;
      }

      const result = await res.json();
      console.log(`result = ${result}`)
      setDetailQna(result.payload);

      const modifiedReplyList = result.payload.replyList.map((reply) => {
        const showMore = false;
        return {...reply, showMore};
      });

      setReplyList(modifiedReplyList);

      //1.댓글 길이만큼 채택 버튼 배열 만들어놓기 ex) {[index : 1, replyAdoption : 'N']}
      const modifiedCheckBtn = result.payload.replyList.map((reply, index) => {
        return {
          replyIdx: reply.replyIdx,
          replyAdoption: reply.replyAdoption
        }
      });
      setCheckedReplyAdoption(modifiedCheckBtn);

      console.log(`modifiedCheckBtn : ${JSON.stringify(modifiedCheckBtn)}`);

      // console.log(modifiedReplyList);
      console.log(result.payload);

      //배열만큼 수정폼 반복
      const updateModifiedBtn = result.payload.replyList.map((list, index) => {
        return false;
      });

      setReplyModifyShow(updateModifiedBtn);
      //
    }

    useEffect(() => {
      asyncDetail();
    }, []);


    //댓글 작성
    //TODO 중복클릭 방지 해야함
    const writeReplyHandler = debounce(async () => {

      if (detailQna.boardAdoption === 'Y') {
        alert('이미 채택된 글은 댓글을 작성하실 수 없습니다.');
        return;
      }
      const inputContent = document.querySelector('.reply-input').value;
      if (inputContent.trim() === '') {
        alert('공백없이 입력해주세요!');
        return;
      }

      const res = await fetch(`${QNAREPLY}/write`, {
        method: 'POST',
        headers: requestHeader,
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

      asyncDetail();


    }, 300)

    //댓글 수정
    const replyModifyShowHandler = (index) => {
      setReplyModifyShow((prev) => {
        const updatedState = {
          ...prev,
          [index]: true
        };
        console.log('replyModifyShow:', updatedState);
        return updatedState;
      });
    };

    //댓글 수정 취소
    const replyModifyCancleHandler = (index) => {
      setReplyModifyShow((prev) => {
        const updatedState = {
          ...prev,
          [index]: false
        };
        console.log('replyModifyShow:', updatedState);
        return updatedState;
      });
    }

    const replyModifyHandler = async (replyIdx) => {

      if (detailQna.boardAdoption === 'Y') {
        alert('이미 채택된 글은 댓글은 수정하실 수 없습니다.');
        return;
      }

      const modifyContent = document.querySelector('.reply-modify-content').value;

      // console.log(`inputContent의 값 ${inputContent}`);
      const res = await fetch(`${QNAREPLY}/modify`, {
        method: 'PATCH',
        headers: requestHeader,
        body: JSON.stringify({
          replyIdx: replyIdx,
          replyContent: modifyContent,
        })
      })

      if (res.status === 400) { // 가입이 안되어있거나, 비번틀린 경우
        const text = await res.text(); // 서버에서 온 문자열 읽기
        alert(text);
        return;
      }
      setReplyModifyShow(false);
      asyncDetail();
      alert("댓글이 수정 되었습니다.");
    }


    //댓글 삭제
    const replyDeleteHandler = async (replyIdx) => {
      if (detailQna.boardAdoption === 'Y') {
        alert('이미 채택된 글은 댓글은 삭제하실 수 없습니다.');
        return;
      }

      // console.log(`inputContent의 값 ${inputContent}`);
      const res = await fetch(`${QNAREPLY}/delete/${replyIdx}`, {
        method: 'DELETE',
        headers: requestHeader
      })
      console.log(`댓글 삭제 res ${JSON.stringify(res)}`)
      alert("댓글이 삭제 되었습니다.");
      asyncDetail();
    }


    //채택완료 처리
    const adoptHandler = async (e) => {
      // if (enterUserNickName === )
      const confirmBtn = window.confirm("정말 채택하시겠습니까? 채택 완료 후 수정이 불가합니다.");
      console.log(`replyIdx : ${e.target.closest('.reply-content-wrapper').querySelector('.replyIdx').value}`)

      const replyIdxValue = e.target.closest('.reply-content-wrapper').querySelector('.replyIdx').value;

      const res = await fetch(`${QNAREPLY}/adopts/${replyIdxValue}`, {
        method: 'PATCH',
        headers: requestHeader,
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
        const replyIndex = checkedReplyAdoption.findIndex((reply) => {
          console.log('after success reply: ', reply);
          console.log('after success replyIdxVal: ', replyIdxValue);
          return reply.replyIdx === +replyIdxValue;
        });
        console.log(`replyIndex = ${replyIndex}`);
        console.log(`checkedReplyAdoption 값 : ${JSON.stringify(checkedReplyAdoption)}`);
        if (replyIndex !== -1) {
          const updatedChekedReplyAdoption = [...checkedReplyAdoption];
          updatedChekedReplyAdoption[replyIndex] = {
            ...updatedChekedReplyAdoption[replyIndex],
            replyAdoption: 'Y'
          };
          setCheckedReplyAdoption(updatedChekedReplyAdoption);
          console.log(`set되고 출력 updatedChekedReplyAdoption 값 : ${JSON.stringify(updatedChekedReplyAdoption)}`);
        }


        // const replyInfo = checkedReplyAdoption.find((reply) => reply.index === index);
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
        if (checkedReplyAdoption.replyAdoption === 'Y') {
          return <button className={'adoption-btn-checked'}>채택완료</button>;
        } else {
          return <button className={'adoption-btn-disabled'} disabled>채택불가</button>;
        }
      } else {
        return <button className={'adoption-btn'} onClick={adoptHandler}>채택하기</button>;
      }
    };

    //게시물 수정
    const modifyHandler = (event) => {
      if (detailQna.boardAdoption === 'Y') {
        alert('이미 채택된 글은 수정 또는 삭제하실 수 없습니다.');
        event.preventDefault();
      }
    }

    //게시물 삭제
    const deleteHandler = async () => {

      if (window.confirm("정말 삭제하시겠습니까?")) {
        if (detailQna.boardAdoption === 'Y') {
          alert('이미 채택된 글은 수정 또는 삭제하실 수 없습니다.');
          return;
        }
        const result = fetch(`${QNA}/delete/${boardIdx}`, {
          method: 'DELETE',
          headers: requestHeader,
          body: JSON.stringify({
            boardIdx: boardIdx
          })
        });
        // const result = await res;
        console.log(`result.body : ${result.body}`)

        httpStateCatcher(result.status);
        if (result.status === 200) {
          alert("삭제가 완료되었습니다.");
          redirection(-1);
        }
      } else {
        return;
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
                  {enterUserNickName === detailQna.boardWriter &&
                    <div className={'category'}>
                      <Link to={`/qna/modify/${boardIdx}`} className={'modify-btn'}
                            onClick={modifyHandler}>수정</Link>
                      <span className={'delete-btn'} onClick={deleteHandler}>삭제</span>
                    </div>
                  }

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
                {replyList.map((reply, index) => {

                  return (
                    <div className={'reply-list'} key={index}>
                      <div className={'reply-top-wrapper'}>
                        <p className={'reply-writer'}>{reply.replyWriter}</p>
                        <div className={'reply-date'}>{reply.replyDate}</div>

                        {enterUserNickName === reply.replyWriter &&
                          <>
                                                        <span className={'reply-modify'}
                                                              onClick={() => replyModifyShowHandler(index)}>수정</span>
                            <span className={'reply-delete'}
                                  onClick={() => replyDeleteHandler(reply.replyIdx)}>삭제</span>
                          </>
                        }
                      </div>
                      {/*현재 index 값 : {index} ///*/}
                      {/*현재 댓글 번호 {replyModifyShow[index] ? '트루' : '폴스'}*/}
                      {/*수정 버튼 누르면 수정 폼 뛰워주기*/}
                      <div className={'reply-content-wrapper'} key={index}>
                        {replyModifyShow[index] ?
                          <>
                                                        <textarea name={"replyContent"}
                                                                  className={'reply-modify-content'}
                                                                  defaultValue={reply.replyContent}/>
                            <button className="reply-modify-cancel-btn"
                                    onClick={() => replyModifyCancleHandler(index)}>취소
                            </button>

                            <button className="reply-modify-btn"
                                    onClick={() => replyModifyHandler(reply.replyIdx)}>수정완료
                            </button>

                          </>
                          :
                          <>
                            <input type={"hidden"} value={reply.replyIdx} name={"replyIdx"}
                                   className={'replyIdx'}/>
                            <div className={'reply-content-text'}>{reply.replyContent}</div>
                            <div>
                              {detailQna.boardAdoption === 'Y' ? (
                                checkedReplyAdoption && checkedReplyAdoption[index].replyAdoption === 'Y' ? (
                                  <button className="adoption-btn-checked">채택완료</button>
                                ) : (
                                  <button className="adoption-btn-disabled"
                                          disabled>채택불가</button>
                                )
                              ) : (
                                enterUserNickName === detailQna.boardWriter && enterUserNickName !== reply.replyWriter
                                  ? (
                                    <button className="adoption-btn"
                                            onClick={adoptHandler}>채택하기</button>
                                  )
                                  : (
                                    enterUserNickName !== reply.replyWriter ?
                                      <div className={'speechBubbleText-wrapper'}>
                                        <img src={speechBubbleText} alt={"말풍선"}
                                             className={'speechBubbleText-icon'}/>
                                        <div className="speechBubbleText"
                                        >채택 대기중
                                        </div>
                                      </div>
                                      :
                                      null


                                  )

                              )}
                            </div>
                          </>
                        }
                      </div>
                    </div>
                  )
                })}

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
  }
;

export default QnaDetail;