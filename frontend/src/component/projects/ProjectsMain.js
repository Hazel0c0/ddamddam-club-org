import React, {useState, useEffect, useRef} from 'react';
import PopularProjects from "./PopularProjects";
import Common from "../common/Common";
import {PROJECT} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";

import {Modal, Button} from 'react-bootstrap';
import {getToken, getUserPosition} from "../common/util/login-util";
import 'bootstrap/dist/css/bootstrap.css';
import './scss/QuickMatching.scss';
import ProjectsQuickMatching from "./ProjectsQuickMatching";
import LatestProjects from "./LatestProjects";

const ProjectsMain = () => {

  // 토큰
  const ACCESS_TOKEN = getToken();
  const USER_POSITION = getUserPosition();
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const navigate = useNavigate();
  const childRef = useRef(null);

  // url
  const [currentUrl, setCurrentUrl] = useState(PROJECT);

  // 인기프로젝트 페이지
  // const [likePage, setLikePage] = useState(1);
  // const LIKE_PAGE_SIZE = 3;
  // const popularityUrl = `${PROJECT}?page=${likePage}&size=${LIKE_PAGE_SIZE}&like=true`;
  // const [popularity, setPopularity] = useState(popularityUrl);

  // 페이지 처리
  // const handlePageChange = (currentPage) => {
  //   setLikePage(currentPage);
  //   console.log(`main 현재 페이지 번호 : ${currentPage}`)
  // }

  // useEffect(() => {
  //   handlePageChange(likePage);
  //   // setPopularity(popularityUrl);
  // }, [likePage, popularity])

  // 퀵 매칭 모달창
  const [quickDetail, setQuickDetail] = useState([]);

  const handleLikeClick = (projectId) => {
    // 서버에 좋아요 처리를 위한 POST 요청을 보냅니다
    fetch(PROJECT + `/like/${projectId}`, {
      method: 'POST',
      headers: headerInfo
    })
      .then(res => {
        if (res.status === 200) return res.json()
      })
      .then(data => {
        console.log(data);
        childRef.current.fetchData(); // 자식 컴포넌트의 함수 호출
      })
      .catch(error => {
        console.log('Error:', error);
      });
  };


  const handleShowDetails = (projectIdx) => {
    console.log('게시판 번호: ', projectIdx);

    navigate(`/projects/detail?projectIdx=${projectIdx}`);
  };

  // 퀵 매칭
  const [show, setShow] = useState(false);

  const handleShow = () => {
    console.log('퀵 매칭 버튼 클릭');
    setShow(true);
    quickMatchingFetchData();
  };
  const handleClose = () => {
    setShow(false)
  };


  const quick = PROJECT + `/quick?position=${USER_POSITION}&size=5`;
  const quickMatchingFetchData = () => {
    fetch(quick, {
      method: 'GET',
      headers: headerInfo
    }).then(res => {
        if (res.status === 500) {
          alert('서버 오류입니다');
          return;
        }
        return res.json();
      }
    ).then(res => {
      if (res) {
      console.log('퀵 매칭 데이터 패치');
      console.log(res.payload.projects);
      setQuickDetail(res.payload.projects);
    }
    })
  };

  return (
    <>
      <Link to={'/projects/write'} className={'projects-write-btn'}>
        작성하기
      </Link>

      <PopularProjects
        // url={popularity}
        sortTitle={'인기 프로젝트'}
        handleLikeClick={handleLikeClick}
        handleShowDetails={handleShowDetails}
        // pageChange={handlePageChange}
        ref={childRef}
      />

      <LatestProjects
        // url={currentUrl}
        sortTitle={'최신 프로젝트'}
        handleLikeClick={handleLikeClick}
        handleShowDetails={handleShowDetails}
        ref={childRef}
      />

      {/* 퀵매칭 */}
      <div className={'quick-wrapper'}>
        <Button className={'quick-btn'}
                onClick={handleShow}
        >퀵 매칭
        </Button>

        <Modal show={show} onHide={handleClose} id="modal-container">

          {quickDetail.map((board) => (
            <>
              <ProjectsQuickMatching board={board}
                                     handleClose={handleClose}/>
            </>
          ))}

        </Modal>
      </div>
    </>
  );
};
export default ProjectsMain;