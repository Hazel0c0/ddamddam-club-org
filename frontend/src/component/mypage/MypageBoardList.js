import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {BASE_URL, MYPAGE, QNA, MENTOR, PROJECT, REVIEW} from '../common/config/HostConfig';
import {getToken} from "../common/util/login-util";
import PageNation from "../common/pageNation/PageNation";
import Common from "../common/Common";
import {Link, useNavigate} from "react-router-dom";


/**
 * 내가 쓴 게시글 목록
 */
const MypageBoardList = () => {

  const ACCESS_TOKEN = getToken();
  const API_BASE_URL = BASE_URL + MYPAGE;
  const redirection = useNavigate();

  // headers
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const [boardList, setBoardList] = useState([]);
  const [pageNation, setPageNation] = useState([]);
  const [clickCurrentPage, setClickCurrentPage] = useState(1);
  const [prevBtn, setPrevBtn] = useState(false);
  const [nextBtn, setNextBtn] = useState(false);
  const [carouselIndex, setCarouselIndex] = useState(1);

  // 로그인 상태 검증 핸들러

  const loginCheckHandler = (e) => {
    console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
    if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
      alert('로그인 후 이용가능합니다.')
      e.preventDefault();
      redirection('/login');
    }
  }

  //현재 페이지 설정
  const currentPageHandler = (clickPageNum) => {
    console.log(`페이지 클릭 시 현재 페이지 번호 : ${clickPageNum}`)
    setClickCurrentPage(clickPageNum);
  }

  // 내가 쓴 게시글 목록 뿌려주기
  const asyncBoardList = async () => {

    console.log(`ACCESS_TOKEN : ${ACCESS_TOKEN}`); // 토큰 잘 나옴;;

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
    // console.log(`pageInfo : ${result.pageInfo}`);
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
          <div className={'board-type'} key={board.boardType}>{board.boardType}</div>
          <div className={'board-title'} key={board.boardTitle}>
            {
              board.boardType === 'Q&A' &&
              <Link to={`/api/ddamddam/qna/${board.boardIdx}`} onClick={loginCheckHandler}>
                {board.boardTitle}
              </Link>
            }
            {
              /* TODO : 멘토멘티 게시글 누르면 어디로 이동시킬건가요..?? */
              board.boardType === '멘토/멘티' &&
              <Link to={`/api/ddamddam/mentor/detail/${board.boardIdx}`} onClick={loginCheckHandler}>
                {board.boardTitle}
              </Link>
            }
            {
              board.boardType === '프로젝트 모집' &&
              <Link to={`/projects/detail?projectIdx=${board.boardIdx}`} onClick={loginCheckHandler}>
                {board.boardTitle}
              </Link>
            }
            {
              board.boardType === '취업후기' &&
              <Link to={`/reviews/detail/${board.boardIdx}`} onClick={loginCheckHandler}>
                {board.boardTitle}
              </Link>
            }
          </div>
          <div className={'board-date'} key={board.boardDate}>{board.boardDate}</div>
        </section>
      ))}

      {/* 페이징 */}
      {/* TODO : 페이징 어떠케하나요 ... ? */}
      <ul>
        <PageNation
          pageNation={pageNation}
          currentPageHandler={currentPageHandler}
          clickCurrentPage={clickCurrentPage}/>
      </ul>

    </Common>
  );
};

export default MypageBoardList;
