import React from 'react'

import Common from "../common/Common";
import './scss/ProjectsTitle.scss'

const ProjectsTitle = () => {


  return (
    <Common className={'projects-view-wrapper'}>
      <div className={'title-wrapper'}>
        <h1 className={'main-title'}>사이드 프로젝트</h1>
        <p className={'main-sub-title'}>프로젝트 서브 타이틀</p>
      </div>
     </Common>
  )
}

export default ProjectsTitle;