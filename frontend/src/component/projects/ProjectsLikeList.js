import React, {useEffect, useState} from 'react';

import Common from "../common/Common";
import './scss/ProjectsList.scss'

const ProjectsLikeList = () => {

    const [project, setProjects] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    useEffect(() => {
        fetch('//localhost:8181/api/ddamddam/project?sort=like', {
            method: 'GET',
            headers: {'content-type': 'application/json'}
          }
        )
          .then(res => {
            if (res.status === 500) {
              alert('잠시 후 다시 접속해주세요.[서버오류]');
              return;
            }
            return res.json();
          })
          .then(
            result => {
              if (!!result) {
                console.log("!!result")
                setProjects(result.payload.projects);
                setPageNation(result.payload.pageInfo);
                console.log("result : " + result);
                console.log(`result.likeCount = ${result.payload.projects[0].likeCount}`);
                console.log(`result.completion = ${result.payload.projects[0].completion}`);
                console.log(`result.projectDate = ${result.payload.projects[0].projectDate}`);
                console.log(`result.pageInfo = ${result.payload.pageInfo}`);
              }
            });
      }, []
    );
    return (
      <Common className={'project-list-wrapper'}>
        <h2 className={'like-sort-title'}>인기 프로젝트 TOP 10</h2>
        {project.map(p => {
          // 현재 날짜와 게시글 작성일 간의 차이를 계산합니다
          const currentDate = new Date();
          const writeDate = new Date(p.projectDate);
          const timeDiff = Math.abs(currentDate - writeDate);
          const daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));

          return (
            <section className={'project-list'} key={p.boardIdx}>
              <div className={'project-wrapper'}>
                <div className={'text-title'}>{p.boardTitle}</div>
                <div className={'text-content'}>{p.boardContent}</div>
                <div className={'project-type'}>{p.projectType}</div>
                <div className={'project-completion'}>
                  {p.completion ? '모집완료' : '구인중'}
                </div>
                <div className={'project-like-btn'}>
                  <div className={'project-like'}>♥ {p.likeCount}</div>
                </div>
                <div className={'project-new-box'}>
                  {daysDiff <= 7 && <div className={'project-new'}>new</div>}
                </div>
              </div>
            </section>
          );
        })}
      </Common>
    );
  }
;

export default ProjectsLikeList;