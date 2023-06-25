import React, {useEffect, useState, forwardRef, useImperativeHandle} from 'react';
import {useNavigate} from 'react-router-dom';
import Common from '../common/Common';
import less from "../../src_assets/less.png";
import than from "../../src_assets/than.png";
import logo from '../../src_assets/logo.png'

import './scss/ProjectsItem.scss';
import ProjectImage from "./ProjectImage";
import {PROJECT} from "../common/config/HostConfig";

const PopularProjects = forwardRef((
        {
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
      //캐러셀
      const [carouselIndex, setCarouselIndex] = useState(1);

      const [currentUrl, setCurrentUrl] = useState(PROJECT);
      const LIKE_PAGE_SIZE = 3;
      const popularityUrl = `${PROJECT}?page=${carouselIndex}&size=${LIKE_PAGE_SIZE}&like=true`;
      const [popularity, setPopularity] = useState(popularityUrl);


      useImperativeHandle(ref, () => ({
        fetchData
      }));

      const fetchData = () => {
        fetch(popularity, {
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
            .then(result => {
              if (!!result) {
                // console.log(`result의 값 : `, result);
                console.log(`projects : `, result.projects);
                console.log(`pageInfo : `, result.pageInfo);
                setProjects(result.projects);
                setPageNation(result.pageInfo);
              }
            });
      };

      useEffect(() => {
        fetchData();
        // console.log(`item page index `, carouselIndex)
        // pageChange(carouselIndex);
        // }, [carouselIndex]);
      }, [carouselIndex]);


      const handlePrevious = () => {
        if (pageNation.prev === true) {
          setCarouselIndex(prevIndex => prevIndex - 1);
        }
      };


      const handleNext = () => {
        console.log('handleNext')
        // // 마지막 페이지 일 때 다시 페이지 설정을 해줘야 정상작동
        // // ex 마지막 페이지가 13인데 carouselIndex가 14일 때
        // // 마지막 페이지의 값이 필요
        //
        // //마지막 페이지 값
        // const lastPage = Math.ceil(pageNation.totalCount / 3);
        //
        // if (lastPage === carouselIndex) {
        //   setCarouselIndex((prevIndex) => prevIndex - 1)
        //   return;
        // }
        // console.log(`렌더링 1단계 전 carouselIndex = `, carouselIndex)
        // if (pageNation.next === true) {
        //   setCarouselIndex(prevIndex => prevIndex + 1);
        // }
        if (pageNation.next === true) {
          setCarouselIndex(prevIndex => prevIndex + 1);
        }
      };


      return (
          <Common className={'project-list-wrapper'}>
            {pageNation.prev &&
                <img src={less} alt={"less-icon"} className={'less-icon'} onClick={handlePrevious}/>
            }

            {pageNation.next &&
                <img src={than} alt={"than-icon"} className={'than-icon'} onClick={handleNext}/>
            }

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
                        key={`${p.boardIdx}`}
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
          </Common>
      );
    }
);

export default PopularProjects;
