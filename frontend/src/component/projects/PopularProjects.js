import React, {useEffect, useState, forwardRef, useImperativeHandle} from 'react';
import {useNavigate} from 'react-router-dom';
import Common from '../common/Common';
import less from "../../src_assets/less.png";
import than from "../../src_assets/than.png";
import logo from '../../src_assets/logo.png'

import './scss/ProjectsItem.scss';
import ProjectImage from "./ProjectImage";
import {PROJECT} from "../common/config/HostConfig";
import ProjectListContainer from "./ProjectListContainer";

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

      const LIKE_PAGE_SIZE = 3;

      useImperativeHandle(ref, () => ({
        fetchData
      }));

      const fetchData = () => {
        fetch(`${PROJECT}?page=${carouselIndex}&size=${LIKE_PAGE_SIZE}&like=true`, {
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
                console.log(`carouselIndex : ${carouselIndex}`)
              }
            });
      };

      useEffect(() => {
        fetchData();
      }, [carouselIndex]);

      // 페이지 처리
      const handlePrevious = () => {
        if (pageNation.prev === true) {
          setCarouselIndex(prevIndex => prevIndex - 1);
          console.log(`main 현재 페이지 번호 : ${carouselIndex}`)
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
          console.log(`main 현재 페이지 번호 : ${carouselIndex}`)
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

            <ProjectListContainer
                projects={projects}
                handleShowDetails={handleShowDetails}
                handleLikeClick={handleLikeClick}
            />

          </Common>
      );
    }
);

export default PopularProjects;
