import React, {useEffect, useState} from 'react';
import {getToken, getUserPosition, isLogin} from '../common/util/login-util';
import {Button, Modal} from 'react-bootstrap';
import {PROJECT} from "../common/config/HostConfig";
import {Link} from "react-router-dom";
import './scss/QuickMatching.scss';
import frontIcon from "../../src_assets/front.png";
import backIcon from "../../src_assets/back-icon.png";
import xIcon from "../../src_assets/x.png";
import {use} from "js-joda";


const ProjectsQuickMatching = () => {
  // 토큰
  const ACCESS_TOKEN = getToken();
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }
  const USER_POSITION = getUserPosition();

  // 퀵 매칭 모달창
  const [quickDetail, setQuickDetail] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [lastPage, setLastPage] = useState('')
  const [show, setShow] = useState(false);

  // 퀵 매칭 버튼 클릭
  const handleShow = () => {
    setShow(true);
    quickMatchingFetchData();
    setCurrentPage(1); // 페이지를 1로 초기화
  };

  const handleClose = () => {
    setShow(false);
  };

  // 모달 밖 영역 클릭 시 모달 닫기
  const handleOutsideClick = (e) => {
    if (e.target.classList.contains('modal-box')) {
      handleClose();
    }
  };
  // 퀵 매칭 데이터 불러오기
  const quick = PROJECT + `/quick?position=${USER_POSITION}&page=${currentPage}`;
  const quickMatchingFetchData = () => {
    fetch(quick, {
      method: 'GET',
      headers: headerInfo
    })
        .then(res => {
          if (res.status === 500) {
            alert('서버 오류입니다');
            return;
          }
          return res.json();
        })
        .then(res => {
          if (res) {
            // console.log(res.payload.projects);
            setQuickDetail(res.payload.projects);
            setLastPage(res.payload.pageInfo.endPage);
          }
        });
  };

  // d-day 계산
  const calculateDday  = (deadline) => {
    const now = new Date();
    const endDate = new Date(deadline);
    const remainingTime = endDate - now;

    // 시간, 분, 초 계산
    const days = Math.floor(remainingTime / (1000 * 60 * 60 * 24));
    const hours = Math.floor(remainingTime / (1000 * 60 * 60));
    const minutes = Math.floor((remainingTime % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((remainingTime % (1000 * 60)) / 1000);

    return { days, hours, minutes, seconds };
  };
  const displayDday  = (deadline) => {
    const remainingTime = calculateDday(deadline);
    const { days, hours, minutes, seconds } = remainingTime;

    return (
        <div>
          <span>남은 시간: </span>
          <span>{days}일 {hours}시간 {minutes}분 {seconds}초</span>
        </div>
    );
  };

  // 지원자 현황
  const renderApplicantImages = (count, imgSrc, position) => {
    return Array(count)
        .fill()
        .map((_, index) => (
            <img
                className={'position-img'}
                src={imgSrc}
                alt={position}
                key={index}
            />
        ));
  };

  // 페이지
  const handlePreviousPage = () => {
    setCurrentPage(prevPage => prevPage - 1);
  };

  const handleNextPage = () => {
    setCurrentPage(prevPage => prevPage + 1);
  };

  useEffect(() => {
    quickMatchingFetchData()
  }, [currentPage]);

  return (
      <>
        {
            isLogin &&
            <button className="quick-btn" onClick={handleShow}>
              Quick Matching
            </button>
        }
        <div
            className={show ? 'modal-box show' : 'modal-box'}
            onClick={handleOutsideClick} // 모달 밖 영역 클릭 시 handleOutsideClick 함수 호출
        >
          <div className="modal-container">
            {quickDetail.map((board) => (
                <React.Fragment key={board.id}>
                  <div className="modal-contents">
                    <div className="modal-header">
                      <button className="close" onClick={handleClose}>
                        <span>&times;</span>
                      </button>
                      <h5 className="modal-title">{board.boardTitle}</h5>
                    </div>
                    <div className="modal-body">
                      <ul>
                        <li>타입: {board.projectType}</li>
                        <li>모집 마감 : {board.offerPeriod}</li>
                        <li>{displayDday (board.offerPeriod)}</li>
                      </ul>
                      <div className="applicant-box">
                        <div className={'front-box'}>
                          <div className={'position-text'}>front</div>
                          {renderApplicantImages(
                              board.applicantOfFront,
                              frontIcon,
                              'front'
                          )}
                          {renderApplicantImages(
                              board.maxFront - board.applicantOfFront,
                              xIcon,
                              'front'
                          )}
                        </div>
                        <div className={'back-box'}>
                          <div className={'position-text'}>back</div>
                          {renderApplicantImages(
                              board.applicantOfBack,
                              backIcon,
                              'back'
                          )}
                          {renderApplicantImages(
                              board.maxBack - board.applicantOfBack,
                              xIcon, 'back'
                          )}
                        </div>
                      </div>
                    </div>
                    <div className="modal-footer">
                      <div className="footer-left">
                        <Link
                            to={`/projects/detail?projectIdx=${board.boardIdx}`}
                            className="btn detail-btn"
                        >
                          상세보기
                        </Link>
                        <div className="like">❤️ {board.likeCount}</div>
                      </div>
                      <div className="footer-right">
                        {currentPage > 1 && (
                            <button
                                className="btn btn-secondary"
                                onClick={handlePreviousPage}
                            >
                              이전
                            </button>
                        )}
                        {lastPage > 10 ? currentPage < lastPage : currentPage <= 10
                            && (
                                <button
                                    className="btn btn-primary"
                                    onClick={handleNextPage}
                                >
                                  다음
                                </button>
                            )}
                      </div>
                    </div>
                  </div>
                </React.Fragment>
            ))}
          </div>
        </div>
      </>
  );

};

export default ProjectsQuickMatching;
