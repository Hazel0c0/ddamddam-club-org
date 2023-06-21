import React, {useEffect, useState, forwardRef, useImperativeHandle} from 'react';
import {useNavigate} from 'react-router-dom';
import Common from '../../common/Common';
import '../scss/ProjectsItem.scss';

const ProjectsItem = forwardRef((
    {
      url,
      sortTitle,
      handleLikeClick,
      handleShowDetails,
    },
    ref
  ) => {
    const navigate = useNavigate();

    const [projects, setProjects] = useState([]);
    const [pageNation, setPageNation] = useState([]);

    useImperativeHandle(ref, () => ({
      fetchData
    }));

    const fetchData = () => {
      // 데이터 다시 가져오는 함수
      // 예: setCurrentUrl을 업데이트한 후 해당 URL로 fetch 요청하여 데이터를 가져옴
      fetch(url, {
        method: 'GET',
        headers: {'content-type': 'application/json'}
      })
        .then(res => {
          if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
          }
          return res.json();
        })
        .then(result => {
          if (!!result) {
            // 데이터 업데이트
            setProjects(result.payload.projects);
          }
        });
    };
    useEffect(() => {
      fetchData();
    }, []);


    return (
      <Common className={'project-list-wrapper'}>
        <h2 className={'sort-title'}>{sortTitle}</h2>
        <div className="project-list-container">
          {projects.map((p) => {
            // 현재 날짜와 게시글 작성일 간의 차이를 계산합니다
            const currentDate = new Date();
            const writeDate = new Date(p.projectDate);
            const timeDiff = Math.abs(currentDate - writeDate);
            const daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));

            return (
              <section
                className={'project-list'}
                key={p.boardIdx}
                onClick={() => handleShowDetails(p.boardIdx)}
              >
                <div className={'project-wrapper'}>
                  <div className={'text-title'}>{p.boardTitle}</div>
                  <div className={'text-content'}>{p.boardContent}</div>
                  <div className={'project-type'}>{p.projectType}</div>
                  <div className={'project-completion'}>
                    {p.completion ? '모집완료' : '구인중'}
                  </div>
                  <div
                    className={'project-like-btn'}
                    onClick={(e) => {
                      e.stopPropagation();
                      handleLikeClick(p.boardIdx);
                    }}
                  >
                    <div className={'project-like'}>
                      ♥ {p.likeCount}
                    </div>
                  </div>
                  {daysDiff <= 7 && (
                    <div className={'project-new-box'}>
                      <div className={'project-new'}>new</div>
                    </div>
                  )}
                </div>
              </section>
            );
          })}
        </div>
      </Common>
    );
  }
);

export default ProjectsItem;
