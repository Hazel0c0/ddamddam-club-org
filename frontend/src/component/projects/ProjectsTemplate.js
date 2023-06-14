import React from 'react';
import ProjectsTitle from './ProjectsTitle';
import ProjectsSearch from "./ProjectsSearch";
import ProjectMain from "./ProjectMain"



const ProjectsTemplate = () => {

  return (
      <>
        <ProjectsTitle/>
        <ProjectsSearch/>
        <ProjectMain />
      </>
  );
};

export default ProjectsTemplate;