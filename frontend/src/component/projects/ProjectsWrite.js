import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/ProjectsWrite.scss';
import {PROJECT} from "../common/config/HostConfig";
import { Link, useNavigate } from "react-router-dom";
import ProjectsTitle from "./ProjectsTitle";
import {Grid} from "@mui/material";
import * as PropTypes from "prop-types";
import { getToken, getUserIdx, getUserEmail, getUserName, getUserNickname, getUserRegdate,
  getUserBirth, getUserPosition, getUserCareer, getUserPoint, getUserProfile,
  getUserRole, isLogin } from '../common/util/login-util';



const ProjectsWrite = () => {
  const [formData
    , setFormData] = useState({
    boardWriterIdx: '1',
    boardTitle: '',
    boardContent: '',
    projectType: '웹페이지',
    maxFront: '1',
    maxBack: '1',
    offerPeriod: '2023-07-19',
    projectImg:'',
  });
  const navigate = useNavigate();

  const ACCESS_TOKEN = getToken();

  const headerInfo = {
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
    console.log(name+" : "+value);
  }

  const handleSubmit = () => {
    const projectJsonBlob = new Blob(
        [JSON.stringify(formData)],
        { type: 'application/json' }
    );

    const projectFormData = new FormData();
    projectFormData.append('project', projectJsonBlob);
    projectFormData.append('projectImage', $fileTag.current.files[0]);

    fetch(PROJECT, {
      method: 'POST',
      headers : headerInfo,
      body: projectFormData,
    }).then(response => response.json())
      .then(data => {
        // setFormData(data.formData);
        console.log("write post")
        console.log(data); // Handle the response data
        navigate('/projects')
      })
      .catch(error => {
        console.error(error); // Handle errors
      });
    console.log(formData); // 예시: 콘솔에 데이터 출력
  };

  const $fileTag = useRef();
  const [imgFile, setImgFile] = useState(null);

  const showThumbnailHandler = e => {

    const file = $fileTag.current.files[0];

    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onloadend = () => {
      setImgFile(reader.result);

    }
  };
      console.log(imgFile)

  return (
    <>
      <ProjectsTitle/>
      <Common className={'project-write-wrapper'}>
        <section className={'write-form-wrapper'}>

          <Grid item xs={12}>
            <div className="thumbnail-box" onClick={() => $fileTag.current.click()}>
              <img
                  src={imgFile || require('../../assets/img/image-add.png')}
                  alt="profile"
              />
            </div>
            <label className='signup-img-label' htmlFor='profile-img'>사진 추가하기</label>
            <input
                id='profile-img'
                type='file'
                accept='image/*'
                ref={$fileTag}
                style={{display: 'none'}}
                onChange={showThumbnailHandler}
            />
          </Grid>

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
                <option value="웹개발">웹개발</option>
                <option value="앱개발">앱개발</option>
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
        <ul className={'-box'}>
          <li className={''} name={''} value={''}><img src={'https://letspl.s3.ap-northeast-2.amazonaws.com/icons/java/java-original.svg'} alt={''}/>JAVA</li>
          <li className={''} name={''} value={''}><img src={''} alt={''}/>C언어</li>
          <li className={''} name={''} value={''}><img src={''} alt={''}/>파이썬</li>
        </ul>
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
          <button className={'submit-btn'}
                  onClick={handleSubmit}
          >작성완료</button>
        </div>
      </Common>
    </>
  );
};

export default ProjectsWrite;