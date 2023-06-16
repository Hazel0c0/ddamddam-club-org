import React from 'react'

import Common from "../../common/Common";
import '../scss/ProjectsTitle.scss'
import {Link} from "react-router-dom";

const ProjectsTitle = () => {


  return (
    <Common className={'projects-view-wrapper'}>
      <div className={'title-wrapper'}>
        <h1 className={'main-title'}>사이드 프로젝트</h1>
        <p className={'main-sub-title'}>나에게 맞는 프로젝트를 찾고, 믿음직한 팀원을 구해봐요.</p>
      </div>
     </Common>
  )
}

export default ProjectsTitle;