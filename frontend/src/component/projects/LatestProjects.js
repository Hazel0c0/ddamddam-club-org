import React, {useEffect, useState, forwardRef, useImperativeHandle} from 'react';
import {useNavigate} from 'react-router-dom';
import Common from '../common/Common';
import './scss/ProjectsItem.scss';
import ProjectImage from "./ProjectImage";
import PageNation from "../common/pageNation/PageNation";
import {PROJECT} from "../common/config/HostConfig";
import ProjectListContainer from "./ProjectListContainer";

const LatestProjects = forwardRef((
        {
          // url,
          sortTitle,
          handleLikeClick,
          handleShowDetails,
            keyword

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
    let FETCH;
    if (!!keyword) {
        FETCH = PROJECT+`?page=${clickCurrentPage}&keyword=${keyword}`;
    } else {
        FETCH = PROJECT+`?page=${clickCurrentPage}`;
    }
      const fetchData = async () => {
        const res =await fetch(FETCH, {
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
      }, [clickCurrentPage,keyword]);


      return (
          <Common className={'project-list-wrapper'}>

            <h2 className={'sort-title'}>{sortTitle}</h2>

            <ProjectListContainer
                projects={projects}
                handleShowDetails={handleShowDetails}
                handleLikeClick={handleLikeClick}
            />

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
