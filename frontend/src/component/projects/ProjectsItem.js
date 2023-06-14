import React, {useEffect, useState} from 'react';

import Common from "../common/Common";
import './scss/ProjectsList.scss'

const ProjectsItem = ({url, sortTitle}) => {

  const [project, setProjects] = useState([]);
  const [pageNation, setPageNation] = useState([]);

  useEffect(() => {
        fetch(url, {
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
                    setProjects(result.payload.projects);
                    setPageNation(result.payload.pageInfo);
                    // console.log("result : " + result);
                    // console.log(`result.likeCount = ${result.payload.projects[0].likeCount}`);
                    // console.log(`result.completion = ${result.payload.projects[0].completion}`);
                    // console.log(`result.projectDate = ${result.payload.projects[0].projectDate}`);
                    // console.log(`result.pageInfo = ${result.payload.pageInfo}`);
                  }
                });
      }, []
  );

  const handleLikeClick = (projectId) => {
    // 상태에서 해당 프로젝트를 찾습니다
    const projectToUpdate = project.find(project => project.boardIdx === projectId);

    if (projectToUpdate) {
      const isLiked = projectToUpdate.likeCount > 0;
      console.log("프로젝트 찾기")

      // 서버에 좋아요 처리를 위한 POST 요청을 보냅니다
      // 1-> ${userIdx} 세션에서 가져올거라 없어질 예정
      fetch(`//localhost:8181/api/ddamddam/project/like/1/${projectId}`, {
        method: 'POST',
        headers: { 'content-type': 'application/json' }
      })
          .then(res => res.json())
          .then(data => {
            if (data.success) {
              console.log("좋아요 누름")
              // // 좋아요 상태에 따라 상태의 likeCount를 업데이트합니다
              // setProjects(prevProjects => {
              //   return prevProjects.map(prevProject => {
              //     if (prevProject.boardIdx === projectId) {
              //       return {
              //         ...prevProject,
              //         likeCount: isLiked ? prevProject.likeCount - 1 : prevProject.likeCount + 1
              //       };
              //     }
              //     return prevProject;
              //   });
              // });
              console.log("성공");
            } else {
              // 서버 요청이 실패한 경우의 에러 처리
              console.log(data.message);
            }
          })
          .catch(error => {
            console.log('Error:', error);
          });
    }
  };

  return (
      <Common className={'project-list-wrapper'}>
        <h2 className={'like-sort-title'}>{sortTitle}</h2>
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
                  <div className={'project-like-btn'}
                       onClick={() => handleLikeClick(p.boardIdx)}
                  >
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
};

export default ProjectsItem;