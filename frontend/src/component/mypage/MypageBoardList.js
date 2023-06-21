import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {BASE_URL, MYPAGE} from '../common/config/HostConfig';
import {getToken} from "../common/util/login-util";
import PageNation from "../common/pageNation/PageNation";
import Common from "../common/Common";

/**
 * 내가 쓴 게시글 목록
 */
const MypageBoardList = () => {

  const ACCESS_TOKEN = getToken();
  const API_BASE_URL = BASE_URL + MYPAGE;

  // headers
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const [boardList, setBoardList] = useState([]);
  const [pageNation, setPageNation] = useState([]);
  const [prevBtn, setPrevBtn] = useState(false);
  const [nextBtn, setNextBtn] = useState(false);
  const [carouselIndex, setCarouselIndex] = useState(1);

  const asyncBoardList = async () => {

    console.log(ACCESS_TOKEN);

    const res = await fetch(API_BASE_URL + `/board-list?page=${carouselIndex}&size=10`, {
      method: 'GET',
      headers: headerInfo,
    });
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

    // 오류 없이 값을 잘 받아왔다면
    const result = await res.json();
    console.log(`boardList : ${result.boardList}`);

    setBoardList(result.boardList);
    setPageNation(result.pageInfo);

  };

  // 첫 렌더링 시 작성 게시글 전체 출력
  useEffect(() => {
    asyncBoardList();
  }, [setCarouselIndex]);


  return (
    <Common className={'mypage-list-wrapper'}>
      {boardList.map((board) => (
        <section className={'board-list'} key={board.boardIdx}>
          <div className={'board-idx'} key={board.boardIdx}>{board.boardIdx}</div>
          <div className={'board-type'} key={board.boardType}>{board.boardType}</div>
          <div className={'board-title'} key={board.boardTitle}>{board.boardTitle}</div>
          <div className={'board-date'} key={board.boardDate}>{board.boardDate}</div>
        </section>
      ))}
    </Common>
  );
};

export default MypageBoardList;
