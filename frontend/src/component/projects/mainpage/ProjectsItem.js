import React, {useEffect, useState, forwardRef, useImperativeHandle} from 'react';
import {useNavigate} from 'react-router-dom';
import Common from '../../common/Common';
import less from "../../../src_assets/less.png";
import than from "../../../src_assets/than.png";
import logo from '../../../src_assets/logo.png'

import '../scss/ProjectsItem.scss';

const ProjectsItem = forwardRef((
    {
      url,
      sortTitle,
      handleLikeClick,
      handleShowDetails,
      pageChange,
    },
    ref
  ) => {
    const navigate = useNavigate();
    const [projects, setProjects] = useState([]);
    const [projectImgUrls, setProjectImgUrls] = useState([]);

    const [pageNation, setPageNation] = useState({});
    const [prevBtn, setPrevBtn] = useState(false);
    const [nextBtn, setNextBtn] = useState(false);
    //캐러셀
    const [carouselIndex, setCarouselIndex] = useState(1);

    useImperativeHandle(ref, () => ({
      fetchData
    }));

    const fetchData = () => {
      fetch(url, {
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
            console.log(`result의 값 : `, result);
            if (result.payload.pageInfo) {
              setPageNation(result.payload.pageInfo);
              setPrevBtn(!!result.payload.pageInfo.prev);
              setNextBtn(!!result.payload.pageInfo.next);
            }
            let res = result.payload.projects;
            setProjects(res);
            console.log(`결과 `);
            console.log(res)
            for (let i = 0; i < res.length; i++) {
              console.log(res[i].boardIdx)
              fetchFileImage(res[i].boardIdx, i);
            }
          }
        });
    };

    useEffect(() => {
      fetchData();
      console.log(`item page index `, carouselIndex)
      pageChange(carouselIndex);
    }, [carouselIndex]);

    const file_URL = '//localhost:8181/api/ddamddam/load-file'

    const fetchFileImage = async (projectIdx, index) => {
      const res = await fetch(
        `${file_URL}?projectIdx=${projectIdx}&boardType=project`, {
          method: 'GET',
        });
      if (res.status === 200) {
        const fileBlob = await res.blob();
        const imgUrl = window.URL.createObjectURL(fileBlob);
        setProjectImgUrls((prevUrls) => {
          const updatedUrls = [...prevUrls];
          updatedUrls[index] = imgUrl;
          return updatedUrls;
        });
        // console.log(`프로젝트 디테일 - 이미지 (${index}): ${imgUrl}`);
      } else {
        const err = await res.text();
        setProjectImgUrls((prevUrls) => {
          const updatedUrls = [...prevUrls];
          updatedUrls[index] = null;
          return updatedUrls;
        });
      }
    };


    const handlePrevious = () => {
      if (prevBtn) {
      setCarouselIndex(prevIndex => prevIndex - 1);
      }
    };


    const handleNext = () => {
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
      if (nextBtn) {
        setCarouselIndex((prevIndex) => prevIndex + 1);
      }
    };


    return (
      <Common className={'project-list-wrapper'}>
        {/*{pageNation.prev &&*/}
        <img src={less} alt={"less-icon"} className={'less-icon'} onClick={handlePrevious}/>
        {/*}*/}

        {/*{pageNation.next &&*/}
        <img src={than} alt={"than-icon"} className={'than-icon'} onClick={handleNext}/>
        {/*}*/}

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
                  <div className={'project-img'}>
                    <img
                      src={projectImgUrls[index] || logo}
                      alt="Project Image" className={'project-img'}
                      style={{
                        height: 120,
                        marginBottom: 20
                      }}
                    />

                  </div>
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
            )
              ;
          })}
        </div>
      </Common>
    );
  }
);

export default ProjectsItem;
