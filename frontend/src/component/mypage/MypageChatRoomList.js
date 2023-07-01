import React, {useEffect, useState} from 'react';
import "./scss/MypageChatRoomList.scss";
import {getToken} from "../common/util/login-util";
import {BASE_URL, MYPAGE} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import less from "../../src_assets/less.png";
import than from "../../src_assets/than.png";
import {httpStateCatcher} from "../common/util/HttpStateCatcherWrite";

const MypageChatRoom = props => {

  const ACCESS_TOKEN = getToken();
  const API_BASE_URL = BASE_URL + MYPAGE;
  const redirection = useNavigate();

  // headers
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const [chatRoomList, setChatRoomList] = useState([]);
  const [pageNation, setPageNation] = useState([]);
  const [carouselIndex, setCarouselIndex] = useState(1);

  const subStringContent = (str, n) => {
    return str?.length > n
      ? str.substr(0, n - 1) + "..."
      : str;
  }

  const handlePrevious = () => {
    if (pageNation.prev === true) {
      setCarouselIndex(prevIndex => prevIndex - 1);
    }
  };

  const handleNext = () => {
    if (pageNation.next === true) {
      setCarouselIndex(prevIndex => prevIndex + 1);
    }
  };

  // 로그인 상태 검증 핸들러
  const loginCheckHandler = (e) => {
    console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
    if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
      alert('로그인 후 이용가능합니다.')
      e.preventDefault();
      redirection('/login');
    }
  }

  const asyncProjectList = async () => {

    // console.log(`ACCESS_TOKEN : ${ACCESS_TOKEN}`); // 토큰 잘 나옴;;

    // http://localhost:8181/api/ddamddam/mypage/chat-list?page=1&size=3
    const res = await fetch(
      `${API_BASE_URL}/chat-list?page=${carouselIndex}&size=3`,
      {
        method: 'GET',
        headers: headerInfo,
      });

    httpStateCatcher(res.status);

    const result = await res.json();

    // console.log(`result : `, result);

    setChatRoomList(result.chatRoomList);
    setPageNation(result.pageInfo);

    // console.log(`chatRoomList : ${chatRoomList}`);
    // console.log(`pagenation : ${pageNation}`);
  };

  // 첫 렌더링 시 작성 게시글 전체 출력
  useEffect(() => {
    asyncProjectList();
  }, [carouselIndex]);

  return (
    <div className={'mypage-chat-wrapper'}>

      {chatRoomList.length === 0 ? (
        <div>참여중인 멘토링이 없습니다.</div>
      ) : null}

      {pageNation.prev &&
        <img src={less} alt={"less-icon"} className={'mypage-less-icon'} onClick={handlePrevious}/>
      }
      <div className={'chat-wrapper'}>
        {chatRoomList.map((chatRoom, index) => (
          <div className={'chat-box'} key={`${chatRoom.roomIdx}-${index}`}>
            <input type={'hidden'} value={chatRoom.roomIdx} className={'chat-room-idx'}/>
            <Link to={`/mentors/detail/chat/${chatRoom.mentorIdx}/${chatRoom.roomIdx}`} onClick={loginCheckHandler}>
              <div className={'chat-title'}>
                {subStringContent(chatRoom.title, 35)}
              </div>
            </Link>
            <div className={'chat-submenu-box'}>
              <p className={'chat-sub-title'}>주제</p><p>{chatRoom.subject}</p>
            </div>
            <div className={'chat-submenu-box'}>
              <p className={'chat-sub-title'}>경력</p><p>{chatRoom.current !== '신입' ? chatRoom.current+'년' : chatRoom.current}</p>
            </div>
          </div>
        ))}
      </div>
      {pageNation.next &&
        <img src={than} alt={"than-icon"} className={'mypage-than-icon'} onClick={handleNext}/>
      }

    </div>
  );
};

MypageChatRoom.propTypes = {};

export default MypageChatRoom;
