import React from 'react';
import ProjectsTitle from './ProjectsTitle';
import ProjectsSearch from "./ProjectsSearch";
import ProjectsMain from "./ProjectsMain"

const ProjectsTemplate = () => {

  return (
      <>
        <ProjectsTitle/>
        <ProjectsSearch/>
        <ProjectsMain />
      </>
  );
};

export default ProjectsTemplate;