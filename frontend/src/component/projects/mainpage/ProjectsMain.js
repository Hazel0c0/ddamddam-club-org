import React, { useState, useEffect,useRef  } from 'react';
import ProjectsItem from "./ProjectsItem";
import Common from "../../common/Common";
import {PROJECT} from "../../common/config/HostConfig";
import { Link, useNavigate } from "react-router-dom";



const ProjectsMain = ({headerInfo}) => {

  console.log(headerInfo)

  const [currentUrl, setCurrentUrl] = useState(PROJECT);
  const popularity = PROJECT + '?sort=like&size=3';
  const navigate = useNavigate();
  const childRef = useRef(null);

  const handleFrontClick = () => {
    console.log("프론트")
    setCurrentUrl(PROJECT + '?position=front');
  };

  const handleBackClick = () => {
    console.log("백")
    setCurrentUrl(PROJECT + '?position=back');
  };

  const handleLikeClick = (projectId) => {
    // 서버에 좋아요 처리를 위한 POST 요청을 보냅니다
    fetch(PROJECT+`/like/${projectId}`, {
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

    navigate(`/projects/detail?projectIdx=${projectIdx}`, { state: { headerInfo } });
  };

  return (
      <>
        <Link to={'/projects/write'} className={'projects-write-btn'}>
          작성하기
        </Link>

        <div className={'sort-button-box'}>
          <div className={'front'} onClick={handleFrontClick}>front</div>
          <div className={'back'} onClick={handleBackClick}>back</div>
        </div>

        <ProjectsItem
          url={popularity}
          sortTitle={'인기 프로젝트'}
          handleLikeClick={handleLikeClick}
          handleShowDetails={handleShowDetails}
          ref={childRef}
        />
        <ProjectsItem
          url={currentUrl}
          sortTitle={'최신 프로젝트'}
          handleLikeClick={handleLikeClick}
          handleShowDetails={handleShowDetails}
          ref={childRef}
        />
      </>
  );
};
export default ProjectsMain;