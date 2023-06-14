import React from 'react';
import ProjectsTitle from './ProjectsTitle';
import ProjectsSearch from "./ProjectsSearch";
import ProjectsLikeList from "./ProjectsLikeList";
import ProjectsList from "./ProjectsList";


const ProjectsTemplate = () => {


  return (
        <div>
          <ProjectsTitle />
          <ProjectsSearch />
          <ProjectsLikeList />
          <ProjectsList />
        </div>
    );
};

export default ProjectsTemplate;