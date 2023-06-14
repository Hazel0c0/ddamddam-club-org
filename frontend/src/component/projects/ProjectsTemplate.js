import React from 'react';
import ProjectsTitle from './ProjectsTitle';
import ProjectsSearch from "./ProjectsSearch";
import ProjectsList from "./ProjectsList";


const ProjectsTemplate = () => {


  return (
        <div>
          <ProjectsTitle />
          <ProjectsSearch />
          <ProjectsList />
          {/* <ProjectsList projectList={projects}/> */}
        </div>
    );
};

export default ProjectsTemplate;