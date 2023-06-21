import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./mainpage/ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import {Link, useLocation, useNavigate} from 'react-router-dom';
import './scss/ProjectDetail.scss';

const ProjectsDetail = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const projectIdx = searchParams.get('projectIdx');

  const [projectDetail, setProjectDetail] = useState([]);
  const [projectImgUrl, setProjectImgUrl] = useState(''); // 새로운 상태 추가
  const navigate = useNavigate();

  const fetchProjectDetail = () => {
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
          console.log(data.payload);

        })
        .catch(error => {
          console.error(error);
        });
  };

  const file_URL = '//localhost:8181/api/ddamddam/load-file'

  const fetchFileImage = async () => {
    const res = await fetch(
      `${file_URL}?projectIdx=${projectIdx}&boardType=project`,{
      method: 'GET',
    });
    if (res.status === 200) {
      const fileBlob = await res.blob();
      const imgUrl = window.URL.createObjectURL(fileBlob);
      setProjectImgUrl(imgUrl);
    } else {
      const err = await res.text();
      setProjectImgUrl(null);
    }
  };


  useEffect(() => {
    fetchProjectDetail();
    fetchFileImage();
  }, []);


  console.log('2-----프로젝트 디테일 : ');
  console.log(projectDetail.boardIdx);

  const handleDelete = (id) => {
    console.log("delete id : " + id);

    fetch(PROJECT + `/${projectIdx}`, {
      method: 'DELETE',
      headers: {'content-type': 'application/json'}
    })
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to delete project');
          }
          console.log('삭제됨');
          navigate('/projects');
          setProjectDetail([response.payload]);

        })
        .catch(error => {
          console.error(error);
        });
  }

  const handleApply = () => {
    // 11 대신 유저 번호 넣을거
    fetch(PROJECT + `/applicant/11/${projectIdx}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        // 요청 본문에 필요한 데이터를 추가할 수 있습니다.
        // 예: 사용자 정보, 프로젝트 정보 등
      }),
    })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to apply for project');
          }
          console.log('신청 성공')
          console.log(response.json())
          // 성공적으로 요청을 보냈을 때 처리할 코드를 추가합니다.
          fetchProjectDetail(); // 변경된 정보로 다시 가져오기

        })
        .catch((error) => {
          console.error(error);
          // 요청이 실패했을 때 처리할 코드를 추가합니다.
        });
  };


  return (
      <>
        <ProjectsTitle/>
        <Common className={'qna-detail-wrapper'}>
          {projectDetail.map(de => {
            return (
                <section className={'main-text-wrapper'}>
                  <div key={de.boardIdx}>
                    <div className={'qna-title'}>{de.boardTitle}</div>
                    {projectImgUrl && <img
                        src={projectImgUrl}
                        alt="Project Image" className={'project-img'}
                        style={{
                          height: 350
                        }}
                          />}
                          <section className={'info-detail-container'}>
                      <div className={'detail-wrapper'}>
                        <div className={'category'}>
                          <Link to={`/projects/modify?projectIdx=${projectIdx}`}
                                className={'modify-btn'}>
                            수정
                          </Link>
                          <span className={'delete-btn'}
                                onClick={() => handleDelete(projectIdx)}>삭제</span>
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
              <div className={'apply-btn'} onClick={handleApply}>신청하기</div>
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