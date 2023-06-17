import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./mainpage/ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import {Link, useLocation} from 'react-router-dom';

const ProjectsDetail = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const projectIdx = searchParams.get('projectIdx');

  const [projectDetail, setProjectDetail] = useState([]);

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

  return (
    <>
      <ProjectsTitle />
      <Common className={'qna-detail-wrapper'}>
        {projectDetail.map(de => {
          return(
          <section className={'main-text-wrapper'}>
            <div key={de.boardIdx}>
              <div className={'qna-title'}>
                <h1 className={'sub-title'}>제목</h1>
                <input
                  type={"text"}
                  placeholder={de.boardTitle}
                  className={'title-text-input'}
                  name={'boardTitle'}
                  // value={formData.boardTitle}
                  // onChange={handleInputChange}
                />
              </div>
              <div className={'hashTag-wrapper'}></div>
              <section className={'info-detail-container'}>
                <div className={'detail-wrapper'}>
                  <div className={'category'}>
                  </div>
                  <div className={'category'}>
                    <span className={'sub-title'}>작성자</span>
                    <span className={'sub-content'}>{de.boardWriter}</span>
                  </div>

                  <Link to={'/projects'} className={'close-btn-a'}>
                    <button className={'close-btn'}>취소하기</button>
                  </Link>
                  {/*<button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>*/}
                  <button className={'submit-btn'} >작성완료</button>

                  <div className={'category'}>
                    {/* 수정 후에는 작성일자 변경 되도록 */}
                    <span className={'sub-title'}>작성일자</span>
                    <span className={'sub-content'}>{de.projectDate}</span>
                  </div>
                </div>
              </section>
              <section>
                <textarea type="text"
                          placeholder={de.boardContent}
                          className={'main-content'}
                          name="boardContent"
                          // value={formData.boardContent}
                          // onChange={handleInputChange}
                />
              </section>
              <section className={'checked-wrapper'}></section>
            </div>
          </section>
          )
        })}
      </Common>
    </>
  );

}

export default ProjectsDetail;