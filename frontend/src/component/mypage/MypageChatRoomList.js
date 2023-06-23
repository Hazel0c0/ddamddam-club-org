import React, {useEffect, useState} from 'react';
import "./scss/MypageChatRoomList.scss";
import {getToken, getUserIdx} from "../common/util/login-util";
import {BASE_URL, MYPAGE} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import less from "../../src_assets/less.png";
import than from "../../src_assets/than.png";

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
  const [prevBtn, setPrevBtn] = useState(false);
  const [nextBtn, setNextBtn] = useState(false);

  //로그인 판별
  const [checkLogin, setCheckLogin] = useState(false);

  // 캐러셀
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

    console.log(`ACCESS_TOKEN : ${ACCESS_TOKEN}`); // 토큰 잘 나옴;;
    console.log(`API_BASE_URL : ${API_BASE_URL}`); // 토큰 잘 나옴;;

    // http://localhost:8181/api/ddamddam/mypage/chat-list?page=1&size=3
    // http://localhost:8181/api/ddamddam/mypage/chat-list?page=1&size=3
    // //localhost:8181/api/ddamddam/mypage
    const res = await fetch(
      // API_BASE_URL + `/chat-list?page=${carouselIndex}&size=3`,
      `${API_BASE_URL}/chat-list?page=1&size=3`,
      {
        method: 'GET',
        headers: headerInfo,
      });

    // let result = await res.json();
    // console.log(`res : `, res);
    // console.log(`result : ${result}`);

    console.log(`res: `, res);

    if (res.status === 400) {
      alert('잘못된 요청 값 입니다.')
      return;
    } else if (res.status === 401) {
      alert('로그인이 만료되었습니다.')
      window.location.href = "/";
    } else if (res.status === 403) {
      alert('권한이 없습니다.')
      window.location.href = "/";
    } else if (res.status === 404) {
      alert('요청을 찾을 수 없습니다.');
      return;
    } else if (res.status === 500) {
      alert('잠시 후 다시 접속해주세요.[서버오류]');
      return;
    }

    const result = await res.json();

    console.log(`result : `, result);

    setChatRoomList(result.chatRoomList);
    setPageNation(result.pageInfo);

    console.log(`chatRoomList : ${chatRoomList}`);
    console.log(`pagenation : ${pageNation}`);

  };


  // 첫 렌더링 시 작성 게시글 전체 출력
  useEffect(() => {
    asyncProjectList();
  }, [carouselIndex]);

  return (
    <div className={'mypage-chat-wrapper'}>

      {pageNation.prev &&
        <img src={less} alt={"less-icon"} className={'less-icon'} onClick={handlePrevious}/>
      }
      <div className={'chat-wrapper'}>
        {chatRoomList.map((chatRoom, index) => (
          /* TODO : 멘토멘티 게시글 누르면 어디로 이동시킬건가요..?? */
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
              <p className={'chat-sub-title'}>현직</p><p>{chatRoom.current}</p>
            </div>
          </div>
        ))}
      </div>
      {pageNation.next &&
        <img src={than} alt={"than-icon"} className={'than-icon'} onClick={handleNext}/>
      }

    </div>
  );
};

MypageChatRoom.propTypes = {};

export default MypageChatRoom;
