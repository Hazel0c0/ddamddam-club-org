import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import ProjectsTitle from "./ProjectsTitle";
import {PROJECT} from "../common/config/HostConfig";
import {Link, useLocation, useNavigate} from 'react-router-dom';
import './scss/ProjectDetail.scss';
import {getToken, getUserIdx, getUserNickname} from "../common/util/login-util";

const ProjectsDetail = () => {
  const ACCESS_TOKEN = getToken();
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const navigate = useNavigate();
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const projectIdx = searchParams.get('projectIdx');
  const [projectDetail, setProjectDetail] = useState([]);
  const [fileUrl, setFileUrl] = useState(''); // 새로운 상태 추가
  const [applyButtonColor, setApplyButtonColor] = useState(''); // New state variable

  const handleLikeClick = (projectId) => {
    // 서버에 좋아요 처리를 위한 POST 요청을 보냅니다
    fetch(PROJECT + `/like/${projectId}`, {
      method: 'POST',
      headers: headerInfo
    })
        .then(res => {
          if (res.status === 200) return res.json()
        })
        .then(data => {
          console.log(data);
          fetchProjectDetail();
        })
        .catch(error => {
          console.log('Error:', error);
        });
  };


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
          console.log("프로젝트 디테일 dto")

          // 내 게시글 신청하기 버튼 색상 변경
          console.log(data.payload.boardWriter);
          const bw = data.payload.boardWriter;
          const userNickname = getUserNickname();
          console.log('유저 넘버')
          console.log(bw);
          console.log(userNickname);

          const isOwner = bw === userNickname;
          setApplyButtonColor(isOwner ? 'gray' : ''); // Set the button color based on ownership
        })
        .catch(error => {
          console.error(error);
        });
  };


  const fileRequestURL = '//localhost:8181/api/ddamddam/project/load-s3';

  const fetchFileImage = async () => {
    const res = await fetch(
        `${fileRequestURL}?projectIdx=${projectIdx}`, {
          method: 'GET',
        });
    if (res.status === 200) {
      const imgUrl = await res.text();
      setFileUrl(imgUrl);
      console.log(`프로젝트 디테일 - 이미지 : ${imgUrl}`);
    } else {
      const err = await res.text();
      setFileUrl(null);
    }
  };


  useEffect(() => {
    fetchProjectDetail();
    fetchFileImage();
  }, []);

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
    fetch(PROJECT + `/applicant/${projectIdx}`, {
      method: 'PATCH',
      headers: headerInfo,
      body: JSON.stringify({}),
    })
        .then((response) => {
          if (response.status === 400) {
            return response.text();
          }
          console.log('신청 성공')
          console.log(response.json())
          // 성공적으로 요청을 보냈을 때 처리할 코드를 추가합니다.
          fetchProjectDetail(); // 변경된 정보로 다시 가져오기
        })
        .then((errorMessage) => {
          if (errorMessage) {
            alert(errorMessage);
          }
        })
        .catch((error) => {
          console.error(error);
          // 요청이 실패했을 때 처리할 코드를 추가합니다.
        });
  };

  return (
      <>
        <ProjectsTitle/>
        <Common className={'project-detail-wrapper'}>
          {projectDetail.map(de => {
            return (
                <section className={'main-text-wrapper'}>
                  <div key={de.boardIdx}>
                    <div className={'qna-title'}>{de.boardTitle}</div>
                    {fileUrl && <img
                        src={fileUrl}
                        alt="Project Image" className={'project-img'}
                        style={{
                          height: 350
                        }}
                    />}
                    <section className={'info-detail-container'}>
                      <div className={'detail-wrapper'}>

                        {(getUserNickname() === de.boardWriter) && (
                            <div className={'category'}>
                              <Link to={`/projects/modify?projectIdx=${projectIdx}`}
                                    className={'modify-btn'}>
                                수정
                              </Link>
                              <span className={'delete-btn'}
                                    onClick={() => handleDelete(projectIdx)}>삭제</span>
                            </div>
                        )}

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

                    <div className={'apply-wrapper'}
                         onClick={(e) => {
                           handleLikeClick(projectIdx);
                         }}
                    >
                      <div className={'apply-btn'}>♥ {de.likeCount}</div>
                    </div>

                    <section className={'apply-wrapper'}>
                      <div className={'apply-btn'}
                           style={{backgroundColor: applyButtonColor}} // Apply the button color
                           onClick={handleApply}>신청하기
                      </div>
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