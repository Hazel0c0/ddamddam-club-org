import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import { useLocation } from 'react-router-dom';
const ProjectsDetail = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const projectIdx = searchParams.get('projectIdx');

  console.log('디테일 게시판 번호');
  console.log(projectIdx);

  const [projectDetail, setProjectDetail] = useState(null);


  useEffect(() => {
    fetch(PROJECT+`/${projectIdx}`)
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to fetch project');
          }
          return response.json();
        })
        .then(data => {
          setProjectDetail(data);
          console.log(data.payload.boardContent);
        })
        .catch(error => {
          console.error(error);
        });
  }, []);

  return (
      <>
        <ProjectsTitle/>
        <Common className={'qna-detail-wrapper'}>
          <section className={'main-text-wrapper'}>
            <h1 className={'qna-title'}>
            </h1>
            <div className={'hashTag-wrapper'}>
            </div>
            <section className={'info-detail-container'}>
              <div className={'detail-wrapper'}>
                <div className={'category'}>
                  <span className={'modify-btn'}>수정</span>
                  <span className={'delete-btn'}>삭제</span>
                </div>
                <div className={'category'}>
                  <span className={'sub-title'}>작성자</span>
                  <span className={'sub-content'}></span>
                </div>
                <div className={'category'}>
                  <span className={'sub-title'}>작성일자</span>
                  <span className={'sub-content'}>2023.06.23</span>
                </div>
                <div className={'icon-wrapper'}>
                  <div className={'view-count-wrapper'}>
                    <img src={''} alt={'view-count'}
                         className={'view-count-icon'}/><span></span>
                  </div>
                  <div className={'speech-count-wrapper'}>
                    <img src={''} alt={'view-count'}
                         className={'speech-count-icon'}/><span>{}</span>
                  </div>
                </div>
              </div>
            </section>
            <section className={'main-content'}>
              {}
            </section>
            <section className={'checked-wrapper'}>
            </section>
          </section>
        </Common>
      </>
  )
}

export default ProjectsDetail;