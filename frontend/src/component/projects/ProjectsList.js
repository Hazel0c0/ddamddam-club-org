import React, {useEffect, useState} from 'react';
import Common from "../common/Common";


const ProjectsList = () => {

  const [project, setProjects] = useState([]);
  const [pageNation, setPageNation] = useState([]);

  useEffect(() => {
    fetch('//localhost:8181/api/ddamddam/project?page=1&size=10')
      .then(res => {
        if (res.status === 500) {
          alert('잠시 후 다시 접속해주세요.[서버오류]');
          return;
        }
        return res.json();
      })
      .then(
        result => {
          console.log(result);
          if (!!result) {
            // setProjects(result.payload.projects);
            // setPageNation(result.payload.pageInfo);
            console.log(result);
            console.log(`result.projects = ${result.payload.projects[0].boardContent}`);
            console.log(`result.pageInfo = ${result.payload.pageInfo}`);
          }
        });
  }, []);
  return (
    <Common className={'project-list-wrapper'}>
            {/*{project.map(p => (*/}
            {/*  <section className={'project-list'} key={p.boardIdx}>*/}

            {/*    <h2 className={'sort-title'}>인기 프로젝트 TOP 10 </h2>*/}

            {/*    <div className={'project-wrapper'}>*/}
            {/*      <div className={'project-title'}>{p.boardTitle}</div>*/}
            {/*      <div className={'project-content'}>{p.boardContent}</div>*/}
            {/*      <div className={'project-tag'}>해시태그</div>*/}
            {/*      <div className={'project-completion'}>모집중/완료</div>*/}
            {/*      <div className={'project-like'}>좋아요 10</div>*/}
            {/*      <div className={'project-new'}>new</div>*/}
            {/*    </div>*/}
            {/*  </section>*/}
            {/*))}*/}
    </Common>
  );
};

export default ProjectsList;