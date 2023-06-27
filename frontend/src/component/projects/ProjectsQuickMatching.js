import React, { useEffect, useState } from 'react';
import {getToken, getUserPosition, isLogin} from '../common/util/login-util';
import { Button, Modal } from 'react-bootstrap';
import { PROJECT } from "../common/config/HostConfig";
import {Link} from "react-router-dom";

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

  // 퀵 매칭
  const [show, setShow] = useState(false);

  const handleShow = () => {
    console.log('퀵 매칭 버튼 클릭');
    setCurrentPage(1); // 페이지를 1로 초기화
    setShow(true);
    quickMatchingFetchData();
  };

  const handleClose = () => {
    setShow(false);
  };

  // 퀵 매칭 데이터 불러오기
  const quick = PROJECT + `/quick?position=${USER_POSITION}&size=1&page=${currentPage}`;
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
          console.log('퀵 매칭 데이터 패치');
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
        <img src={imgSrc} alt={position} style={{ height: 50 }} key={index} />
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
      <Button className="quick-btn" onClick={handleShow}>
        퀵 매칭
      </Button>
      }

      <Modal show={show} onHide={handleClose} id="modal-container">
        {quickDetail.map(board => (
          <React.Fragment key={board.id}>
            <Modal.Header closeButton>
              <Modal.Title>{board.boardTitle}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <div>타입: {board.projectType}</div>
              <div>모집 마감 : {board.offerPeriod}</div>
              <div>♥ : {board.likeCount}</div>

              <div>
                {renderApplicantImages(
                  board.applicantOfFront,
                  'https://cdn-icons-png.flaticon.com/128/5226/5226066.png',
                  'front'
                )}
                {renderApplicantImages(
                  board.maxFront - board.applicantOfFront,
                  'https://cdn-icons-png.flaticon.com/128/5226/5226057.png',
                  'front'
                )}
                {renderApplicantImages(
                  board.applicantOfBack,
                  'https://cdn-icons-png.flaticon.com/128/1353/1353491.png',
                  'back'
                )}
                {renderApplicantImages(
                  board.maxBack - board.applicantOfBack,
                  'https://cdn-icons-png.flaticon.com/128/1353/1353367.png',
                  'back'
                )}
              </div>
            </Modal.Body>
            <Modal.Footer>
              {/*{currentPage > 0 && (*/}
              <Button variant="secondary" onClick={handlePreviousPage}>
                이전
              </Button>
              {/*)}*/}
              {/*{currentPage < board.pages - 1 && (*/}
              <Button variant="primary" onClick={handleNextPage}>
                다음
              </Button>
              {/*)}*/}
              <Button variant="secondary" onClick={handleClose}>
                닫기
              </Button>
              <Link to={`/projects/detail?projectIdx=${board.boardIdx}`} >
                상세보기
              </Link>
            </Modal.Footer>
          </React.Fragment>
        ))}
      </Modal>
    </>
  );
};

export default ProjectsQuickMatching;
