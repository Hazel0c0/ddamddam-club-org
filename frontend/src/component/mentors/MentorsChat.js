import React, {useCallback, useEffect, useRef, useState} from 'react';
import {TfiClose} from 'react-icons/tfi';
import Common from '../common/Common';
import {Link, useParams} from 'react-router-dom';
import {CHAT, MENTOR, SOCKET_URL} from '../common/config/HostConfig';
import './scss/MentorChat.scss';
import {getToken, getUserIdx, getUserNickname, getUserRegdate} from '../common/util/login-util';
import {ChatSharp, Window} from '@mui/icons-material';
import {BsPersonFill} from "react-icons/bs";
import back from "../../src_assets/back.png";

const MentorsChat = () => {
  const {chatPageIdx, roomId} = useParams(); // 멘토 게시판 idx
  const [detailMember, setDetailMember] = useState({}); // 멘토게시판
  const [messages, setMessages] = useState([]); // 디비 저장된 메세지
  const [chkLog, setChkLog] = useState(false);
  const [socketData, setSocketData] = useState();
  const [chat, setChat] = useState([]); // 멘토,멘티 해당 채팅룸 메세지 렌더링
  const [msg, setMsg] = useState(""); // 소켓 메세지
  const name = getUserNickname(); // 접속한 유저의 닉네임
  const [chatRoom, setChatRoom] = useState([]); // 멘토가 선택한 채팅룸 정보
  const [selectChatRoomId, setSelectChatRoomId] = useState(); // 멘토가 선택한 채팅방 idx
  const enterUserIdx = +getUserIdx(); // 접속한 유저의 idx
  const ACCESS_TOKEN = getToken(); // 토큰
  const [menteeCount, setMenteeCount] = useState(0); // 멘티 확정 시 멘티 숫자 올리기
  const [menteeCountList, setMenteeCountList] = useState([]);
  const [applyMenteeList, setApplyMenteeList] = useState([]); // 멘토링 확정된 멘티 리스트
  const subStringContent = (str, n) => { // 메세지 길이 자르기
    return str?.length > n
      ? str.substr(0, n - 1) + "..."
      : str;
  }

  // headers
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }


  const ws = useRef(null);
  const chatScroll = useRef(null);
  // 삭제하기
  const handleDelete = e => {
    if (window.confirm('삭제하시겠습니까?')) {
      fetch(`${MENTOR}/${idx}`, {
        method: 'DELETE',
        headers: headerInfo
      })
        .then(res => res.json())
        .then(json => {
          window.location.href = '/mentors';
        });
    } else {
      return;
    }
  };

  // 멘토한정 채팅방으로 돌아가기
  const backChatRoomList = () => {
    document.querySelector('.mentor-back-room').style.display = 'none';
    window.location.assign('/mentors/detail/chat/' + chatPageIdx + '/' + roomId);
  };

  // 멘토가 멘티들의 채팅방 선택
  const handleSelectRoom = (e) => {
    setSelectChatRoomId(e.target.closest('.chat-room-list').querySelector('.chatRoom-idx').value);
    const elements = document.querySelectorAll('.chat-room-list');
    elements.forEach(element => {
      element.style.display = 'none';
    });

    document.querySelector('.input-section').style.display = 'flex';
    document.querySelector('.mentor-back-room').style.display = 'block';

    const menteeBtn = document.querySelector('.application-btn');
    menteeBtn.textContent = '멘토 확정';
    menteeBtn.style.pointerEvents = 'auto';

    fetch(CHAT + `/mentor/chatroom/detail?mentorIdx=${chatPageIdx}&roomIdx=${e.target.closest('.chat-room-list').querySelector('.chatRoom-idx').value}&senderIdx=${e.target.closest('.chat-room-list').querySelector('.sender-idx').value}`, {
      method: 'GET',
      headers: headerInfo
    })
      .then(res => {
        if (res.status === 500) {
          return;
        }
        return res.json();
      })
      .then((detailResult) => {
        if (detailResult !== undefined) {
          setMessages(detailResult);
        }
        return;
      });
  };

  // 멘티 확정 시 렌더링
  const menteeCountUp = e => {

    // menteeCountList.forEach((mentee) => {
    //     if (mentee.userIdx === chatRoom[0].sender.userIdx) {
    //       console.log(`Found user: ${mentee}`);
    //     }
    //   });
    if(chatRoom === undefined){
      alert('return');
      return;
    }
    const saveMenteeCount = menteeCountList.length;

    if (window.confirm(chatRoom[0].sender.userNickname + '님을 멘티로 확정하시겠습니까?')) {
      if (detailMember.userIdx === enterUserIdx && selectChatRoomId !== undefined) {
        fetch(MENTOR + '/mentee/' + chatPageIdx + "/" + selectChatRoomId, {
          method: 'PUT',
          headers: headerInfo
        })
          .then((res) => {
            if(res.status === 400){
              alert('이미 확정된 멘티입니다');
              setMenteeCount(saveMenteeCount);
              return;
            } 
            return res.json();
          })
          .then((result) => {
            setMenteeCount(result);
          });
      }
    }
  };

  const delChatRoom = (e) => {

    const delRoomIdx = e.target.closest('.chat-room-list').querySelector('.chatRoom-idx').value;

    if (window.confirm('채팅방을 삭제하시겠습니까?')) {
      fetch(CHAT + "/" + delRoomIdx + "/" + chatPageIdx, {
        method: 'DELETE',
        headers: headerInfo
      })
        .then((res) => {
          return res.json();
        })
        .then((result) => {
          console.log('삭제하기');
          window.location.href('/mentors/detail/chat/' + chatPageIdx);
          setChatRoom(result);
        });
    }
  };


  // 멘토가 채팅방 선택 렌더링
  const mentorsChatRoom =
    chatRoom.map((item, idx) => {
      if(chatRoom !== undefined){
      return (
      <div className={'chat-room-list'} key={`${item.name}-${idx}`} onClick={handleSelectRoom}>
        <input type={'hidden'} value={item.sender.userIdx} className={'sender-idx'}/>
        <input type={'hidden'} value={item.roomId} className={'chatRoom-idx'}/>
        {
            item.sender.userProfile !== 'null'
            ? <img src={item.sender.userProfile} className={'profile-img'}/>
            : <img className={'profile-img'}/>
        }

        <span className={'mentee-nick-name'}>{item.sender.userName}</span>
        <span className={'mentee-msg-content'}>{subStringContent(item.message, 5)}</span>
        <span className={'mentee-date'}>{item.sentAt}</span>
        <button className={'chatroom-delbtn'} onClick={delChatRoom}>삭제</button>
      </div>
    )
  }
  return null;
  });

  // 멘티 채팅방 입장 후 메세지 렌더링
  const menteeMsgBox = chat.map((item, idx) => {
    if (+item.roomId === +selectChatRoomId || +item.roomId === +roomId) {
      return (
        <div className={item.senderId === enterUserIdx ? 'sender-wrapper' : 'receiver-wrapper'}
             key={`${item.name}-${idx}`}>
          <span className={item.senderId === enterUserIdx ? 'sender' : 'receiver'}>{item.name}</span>
          <span className={item.senderId === enterUserIdx ? 'sender-content' : 'receiver-content'}>{item.msg}</span>
        </div>
      );
    }
  });

// 멘토가 채팅방 입장 후 메세지 렌더링
  const mentorMsgBox = chat.map((item, idx) => {

    if (+item.roomId === +selectChatRoomId) {
      return (
        <div className={item.senderId === enterUserIdx ? 'sender-wrapper' : 'receiver-wrapper'}
             key={`${item.name}-${idx}`}>
          <span className={item.senderId === enterUserIdx ? 'sender' : 'receiver'}>{item.name}</span>
          <span
            className={item.senderId === enterUserIdx ? 'sender-content' : 'receiver-content'}>{item.msg}</span>
        </div>
      );

    }
    return null;
  });

  // 디비에 저장된 멘티 메세지 렌더링
  const menteeMsgRender = messages != undefined && messages.map((item, idx) => (
    <div className={item.sender.userIdx === enterUserIdx ? 'sender-wrapper' : 'receiver-wrapper'}
         key={`${item.name}-${idx}`}>
            <span
              className={item.sender.userIdx === enterUserIdx ? 'sender' : 'receiver'}>{item.sender.userNickname}</span>
      <span
        className={item.sender.userIdx === enterUserIdx ? 'sender-content' : 'receiver-content'}>{item.message}</span>
    </div>
  ));


// 렌더링
  useEffect(() => {
    // 확정 멘티 리스트 조회

    fetch(MENTOR + '/mentee/list/' + chatPageIdx, {
      method: 'GET',
      headers: headerInfo,
    }).then((res) => {
      return res.json();
    }).then((result) => {
      setApplyMenteeList(result.menteeResponseDTOList);
      console.log('applyMenteeList:', result.menteeResponseDTOList);
    }).catch((error) => {
      console.log('네트워크 요청 오류:', error);
    });

    // 멘토 상세 정보 조회
    fetch(MENTOR + '/detail?mentorIdx=' + chatPageIdx, {
      method: 'GET',
      headers: headerInfo
    }).then((res) => {
      if (res.status === 500) {
        alert('잠시 후 다시 접속해주세요.[서버오류]');
        return;
      }
      return res.json();
    })
      .then((result) => {
        console.log(result.completeMentee);
        setDetailMember(result);
        setMenteeCount(result.completeMentee)
        setMenteeCountList(result.menteeList);
        console.log(result);
        if (result.userIdx !== enterUserIdx) {
          fetch(CHAT + '/mentee/list/' + chatPageIdx
            , {
              method: 'GET',
              headers: headerInfo
            })
            .then((detailRes) => detailRes.json())
            .then((detailResult) => {
              setMessages(detailResult);
              if (detailResult[0] !== undefined) {
                setSelectChatRoomId(detailResult[0].roomId);
              }
            });
        } else {
          document.querySelector('.input-section').style.display = 'none';
          fetch(CHAT + '/mentor/list/' + chatPageIdx)
            .then((allRes) => {
              if (!allRes.ok) {
                alert('참여중인 멘티가 없습니다');
                return;
              }
              return allRes.json();
            })
            .then((allResult) => {
              if (allResult != undefined) {
                setChatRoom(allResult);
                console.log(allResult);
              }
              return;
            });
        }
      });

  }, [chatPageIdx]);


// 소켓 연결
// 메세지 컨트롤러 보내기
  useEffect(() => {
    const webSocketLogin = () => {
      ws.current = new WebSocket("ws://" + SOCKET_URL + "/socket/chat");

      // ws.current.onopen = () => {
      //   console.log("WebSocket 연결 성공");
      // };
      ws.current.onmessage = (message) => {
        try {
          const dataSet = JSON.parse(message.data);
          const filteredData = Array.isArray(dataSet) ? dataSet.filter((item) => +item.roomId === selectChatRoomId) : [dataSet];
          setSocketData(filteredData);
        } catch (error) {
          console.error("Error parsing WebSocket message:", error);
        }
      };


    };
    webSocketLogin();

    return () => {
      // 컴포넌트가 언마운트될 때 WebSocket 연결을 정리합니다.
      ws.current.close();
    };
  }, []);

// 보낸 채팅 메세지 담기
  useEffect(() => {
    if (socketData !== undefined) {
      const tempData = chat.concat(socketData);
      console.log(tempData);
      setChat(tempData);
    }
  }, [socketData]);

// 입력한 메세지 담기
  const onText = event => {
    console.log(event.target.value);
    setMsg(event.target.value);
  }

// 메세지 
  const saveMessage = useCallback(() => {
    const messageToSave = {
      roomId: chatPageIdx,
      senderId: enterUserIdx,
      message: msg,
    };
    if (detailMember.userIdx === enterUserIdx) {
      fetch(CHAT + '/mentor/' + selectChatRoomId + '/messages', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(messageToSave),
      })
        .then((res) => res.json())
        .then((result) => {
          chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
        });
    } else {
      fetch(CHAT + '/mentee/' + chatPageIdx + '/messages', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(messageToSave),
      })
        .then((res) => res.json())
        .then((result) => {
          chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
        });
    }
  }, [chatPageIdx, enterUserIdx, msg]);

  const send = () => {
    if (!chkLog) {
      setChkLog(true);
    }

    if (msg !== '') {
      const data = {
        mentorIdx: chatPageIdx,
        senderId: enterUserIdx,
        roomId: selectChatRoomId || roomId,
        name,
        msg,
        date: new Date().toLocaleString('ko-KR', {hour: 'numeric', minute: 'numeric', hour12: true}),
      };

      const temp = JSON.stringify(data);

      if (ws.current.readyState === WebSocket.OPEN) {
        ws.current.send(temp);
        saveMessage(); // 메시지 저장 및 스크롤 조정

      } else {
        alert('WebSocket 연결이 열려 있지 않습니다.');
      }

      setMsg('');
    } else {
      alert('메시지를 입력해주세요.');
      document.getElementById('msg').focus();
    }
  };

//webSocket


  console.log('접속한 userIdx: ' + enterUserIdx);


  useEffect(() => {
    chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
  }, [messages]);

  useEffect(() => {
    chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
  }, [chat]);


  const {career, content, current, date, idx, mentee, nickName, profile, subject, title} = detailMember;

    return (
        <Common className={'mentors-chat-wrapper'}>
            <div className={'mentor-detail-wrapper'}>
              <Link to={'/mentors'} className={'back-btn'}>
                <img src={back} alt={'back-icon'} className={'back-icon'}/>
                {/*<span className={'back-text'}>Back</span>*/}
              </Link>
                <section className={'top-section'}>
                    <div className={'top-title'}>
                        <h1 className={'top-title-text'}>멘토 소개</h1>
                        <div className={'write-date'}>{date}</div>

                        {detailMember.userIdx === enterUserIdx &&
              <>
                <div className={'writer-wrapper'}>
                  <Link to={`/mentors/modify/${idx}`} className={'modify-btn'}>
                    수정
                  </Link>
                  <div className={'delete-btn'} onClick={handleDelete}>삭제</div>
                </div>
              </>
            }
                    </div>
                </section>

        <section className={'writer-section'}>
          {
            profile !== 'null'
              ? <div className={'detail-profile-img'} style={{backgroundImage: `url(${profile})`}}>
              </div>
              : <div className={'detail-profile-img'}></div>
          }
          <div className={'writer-text-wrapper'}>
            <h2 className={'detail-writer'}>{nickName}</h2>
            <h3 className={'detail-sub-title'}>{title}</h3>
            <div className={'etc-wrapper'}>
              <div className={'member-count'}>
                <p className={'detail-sub-text'}>인원</p>
                {mentee}명 모집
              </div>
              <div className={'mentor-career'}>
                <p className={'detail-sub-text'}>모집완료</p>
                {menteeCount} 명
              </div>
              <div className={'subject'}>
                <p className={'detail-sub-text'}>주제</p>
                {subject}
              </div>
              <div className={'current'}>
                <p className={'detail-sub-text'}>현직</p>
                {current}
              </div>
            </div>

            {/*확정 멘티*/}
            <div className={'mentee-wrapper'}>
              <div className={'mentee-title'}>확정 멘티</div>
              <div className={'mentee-box'}>
                {applyMenteeList.map((mentee, index) => (
                  <p className={'mentee'} key={`${mentee.menteeIdx}-${index}`}><BsPersonFill size='20' />{mentee.menteeNickname} </p>
                ))}
              </div>
            </div>

          </div>
        </section>

        <section className={'main-section'}>
          <div className={'main-section-text'}>{content}</div>
        </section>

        <div className={'btn-wrapper'}>
          <button
            className={'application-btn'} onClick={menteeCountUp}>
            {/* {detailMember.userIdx === enterUserIdx ? '멘티 확정' : '멘토링중'} */}
            멘토링중
          </button>
          <button className={'mentor-back-room'} onClick={backChatRoomList}>채팅방 돌아가기</button>
        </div>
      </div>

      <div className={'mentor-chat-room'}>

        <section className={'chating-list'} ref={chatScroll}>

          {/*디비 메세지 렌더링 */}
          {menteeMsgRender}

          {/*멘토 채팅방*/}
          {detailMember.userIdx === enterUserIdx &&
            <>
              {mentorsChatRoom}
              {mentorMsgBox}
            </>
          }

          {/*멘토 채팅방*/}
          {detailMember.userIdx !== enterUserIdx &&
            <>
              {menteeMsgBox}
            </>
          }
        </section>

        <section className={'input-section'}>
                        <textarea
                          className={'text-input'}
                          value={msg} onChange={onText}
                          onKeyDown={(ev) => {
                            if (ev.keyCode === 13) {
                              send();
                            }
                          }}
                          placeholder={'대화를 입력해 멘토님과 이야기를 나눠보세요!'}>
                </textarea>


          <button className={'send-btn'} onClick={send}>
            SEND
          </button>
          {/* </div> */}

        </section>
      </div>
    </Common>
  );
};

export default MentorsChat;
