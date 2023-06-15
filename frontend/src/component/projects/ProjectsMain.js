import React from 'react';
import ProjectsItem from "./ProjectsItem";
import Common from "../common/Common";


const ProjectsMain = () => {
  const latest ='//localhost:8181/api/ddamddam/project';
  const popularity = '//localhost:8181/api/ddamddam/project?sort=like';

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