import React, {useEffect, useState} from 'react';

import Common from "../common/Common";
import './scss/ProjectsItem.scss'

const ProjectsItem = ({url, sortTitle}) => {

  const [project, setProjects] = useState([]);
  const [pageNation, setPageNation] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
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
            // console.log(result)
            setProjects(result.payload.projects);
            setPageNation(result.payload.pageInfo);
            console.log(result.payload.projects)
          }
        });
  }

  const handleLikeClick = (projectId) => {

    // 서버에 좋아요 처리를 위한 POST 요청을 보냅니다
    // 1-> ${userIdx} 세션에서 가져올거라 없어질 예정
    fetch(`//localhost:8181/api/ddamddam/project/like/4/${projectId}`, {
      method: 'POST',
      headers: {'content-type': 'application/json'}
    })
      .then(res => {
        if (res.status === 200) return res.json()
      })
      .then(data => {
        console.log(data);
        fetchData();
      })
      .catch(error => {
        console.log('Error:', error);
      });
  };

  return (
    <Common className={'project-list-wrapper'}>
      <h2 className={'sort-title'}>{sortTitle}</h2>
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
              {/*  토큰 정보 = like 누른 회원 비교 -> 버튼 색상 설정   */}
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