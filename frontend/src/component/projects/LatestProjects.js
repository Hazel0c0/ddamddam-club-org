import React, {useEffect, useState, forwardRef, useImperativeHandle} from 'react';
import {useNavigate} from 'react-router-dom';
import Common from '../common/Common';
import './scss/ProjectsItem.scss';
import ProjectImage from "./ProjectImage";
import PageNation from "../common/pageNation/PageNation";
import {PROJECT} from "../common/config/HostConfig";

const LatestProjects = forwardRef((
        {
          // url,
          sortTitle,
          handleLikeClick,
          handleShowDetails,
        },
        ref
    ) => {
      const navigate = useNavigate();
      const [projects, setProjects] = useState([]);
      const [pageNation, setPageNation] = useState([]);
      const [prevBtn, setPrevBtn] = useState(false);
      const [nextBtn, setNextBtn] = useState(false);
      const [clickCurrentPage, setClickCurrentPage] = useState(1);

      const currentPageHandler = (clickPageNum) => {
        console.log(`페이지 클릭 시 현재 페이지 번호 : ${clickPageNum}`)
        setClickCurrentPage(clickPageNum);
      }

      useImperativeHandle(ref, () => ({
        fetchData
      }));

      const fetchData = async () => {
        const res =await fetch(PROJECT+`?page=${clickCurrentPage}`, {
          method: 'GET',
          headers: {'content-type': 'application/json'}
        })
            .then(res => {
              if (res.status === 500) {
                alert('잠시 후 다시 접속해주세요.[서버오류]');
                return;
              }
              return res.json();
            })
            .then(async result => {
              if (!!result) {
                console.log(`result의 값 : `, result);
                const projects = result.projects;
                console.log(`projects : `, projects);
                const pageInfo = result.pageInfo;
                console.log(`pageInfo : `, pageInfo);
                setProjects(projects);
                setPageNation(pageInfo);
              }
            });
      };

      useEffect(() => {
        fetchData();
      }, [clickCurrentPage]);


      return (
          <Common className={'project-list-wrapper'}>

            <h2 className={'sort-title'}>{sortTitle}</h2>
            <div className="project-list-container">
              {projects.map((p, index) => {
                // 현재 날짜와 게시글 작성일 간의 차이를 계산합니다
                const currentDate = new Date();
                const writeDate = new Date(p.projectDate);
                const timeDiff = Math.abs(currentDate - writeDate);
                const daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));

                return (
                    <section
                        className={'project-list'}
                        key={p.boardIdx}
                        onClick={() => handleShowDetails(p.boardIdx)}
                    >

                      <div className={'project-wrapper'}>
                        <ProjectImage projectIdx={p.boardIdx}/>

                        <div className={'text-title'}>{p.boardTitle}</div>
                        <div className={'text-content'}>{p.boardContent}</div>
                        <div className={'project-type'}>{p.projectType}</div>
                        <div className={'project-completion'}>
                          {p.completion ? '모집완료' : '구인중'}
                        </div>
                        <div
                            className={'project-like-btn'}
                            onClick={(e) => {
                              e.stopPropagation();
                              handleLikeClick(p.boardIdx);
                            }}
                        >
                          <div className={'project-like'}>
                            ♥ {p.likeCount}
                          </div>
                        </div>
                        {daysDiff <= 7 && (
                            <div className={'project-new-box'}>
                              <div className={'project-new'}>new</div>
                            </div>
                        )}
                      </div>
                    </section>
                );
              })}
            </div>

            <ul>
              <PageNation
                  pageNation={pageNation}
                  currentPageHandler={currentPageHandler}
                  clickCurrentPage={clickCurrentPage}/>
            </ul>
          </Common>
      );
    }
);

export default LatestProjects;
