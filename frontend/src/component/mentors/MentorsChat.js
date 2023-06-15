import React, { useCallback, useEffect, useRef, useState } from 'react';
import { TfiClose } from 'react-icons/tfi';
import Common from '../common/Common';
import { useParams } from 'react-router-dom';
import { CHAT, MENTOR } from '../common/config/HostConfig';
import './scss/MentorChat.scss';

const MentorsChat = () => {
  const { chatPageIdx } = useParams();
  const [detailMember, setDetailMember] = useState({});
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const [chkLog, setChkLog] = useState(false);
  const [socketData, setSocketData] = useState();
  const [chat, setChat] = useState([]);
  const [msg, setMsg] = useState("");
  const [name, setName] = useState("");


  const ws = useRef(null);
  const chatScroll = useRef(null);

  const msgBox = chat.map((item, idx) => (
    <div key={idx} className={item.name === name ? 'me' : 'other'}>
      <span><b>{item.name}</b></span> [ {item.date} ]<br/>
      <span>{item.msg}</span>
    </div>
  ));

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
      });

    fetch(CHAT + '/mentee/list/' + chatPageIdx)
      .then((res) => res.json())
      .then((result) => {
        setMessages(result);
      });

    // 컴포넌트가 마운트되었을 때 웹소켓 연결
//     ws.current = new WebSocket('ws://localhost:8181/chat'); // 웹소켓 서버 주소
//     ws.current.onopen = () => {
//       console.log('WebSocket 연결 성공');
//       ws.current.send(JSON.stringify({ type: 'SUBSCRIBE', topic: '/topic/chat' }));
//     };

//     ws.current.onmessage = (event) => {
//     // 새로운 메시지 도착 시 처리
//       const newMessage = JSON.parse(event.data);
//       setMessages((prevMessages) => [...prevMessages, newMessage]);
//       chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
//     };

// // 컴포넌트가 언마운트될 때 웹소켓 연결 해제
//      return () => {
//        ws.current.close();
//      };
  }, [chatPageIdx]);







  useEffect(() => {
    if(socketData !== undefined) {
        const tempData = chat.concat(socketData);
        console.log(tempData);
        setChat(tempData);
    }
}, [socketData]);

const onText = event => {
  console.log(event.target.value);
  setMsg(event.target.value);
}


const webSocketLogin = useCallback(() => {
  ws.current = new WebSocket("ws://localhost:8181/socket/chat");

  ws.current.onmessage = (message) => {
      const dataSet = JSON.parse(message.data);
      setSocketData(dataSet);
  }
});
const send = useCallback(() => {
  if(!chkLog) {
      if(name === "") {
          alert("이름을 입력하세요.");
          document.getElementById("name").focus();
          return;
      }
      webSocketLogin();
      setChkLog(true);
  }

  if(msg !== ''){
      const data = {
          name,
          msg,
          date: new Date().toLocaleString(),
      };  //전송 데이터(JSON)

      const temp = JSON.stringify(data);
      
      if(ws.current.readyState === 0) {   //readyState는 웹 소켓 연결 상태를 나타냄
          ws.current.onopen = () => { //webSocket이 맺어지고 난 후, 실행
              console.log(ws.current.readyState);
              ws.current.send(temp);
          }
      }else {
          ws.current.send(temp);
      }
  }else {
      alert("메세지를 입력하세요.");
      document.getElementById("msg").focus();
      return;
  }
  setMsg("");
});
//webSocket





  useEffect(() => {
    chatScroll.current.scrollTop = chatScroll.current.scrollHeight;
  }, [messages]);

  const handleInputChange = (event) => {
    setInput(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (input.trim() === '') return;

// 메시지 전송
    const chatMessage = {
      roomId: chatPageIdx,
      message: input,
      senderId: 1,
    };
    ws.current.send(JSON.stringify(chatMessage));
    setInput('');

    const messageToSave = {
      roomId: chatPageIdx,
      senderId: 1,
      message: input,
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
  };

//  console.log(messages);

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
          {messages.map((message, index) => (
            <div className={'sender-wrapper'} key={index}>
              <span className={'sender'}>{message.sender.userNickname}</span>
              <span className={'sender-content'}>{message.message}</span>
            </div>
          ))}
          {msgBox}
        </section>

        <section className={'input-section'}>
          <textarea
            className={'text-input'}
            value={input}
            onChange={handleInputChange}
            placeholder={'대화를 입력해 멘토님과 이야기를 나눠보세요!'}
          ></textarea>
          <button onClick={handleSubmit} className={'send-btn'}>
            Send
          </button>

          <input disabled={chkLog}
                        placeholder='이름을 입력하세요.' 
                        type='text' 
                        id='name' 
                        value={name} 
                        onChange={(event => setName(event.target.value))}/>
                    <div id='sendZone'>
                        <textarea id='msg' value={msg} onChange={onText}
                            onKeyDown={(ev) => {if(ev.keyCode === 13){send();}}}></textarea>
                        <input type='button' value='전송' id='btnSend' onClick={send}/>
                    </div>

        </section>
      </div>
    </Common>
  );
};

export default MentorsChat;
