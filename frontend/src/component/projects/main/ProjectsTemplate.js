import React from 'react';
import ProjectsTitle from './ProjectsTitle';
import ProjectsSearch from "./ProjectsSearch";
import ProjectsMain from "./ProjectsMain"
import {useLocation} from "react-router-dom";

const ProjectsTemplate = () => {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const keyword = searchParams.get('keyword');


  return (
      <>
        <ProjectsTitle/>
        <ProjectsSearch/>
        <ProjectsMain
        keyword = {keyword}
          />
      </>
  );
};

export default ProjectsTemplate;