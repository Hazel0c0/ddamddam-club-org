import React, {useEffect, useState} from 'react';
import {getToken, getUserPosition, isLogin} from '../common/util/login-util';
import {Button, Modal} from 'react-bootstrap';
import {PROJECT} from "../common/config/HostConfig";
import {Link} from "react-router-dom";
import './scss/QuickMatching.scss';
import frontIcon from "../../src_assets/front.png";
import backIcon from "../../src_assets/back-icon.png";
import xIcon from "../../src_assets/x.png";


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
            console.log(res.payload.projects);
            setQuickDetail(res.payload.projects);
          }
        });
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
        <div className={show ? 'modal-box show' : 'modal-box'}>
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
                        {currentPage <= 10 && (
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
