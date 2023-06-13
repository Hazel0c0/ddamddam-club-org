import React, {useEffect, useRef, useState} from 'react';
import {TfiClose} from "react-icons/tfi";
import Common from "../common/Common";
import {useParams} from "react-router-dom";
import {CHAT, MENTOR} from "../common/config/HostConfig";
import './scss/MentorChat.scss';

const MentorsChat = () => {
    const {chatPageIdx} = useParams();
    const [detailMember, setDetailMember] = useState([]);
    const [messages, setMessages] = useState([]); // 채팅 메시지 목록
    const [input, setInput] = useState(''); // 입력된 메시지
    const ws = useRef(null); // WebSocket 객체
    const [message, setMessage] = useState([]);
    const [checkSender, setCheckSender] = useState([]);
    useEffect(() => {
        fetch(MENTOR + '/detail?mentorIdx=' + chatPageIdx)
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                setDetailMember(result);
                console.log(result);
                console.log(result.idx);
            });
            // 컴포넌트가 마운트되었을 때 웹소켓 연결
            ws.current = new WebSocket('ws://localhost:8181/chat'); // 웹소켓 서버 주소
        ws.current.onopen = () => {
        console.log('WebSocket 연결 성공');
            };
    ws.current.onmessage = (event) => {
      // 새로운 메시지 도착 시 처리
      const message = JSON.parse(event.data);
      setMessages((prevMessages) => [...prevMessages, message]);
    };
    
    fetch(CHAT + '/mentee/list/' + chatPageIdx)
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                console.log('잘됨');
                console.log(result);
                setMessage(result);
                // setCheckSender(result.sender);
            });
    // 컴포넌트가 언마운트될 때 웹소켓 연결 해제
    return () => {
      ws.current.close();
    };
    }, []);

    const handleInputChange = (event) => {
    setInput(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (input.trim() === '') return;

    // 메시지 전송
    const message = {
      user: 'User Name', // 사용자 이름
      content: input, // 입력된 메시지 내용
    };
    ws.current.send(JSON.stringify(message));
    setInput('');
  };

    const {career, content, current, date, idx, mentee, nickName, profile, subject, title} = detailMember;

    return (
        <Common className={'mentors-chat-wrapper'}>
            <div className={'mentor-detail-wrapper'}>
                <div>
                    {/* {message[0].message} */}
                </div>
                <section className={'top-section'}>
                    <div className={'top-title'}>
                        <h1 className={'top-title-text'}>멘토 소개</h1>
                        <div className={'write-date'}>{date}</div>
                    </div>

                    <div className={'close-btn'}><TfiClose/></div>
                </section>

                <section className={'writer-section'}>
                    <div className={'detail-profile-img'}></div>
                    <div className={'writer-text-wrapper'}>
                        <h2 className={'detail-writer'}>{nickName}</h2>
                        <h3 className={'detail-sub-title'}>{title}</h3>
                        <div className={'etc-wrapper'}>
                            <div className={'member-count'}><p className={'detail-sub-text'}>인원</p>{mentee}명 모집</div>
                            <div className={'subject'}><p className={'detail-sub-text'}>주제</p>{subject}</div>
                            <div className={'career'}><p className={'detail-sub-text'}>경력</p>{career}</div>
                            <div className={'current'}><p className={'detail-sub-text'}>현직</p>{current}</div>
                        </div>
                    </div>
                </section>

                <section className={'main-section'}>
                    <div className={'main-section-text'}>
                        {content}
                    </div>
                </section>

                <div className={'btn-wrapper'}>
                    <button className={'application-btn'}>멘토링중</button>
                </div>
            </div>

            <div className={'mentor-chat-room'}>
                <section className={'chating-list'}>
                    <div className={'sender-wrapper'}>
                        <span className={'sender-content'}>안녕하세요 발신자안녕하세요 발신자안녕하세요 발신자안녕하세요 발신자안녕하세요 발신자</span>
                    </div>
                    <div className={'receiver-wrapper'}>
                        <span className={'receiver'}>홍길동</span>
                        <span className={'receiver-content'}>안녕하세요 수신자</span>
                    </div>
                </section>

                <section className={'input-section'}>
                    <textarea className={'text-input'} value={input} onChange={handleInputChange} placeholder={'대화를 입력해 멘토님과 이야기를 나눠보세요!'} ></textarea>
                    <button onClick={handleSubmit} className={'send-btn'}>Send</button>
                </section>
            </div>
        </Common>


    );
};

export default MentorsChat;