import React, { useState } from 'react';
import ProjectsItem from "./ProjectsItem";
import Common from "../../common/Common";
import {PROJECT} from "../../common/config/HostConfig";
import {Link} from "react-router-dom";


const ProjectsMain = () => {

  const [currentUrl, setCurrentUrl] = useState(PROJECT);

  const handleFrontClick = () => {
    console.log("프론트")
    setCurrentUrl(PROJECT + '?position=front');
  };

  const handleBackClick = () => {
    console.log("백")
    setCurrentUrl(PROJECT + '?position=back');
  };

  const popularity = PROJECT + '?sort=like&size=3';

  return (
      <>
        <Link to={'/projects/write'} className={'projects-write-btn'}>
          작성하기
        </Link>

        <div className={'sort-button-box'}>
          <div className={'front'} onClick={handleFrontClick}>front</div>
          <div className={'back'} onClick={handleBackClick}>back</div>
        </div>

        <ProjectsItem url={popularity}
                      sortTitle={'인기 프로젝트'} />
        <ProjectsItem url={currentUrl} sortTitle={'최신 프로젝트'} />

      </>
  );
};
export default ProjectsMain;