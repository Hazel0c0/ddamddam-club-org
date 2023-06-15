import React, {useState} from 'react';
import Common from "../common/Common";
import './scss/ProjectsWrite.scss';
import {Link} from "react-router-dom";
import ProjectsTitle from "./ProjectsTitle";

const ProjectsWrite = () => {
  const [formData, setFormData] = useState({
    boardTitle: "",
    projectType: "",
    maxFront: "",
    maxBack: "",
    offerPeriod: ""
  });

  const handleSubmit = () => {
    // 작성완료 버튼을 눌렀을 때 실행되는 함수
    // formData를 컨트롤러로 보내는 로직을 작성하세요.
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
            />
          </div>
          <div className={'select-input-wrapper'}>
            <div className={'project-type'}>
              <div className={'sub-title'}>프로젝트 타입</div>
              <select className="subject-select"
                      name="projectType"
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
                // value={textInput.mentorCurrent}
                // onChange={handleSelect}
              />까지
            </div>

          </div>
        </section>

        <section>
                <textarea type="text"
                          placeholder={"내용을 입력해주세요"}
                          className={'boardContent'}
                  // value={textInput.mentorContent}
                  // name="mentorContent"
                  // onChange={handleSelect}
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