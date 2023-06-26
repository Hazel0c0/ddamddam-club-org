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

const ProjectsMain = ({keyword}) => {

  // 토큰
  const ACCESS_TOKEN = getToken();
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const navigate = useNavigate();
  const childRef = useRef(null);


  // useEffect(() => {
  //   handlePageChange(likePage);
  //   // setPopularity(popularityUrl);
  // }, [likePage, popularity])

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


  useEffect(() => {
  }, []);

  return (
    <Common>
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
        keyword={keyword}
      />

      <LatestProjects
        // url={currentUrl}
        sortTitle={'최신 프로젝트'}
        handleLikeClick={handleLikeClick}
        handleShowDetails={handleShowDetails}
        ref={childRef}
        keyword={keyword}
      />

      {/* 퀵매칭 */}
      <div className={'quick-wrapper'}>
        <ProjectsQuickMatching />
      </div>
    </Common>
  );
};
export default ProjectsMain;