import React, {useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./main/ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import {Link, useLocation, useNavigate} from 'react-router-dom';
import './scss/ProjectsWrite.scss';
import {getToken} from "../common/util/login-util";


const ProjectsModify = () => {

  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const projectIdx = searchParams.get('projectIdx');
  const navigate = useNavigate(); // useNavigate 훅 사용
  const [updatedFormData, setUpdatedFormData] = useState([]);

  const ACCESS_TOKEN = getToken();

  const headerInfo = {
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }


  // 기존 데이터 불러오기
  useEffect(() => {
    fetch(PROJECT + `/detail/${projectIdx}`, {
      method: 'GET',
      headers: headerInfo
    })
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to fetch project');
          }
          return response.json();
        })
        .then(data => {
          setUpdatedFormData(data.payload);

          console.log(data.payload);

          console.log(' 이미지 사진 불러오기 : ')
          console.log(data.payload.boardImg)
        })
        .catch(error => {
          console.error(error);
        });
  }, []);


  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setUpdatedFormData((prevFormData) => ({
      ...prevFormData,
      // 입력값이 없으면 기존 정보 사용
      [name]: value || prevFormData[name],
    }));
    console.log(name + " : " + value);
  }

  const modifySubmitHandler = async () => {
    console.log(updatedFormData);
    console.log('projectIdx:', projectIdx);

    const {projectImg, boardTitle, boardContent, projectType, maxFront, maxBack,} = updatedFormData;

    const projectJsonBlob = new Blob(
        [JSON.stringify(updatedFormData)],
        {type: 'application/json'}
    );
    const projectFormData = new FormData();
    projectFormData.append('project', projectJsonBlob);
    projectFormData.append('projectImage', $fileTag.current.files[0]);


    // 작성완료 버튼을 눌렀을 때 실행되는 함수
    if (window.confirm("작성을 완료하시겠습니까?")) {
      const res = fetch(PROJECT, {
        method: 'PATCH',
        headers: headerInfo,
        body: projectFormData
      }).then(response => response.json())
          .then(data => {
            setUpdatedFormData(data.formData);
            console.log(data); // Handle the response data

            navigate(`/projects/detail?projectIdx=${projectIdx}`)
          })
          .catch(error => {
            console.error(error);
          });
      console.log(updatedFormData); // 예시: 콘솔에 데이터 출력
    }
  };

  const $fileTag = useRef();
  const [imgFile, setImgFile] = useState(null);

  const showThumbnailHandler = () => {
    const file = $fileTag.current.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onloadend = () => {
      setImgFile(reader.result);
    };
  };
  console.log('img file upload : ' + imgFile)

  return (
      <>
        <ProjectsTitle/>
        <Common className={'project-write-wrapper'}>

          <div key={updatedFormData.boardIdx}>

            {/* 이미지 */}
            <div className={'thumbnail-box'} onClick={() => $fileTag.current.click()}>
              <img
                  src={imgFile || require('../../assets/img/image-add.png')}
                  alt="profile"
              />
              <label className='signup-img-label' htmlFor='profile-img'>사진 수정하기</label>
              <input
                  id='profile-img'
                  type="file"
                  accept="image/*"
                  ref={$fileTag}
                  style={{display: 'none'}}
                  onChange={showThumbnailHandler}
              />
            </div>


            <section className={'write-form-wrapper'}>
              <input type="hidden"
                     value={updatedFormData.boardIdx}
                     name="boardIdx"
              />
              <div className={'project-date'}>
                {/* 수정 후에는  변경 되도록 */}
                <span className={'sub-title'}>작성일자</span>
                <span className={'sub-content'}>{updatedFormData.projectDate}</span>
              </div>

              <div className={'writer'}>
                <span className={'sub-title'}>작성자</span>
                <span className={'sub-content'}>{updatedFormData.boardWriter}</span>
              </div>

              {/* write - 공통 데이터 */}
              <div className={'title-input-wrapper'}>
                <h1 className={'sub-title'}>제목</h1>
                <input
                    type={"text"}
                    placeholder={updatedFormData.boardTitle}
                    className={'title-text-input'}
                    name={'boardTitle'}
                    defaultValue={updatedFormData.boardTitle}
                    onChange={handleInputChange}
                />
              </div>

              <div className={'select-input-wrapper'}>
                <div className={'project-type'}>
                  <div className={'sub-title'}>프로젝트 타입</div>
                  <select className="subject-select"
                          name="projectType"
                          value={updatedFormData.projectType}

                          onChange={handleInputChange}
                  >
                    <option value="Mobile Web">Mobile Web</option>
                    <option value="Web App">Web App</option>
                    <option value="Native App">Native App</option>
                    <option value="Native App">Native App</option>
                  </select>
                </div>

                <div className={'applicant'}>
                  <div className={'sub-title'}>모집인원</div>
                  <div className={'sub-title'}>프론트</div>
                  <select className="mentee-text-input"
                          name="maxFront"
                          value={updatedFormData.maxFront}
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
                          value={updatedFormData.maxBack}
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
                  <input type={"date"}
                         placeholder={'기한을 입력해주세요'}
                         name="offerPeriod"
                         className={'current-text-input'}
                         defaultValue={updatedFormData.offerPeriod}
                         onChange={handleInputChange}
                  />까지
                </div>
              </div>
            </section>

            <section>
                <textarea type="text"
                          placeholder={updatedFormData.boardContent}
                          className={'boardContent'}
                          name="boardContent"
                          value={updatedFormData.boardContent}
                          onChange={handleInputChange}
                />
            </section>

            <div className={'btn-wrapper'}>
              <Link to={'/projects'} className={'close-btn-a'}>
                <button className={'close-btn'}>취소하기</button>
              </Link>
              <button className={'submit-btn'}
                      onClick={modifySubmitHandler}
              >작성완료
              </button>
            </div>

          </div>
        </Common>
      </>
  );
}

export default ProjectsModify;