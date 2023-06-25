import React from 'react'
import Common from "../common/Common";
import searchIcon from "../../src_assets/search-icon.png";
import './scss/ProjectSearch.scss';
import {Link} from "react-router-dom";

const ProjectsSearch = () => {

  return (
    <Common className={'project-search-wrapper'}>
      <ul className={'sort-btn'}>
        <li>▼ 제목</li>
      </ul>
      <div className={'search-wrapper'}>
        <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
        <input className={'input-btn'} placeholder={'검색창'} name={'search'}></input>
      </div>
    </Common>
  )
}

export default ProjectsSearch