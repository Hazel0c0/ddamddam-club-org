import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./mainpage/ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import { Link, useLocation,useNavigate  } from 'react-router-dom';
import './scss/ProjectDetail.scss';
const ProjectsDetail = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const projectIdx = searchParams.get('projectIdx');

  const [projectDetail, setProjectDetail] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch(PROJECT + `/${projectIdx}`, {
      method: 'GET',
      headers: {'content-type': 'application/json'}
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch project');
        }
        return response.json();
      })
      .then(data => {
        setProjectDetail([data.payload]);

        console.log("1-----");
        console.log(data.payload);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  console.log('2-----프로젝트 디테일 : ');
  console.log(projectDetail.boardIdx);

  const handleDelete = (id) => {
    console.log("delete id : "+id);

    fetch(PROJECT + `/${projectIdx}`, {
      method: 'DELETE',
      headers: { 'content-type': 'application/json' }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to delete project');
        }
        console.log('삭제됨');
        navigate('/projects');
      })
      .catch(error => {
        console.error(error);
      });
  }

  return (
    <>
      <ProjectsTitle />
      <Common className={'qna-detail-wrapper'}>
        {projectDetail.map(de => {
          return(
          <section className={'main-text-wrapper'}>
            <div key={de.boardIdx}>
              <div className={'qna-title'}>{de.boardTitle}</div>
              <div className={'hashTag-wrapper'}></div>
              <section className={'info-detail-container'}>
                <div className={'detail-wrapper'}>
                  <div className={'category'}>
                    <Link to={`/projects/modify?projectIdx=${projectIdx}`} className={'modify-btn'}>
                      수정
                    </Link>
                    <span className={'delete-btn'} onClick={() => handleDelete(projectIdx)}>삭제</span>
                  </div>
                  <div className={'category'}>
                    <span className={'p-sub-title'}>작성자</span>
                    <span className={'sub-content'}>{de.boardWriter}</span>
                  </div>
                  <div className={'category'}>
                    <span className={'p-sub-title'}>프로젝트 타입 : </span>
                    <span className={'sub-content'}>{de.projectType}</span>
                  </div>
                  <div>
                  <div className={'category'}>
                    <span className={'p-sub-title'}>FRONT</span>
                    <span className={'sub-content'}>{de.applicantOfFront}</span>
                    /
                    <span className={'sub-content'}>{de.maxFront}</span>
                  </div>
                  <div className={'category'}>
                  <span className={'p-sub-title'}>BACK</span>
                  <span className={'sub-content'}>{de.applicantOfBack}</span>
                    /
                  <span className={'sub-content'}>{de.maxBack}</span>
                </div>
                  </div>
                  <div className={'category'}>
                    <span className={'p-sub-title'}>작성일자</span>
                    <span className={'sub-content'}>{de.projectDate}</span>
                  </div>
                </div>
              </section>
              <section className={'main-content'}>{de.boardContent}</section>
              <section className={'apply-wrapper'}>
                <div className={'apply-btn'}>신청하기</div>
              </section>
            </div>
          </section>
          )
        })}
      </Common>
    </>
  );

}

export default ProjectsDetail;