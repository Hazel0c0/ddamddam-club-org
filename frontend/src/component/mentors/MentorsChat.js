import React, { useCallback, useEffect, useRef, useState } from 'react';
import { TfiClose } from 'react-icons/tfi';
import Common from '../common/Common';
import { useParams } from 'react-router-dom';
import { CHAT, MENTOR } from '../common/config/HostConfig';
import './scss/MentorChat.scss';
import { getToken, getUserIdx, getUserEmail, getUserName, getUserNickname, getUserRegdate,
          getUserBirth, getUserPosition, getUserCareer, getUserPoint, getUserProfile,
          getUserRole, isLogin } from '../common/util/login-util';
const MentorsChat = () => {
  const { chatPageIdx } = useParams(); // 멘토 게시판 idx
  const [detailMember, setDetailMember] = useState({}); // 멘토게시판
  const [messages, setMessages] = useState([]); // 디비 저장된 메세지
  const [chkLog, setChkLog] = useState(false);
  const [socketData, setSocketData] = useState();
  const [chat, setChat] = useState([]);
  const [msg, setMsg] = useState("");
  const name = getUserNickname(); // 접속한 유저의 닉네임
  const [chatRoom, setChatRoom] = useState([]);

  const [selectChatRoomId, setSelectChatRoomId] = useState(); // 멘토가 선택한 채팅방 idx

  const enterUserIdx = +getUserIdx(); // 접속한 유저의 idx


  const ws = useRef(null);
  const chatScroll = useRef(null);

  // 멘토가 멘티들의 채팅방 선택
  const handleSelectRoom = (e) => {
  setSelectChatRoomId(e.target.closest('.chat-room-list').querySelector('.chatRoom-idx').value);
  document.querySelector('.chat-room-list').style.display = 'none';
  fetch(CHAT + `/mentor/chatroom/detail?mentorIdx=${chatPageIdx}&roomIdx=${e.target.closest('.chat-room-list').querySelector('.chatRoom-idx').value}`)
            .then(res => {
                if (res.status === 500) {
                    alert('모지');
                    return;
                }
                return res.json();
            })
            .then((detailResult) => {
              if(detailResult !== null){
              setMessages(detailResult);
            }
            return;
            });
  };

  // 멘토가 채팅방 선택 렌더링
  const mentorsChatRoom = chatRoom.map((item, idx) => (
  <div className={'chat-room-list'} key={`${item.name}-${idx}`} onClick={handleSelectRoom}>
      <input type={'hidden'} value={item.roomId} className={'chatRoom-idx'}/>
      <span className={'mentee-nick-name'}>{item.sender.userName}</span>
      <span className={'mentee-msg-content'}>{item.message}</span>
      <span className={'mentee-date'}>{item.sentAt}</span>
    </div>
  ));

    // 멘티 채팅방 입장 후 메세지 렌더링
  const menteeMsgBox = chat.map((item, idx) => (
    <div className={item.senderId === enterUserIdx ? 'sender-wrapper' : 'receiver-wrapper'} key={`${item.name}-${idx}`}>
      <span className={item.senderId === enterUserIdx ? 'sender' : 'receiver'}>{item.name}</span>
      {/* [ {item.date} ] */}
      <span className={item.senderId === enterUserIdx ? 'sender-content' : 'receiver-content'}>{item.msg}</span>
    </div>
  ));

    // 멘토가 채팅방 입장 후 메세지 렌더링
  const mentorMsgBox = chat.map((item, idx) => (
    <div className={item.senderId === enterUserIdx ? 'sender-wrapper' : 'receiver-wrapper'} key={`${item.name}-${idx}`}>
      <span className={item.senderId === enterUserIdx ? 'sender' : 'receiver'}>{item.name}</span>
      {/* [ {item.date} ] */}
      <span className={item.senderId === enterUserIdx ? 'sender-content' : 'receiver-content'}>{item.msg}</span>
    </div>
  ));

  // 디비에 저장된 멘티 메세지 렌더링
  const menteeMsgRender = messages.map((item, idx) => (
    <div className={'sender-wrapper'} key={`${item.name}-${idx}`}>
      <span className={'sender'}>{item.sender.userNickname}</span>
      <span className={'sender-content'}>{item.message}</span>
    </div>
  ));

  
// 렌더링
  useEffect(() => {
    // 멘토 상세 정보 조회
    fetch(MENTOR + '/detail?mentorIdx=' + chatPageIdx)
      .then((res) => {
        if (res.status === 500) {
          alert('잠시 후 다시 접속해주세요.[서버오류]');
          return;
        }
        return res.json();
      })
      .then((result) => {
        setDetailMember(result);
        console.log(result);
        if(result.userIdx !== enterUserIdx){
        // if(result.userIdx !== 1){
          fetch(CHAT + '/mentee/list/' + chatPageIdx)
            .then((detailRes) => detailRes.json())
            .then((detailResult) => {
              setMessages(detailResult);
            });
            }else{
              fetch(CHAT + '/mentor/list/' + chatPageIdx)
            .then((allRes) => {
              if (allRes.status === 500) {
                  alert('참여중인 멘티가 없습니다');
                  return;
              }
              return allRes.json();
          })
            .then((allResult) => {
              if(allResult[0].message !== null){
              setChatRoom(allResult);
              console.log(allResult);
              }
              return;
            });
            }
      });
    
  }, [chatPageIdx]);


 // 보낸 채팅 메세지 담기
  useEffect(() => {
    if(socketData !== undefined) {
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

// 소켓 연결
// 메세지 컨트롤러 보내기
const webSocketLogin = useCallback(() => {
  ws.current = new WebSocket("ws://localhost:8181/socket/chat");
  console.log('socket');
  ws.current.onmessage = (message) => {
      const dataSet = JSON.parse(message.data);
      setSocketData(dataSet);
  }
});

// 메세지 전송
const saveMessage = useCallback(() => {
  const messageToSave = {
    roomId: chatPageIdx,
    senderId: enterUserIdx,
    message: msg,
  };

  fetch(CHAT + '/rooms/' + chatPageIdx + '/messages', {
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
}, [chatPageIdx, enterUserIdx, msg]);

const send = useCallback(() => {
  if (!chkLog) {
    webSocketLogin();
    setChkLog(true);
  }
  if (msg !== '') {
    const data = {
      mentorIdx: chatPageIdx,
      senderId: enterUserIdx,
      roomId: selectChatRoomId,
      name,
      msg,
      date: new Date().toLocaleString('ko-KR', { hour: 'numeric', minute: 'numeric', hour12: true }),
    };

    const temp = JSON.stringify(data);

    if (ws.current.readyState === 0) {
      ws.current.onopen = () => {
        console.log(ws.current.readyState);
        ws.current.send(temp);
        saveMessage(); // 메시지 저장 및 스크롤 조정
      };
    } else {
      ws.current.send(temp);
      saveMessage(); // 메시지 저장 및 스크롤 조정
    }

    setMsg('');
  } else {
    alert('메세지를 입력하세요.');
    document.getElementById('msg').focus();
  }
});

//webSocket


console.log(enterUserIdx);


  useEffect(() => {
    chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
  }, [messages]);

  useEffect(() => {
    chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
  }, [chat]);

  
  const { career, content, current, date, idx, mentee, nickName, profile, subject, title } = detailMember;

  return (
    <Common className={'mentors-chat-wrapper'}>
      <div className={'mentor-detail-wrapper'}>
        <section className={'top-section'}>
          <div className={'top-title'}>
            <h1 className={'top-title-text'}>멘토 소개</h1>
            <div className={'write-date'}>{date}</div>
          </div>
          <div className={'close-btn'}>
            <TfiClose />
          </div>
        </section>

        <section className={'writer-section'}>
          <div className={'detail-profile-img'}></div>
          <div className={'writer-text-wrapper'}>
            <h2 className={'detail-writer'}>{nickName}</h2>
            <h3 className={'detail-sub-title'}>{title}</h3>
            <div className={'etc-wrapper'}>
              <div className={'member-count'}>
                <p className={'detail-sub-text'}>인원</p>
                {mentee}명 모집
              </div>
              <div className={'subject'}>
                <p className={'detail-sub-text'}>주제</p>
                {subject}
              </div>
              <div className={'career'}>
                <p className={'detail-sub-text'}>경력</p>
                {career}
              </div>
              <div className={'current'}>
                <p className={'detail-sub-text'}>현직</p>
                {current}
              </div>
            </div>
          </div>
        </section>

        <section className={'main-section'}>
          <div className={'main-section-text'}>{content}</div>
        </section>

        <div className={'btn-wrapper'}>
          <button className={'application-btn'}>멘토링중</button>
        </div>
      </div>

      <div className={'mentor-chat-room'}>
        <section className={'chating-list'} ref={chatScroll}>
          {mentorsChatRoom}
          {menteeMsgRender}
          {mentorMsgBox}
          {menteeMsgBox}
        </section>

        <section className={'input-section'}>
          {/* <textarea
            className={'text-input'}
            value={input}
            onChange={handleInputChange}
            placeholder={'대화를 입력해 멘토님과 이야기를 나눠보세요!'}
          ></textarea>
          <button onClick={handleSubmit} className={'send-btn'}>
            Send
          </button> */}

          {/* <input disabled={chkLog}
                        placeholder='이름을 입력하세요.' 
                        type='text' 
                        id='name' 
                        value={name} 
                        onChange={(event => setName(event.target.value))}/> */}
                    {/* <div id='sendZone'> */}
                        <textarea 
                          className={'text-input'} 
                          value={msg} onChange={onText}
                          onKeyDown={(ev) => {if(ev.keyCode === 13){send();}}}
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
