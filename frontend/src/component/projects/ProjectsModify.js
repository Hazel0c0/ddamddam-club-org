import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./mainpage/ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import {Link, useLocation} from 'react-router-dom';
import {fetchProjectDetail} from './ProjectsDetail';
import {handleInputChange} from "./ProjectsWrite"

import './scss/ProjectsWrite.scss';


const ProjectsModify = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  // const projectIdx = searchParams.get('projectIdx');

  const [projectDetail, setProjectDetail] = useState([]);

  const [updatedFormData, setUpdatedFormData] = useState({
    boardTitle: '',
    boardContent: '',
    projectType: '',
    maxFront: '',
    maxBack: '',
  });

  const projectIdx = 12;
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
        setProjectDetail(data.payload);
        console.log(`수정 후 : ${JSON.stringify(data.payload)}`);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  console.log('프로젝트 디테일 : ');
  console.log(projectDetail[0].boardContent);

  const modifySubmitHandler = async () => {
      console.log(updatedFormData);
      const {boardTitle, boardContent, projectType,maxFront,maxBack} = updatedFormData;

    // 작성완료 버튼을 눌렀을 때 실행되는 함수
    // formData를 컨트롤러로 보내는 로직을 작성하세요.
    const res= fetch(PROJECT, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updatedFormData),
    }).then(response => response.json())
      .then(data => {
        // setFormData(data.formData);
        console.log(data); // Handle the response data
      })
      .catch(error => {
        console.error(error); // Handle errors
      });
    console.log(updatedFormData); // 예시: 콘솔에 데이터 출력
  };


  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setProjectDetail((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
    console.log(name+" : "+value);
  }

  return (
    <>
      <ProjectsTitle/>
      <Common className={'qna-detail-wrapper'}>
        {projectDetail.map(de => {
          return (
            <section className={'main-text-wrapper'}>
              <div key={de.boardIdx}>
                <div className={'qna-title'}>
                  <h1 className={'sub-title'}>제목</h1>
                  <input
                    type={"text"}
                    placeholder={de.boardTitle}
                    className={'title-text-input'}
                    name={'boardTitle'}
                    defaultValue={updatedFormData.boardTitle}
                    onChange={handleInputChange}
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

                    <div className={'project-type'}>
                      <div className={'sub-title'}>프로젝트 타입</div>
                      <select className="subject-select"
                              name="projectType"
                      >
                        {/*<option disabled selected>fruits 🍊</option>*/}
                        <option value="웹페이지">웹페이지</option>
                        <option defaultValue="웹페이지">웹페이지</option>
                        <option defaultValue="웹페이지">웹페이지</option>
                        <option defaultValue="기타">기타</option>
                      </select>
                    </div>

                    <div className={'personnel'}>
                      <div className={'sub-title'}>모집인원</div>
                      <div className={'sub-title'}>프론트</div>
                      <select className="mentee-text-input"
                              name="maxFront"
                              // value={formData.maxFront}
                              // onChange={handleInputChange}
                      >
                        <option defaultValue="1">1명</option>
                        <option defaultValue="2">2명</option>
                        <option defaultValue="3">3명</option>
                        <option defaultValue="4">4명</option>
                        <option defaultValue="5">5명</option>
                      </select>
                      <div className={'sub-title'}>back</div>
                      <select className="mentee-text-input"
                              name="maxBack"
                              // value={formData.maxBack}
                              // onChange={handleInputChange}
                      >
                        <option defaultValue="1">1명</option>
                        <option defaultValue="2">2명</option>
                        <option defaultValue="3">3명</option>
                        <option defaultValue="4">4명</option>
                        <option defaultValue="5">5명</option>
                      </select>
                    </div>

                    <div className={'offerPeriod'}>
                      <h1 className={'sub-title'}>모집기간</h1>
                      <input type={"text"}
                             placeholder={'기한을 입력해주세요'}
                             name="offerPeriod"
                             className={'current-text-input'}
                             defaultValue={updatedFormData.offerPeriod}
                             onChange={handleInputChange}
                      />까지
                    </div>

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
                  // onChange={(e) =>
                  //     handleInputChange(e, formData, setFormData)        }
                />
                </section>

                <Link to={'/projects'} className={'close-btn-a'}>
                  <button className={'close-btn'}>취소하기</button>
                </Link>
                {/*<button className={'submit-btn'} onClick={modifySubmitHandler}>작성완료</button>*/}
                <button className={'submit-btn'}
                        onClick={modifySubmitHandler}
                >작성완료</button>

              </div>
            </section>
          )
        })}
      </Common>
    </>
  );

}

export default ProjectsModify;