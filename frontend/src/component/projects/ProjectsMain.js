import React from 'react';
import ProjectsItem from "./ProjectsItem";
import Common from "../common/Common";
import {PROJECT} from "../common/config/HostConfig";


const ProjectsMain = () => {
  const latest =PROJECT;
  const popularity = PROJECT+'?sort=like';

  return (
      <>
        <ProjectsItem url={popularity}
                      sortTitle={'인기 프로젝트 TOP 10'} />
        <ProjectsItem url={latest}
                      sortTitle={'최신 프로젝트'} />
      </>
  );
};
export default ProjectsMain;