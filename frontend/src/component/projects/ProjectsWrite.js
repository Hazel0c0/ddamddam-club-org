import React, {useState} from 'react';
import Common from "../common/Common";
import './scss/ProjectsWrite.scss';
import {PROJECT} from "../common/config/HostConfig";
import {Link} from "react-router-dom";
import ProjectsTitle from "./mainpage/ProjectsTitle";

const ProjectsWrite = () => {
  const [formData
    , setFormData] = useState({
    boardWriterIdx: '3',
    boardTitle: '',
    boardContent: '',
    projectType: '',
    maxFront: '',
    maxBack: '',
    offerPeriod: '',
  });

  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
    console.log(name+" : "+value);
  }

  const handleSubmit = () => {
    // 작성완료 버튼을 눌렀을 때 실행되는 함수
    // formData를 컨트롤러로 보내는 로직을 작성하세요.
    fetch(PROJECT, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    }).then(response => response.json())
      .then(data => {
        // setFormData(data.formData);
        console.log(data); // Handle the response data
      })
      .catch(error => {
        console.error(error); // Handle errors
      });
    console.log(formData); // 예시: 콘솔에 데이터 출력
  };

  return (
    <>
      <ProjectsTitle/>
      <Common className={'project-write-wrapper'}>
        <section className={'write-form-wrapper'}>
          <div className={'title-input-wrapper'}>
            <h1 className={'sub-title'}>제목</h1>
            <input
              type={"text"}
              placeholder={'제목을 입력하세요'}
              className={'title-text-input'}
              name={'boardTitle'}
              value={formData.boardTitle}
              onChange={handleInputChange}
            />
          </div>
          <div className={'select-input-wrapper'}>
            <div className={'project-type'}>
              <div className={'sub-title'}>프로젝트 타입</div>
              <select className="subject-select"
                      name="projectType"
                      value={formData.projectType}
                      onChange={handleInputChange}
              >
                {/*<option disabled selected>fruits 🍊</option>*/}
                <option value="웹페이지">웹페이지</option>
                <option value="웹페이지">웹페이지</option>
                <option value="웹페이지">웹페이지</option>
                <option value="기타">기타</option>
              </select>
            </div>

            <div className={'personnel'}>
              <div className={'sub-title'}>모집인원</div>
              <div className={'sub-title'}>프론트</div>
              <select className="mentee-text-input"
                      name="maxFront"
                      value={formData.maxFront}
                      onChange={handleInputChange}
              >
                <option value="1">1명</option>
                <option value="2">2명</option>
                <option value="3">3명</option>
                <option value="4">4명</option>
                <option value="5">5명</option>
              </select>
              <div className={'sub-title'}>back</div>
              <select className="mentee-text-input"
                      name="maxBack"
                      value={formData.maxBack}
                      onChange={handleInputChange}
              >
                <option value="1">1명</option>
                <option value="2">2명</option>
                <option value="3">3명</option>
                <option value="4">4명</option>
                <option value="5">5명</option>
              </select>
            </div>

            <div className={'offerPeriod'}>
              <h1 className={'sub-title'}>모집기간</h1>
              <input type={"text"}
                     placeholder={'기한을 입력해주세요'}
                     name="offerPeriod"
                     className={'current-text-input'}
                     value={formData.offerPeriod}
                     onChange={handleInputChange}
              />까지
            </div>

          </div>
        </section>

        <section>
                <textarea type="text"
                          placeholder={"내용을 입력해주세요"}
                          className={'boardContent'}
                          name="boardContent"
                          value={formData.boardContent}
                          onChange={handleInputChange}
                />
        </section>

        <div className={'btn-wrapper'}>
          <Link to={'/projects'} className={'close-btn-a'}>
            <button className={'close-btn'}>취소하기</button>
          </Link>
          <button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>
        </div>
      </Common>
    </>
  );
};

export default ProjectsWrite;