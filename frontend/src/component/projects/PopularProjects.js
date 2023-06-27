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
      keyword
    },
    ref
  ) => {

// 키워드 null값 처리
    const navigate = useNavigate();
    const [projects, setProjects] = useState([]);

    const [pageNation, setPageNation] = useState([]);
    const [prevBtn, setPrevBtn] = useState(false);
    const [nextBtn, setNextBtn] = useState(false);
    //캐러셀
    const [carouselIndex, setCarouselIndex] = useState(1);
    const [clickCurrentPage, setClickCurrentPage] = useState(1);


    const currentPageHandler = (clickPageNum) => {
      console.log(`페이지 클릭 시 현재 페이지 번호: ${clickPageNum}`);
      setClickCurrentPage(clickPageNum);
    }
    useImperativeHandle(ref, () => ({
      fetchData
    }));

    let FETCH;
    if (!!keyword) {
      FETCH = `${PROJECT}?page=${carouselIndex}&like=true&keyword=${keyword}`;
    } else {
      FETCH = `${PROJECT}?page=${carouselIndex}&like=true`;
    }
    const fetchData = () => {
      fetch(FETCH, {
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
      setCarouselIndex(1);
    }, [keyword]);

    useEffect(() => {
      fetchData();
    }, [carouselIndex, keyword]);


    // 페이지 처리
    const handlePrevious = () => {
      if (pageNation.currentPage > 1) {
        console.log(`pageNation.currentPage : ${pageNation.currentPage}`)
        setCarouselIndex(prevIndex => prevIndex - 1);
      }
    };

    const lastPage = Math.ceil(pageNation.totalCount / 3);

    const handleNext = () => {
      console.log('handleNext')

      if (pageNation.currentPage < lastPage) {
        setCarouselIndex(prevIndex => prevIndex + 1);
        console.log(`pageNation.currentPage : ${pageNation.currentPage}`)
      }
    };


    return (
      <Common className={'project-list-wrapper'}>
        {pageNation.currentPage > 1 &&
          <img src={less} alt={"less-icon"} className={'less-icon'} onClick={handlePrevious}/>
        }

        {pageNation.currentPage < lastPage &&
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
