import React, {useEffect, useState} from 'react';
import "./scss/MypageProjectList.scss";
import {RiVipCrownFill} from "react-icons/ri";
import {getToken, isLogin} from "../common/util/login-util";
import {BASE_URL, MYPAGE} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import less from "../../src_assets/less.png";
import than from "../../src_assets/than.png";
import {httpStateCatcher} from "../common/util/HttpStateCatcherWrite";

const MypageProjectList = props => {

  const ACCESS_TOKEN = getToken();
  const API_BASE_URL = BASE_URL + MYPAGE;
  const redirection = useNavigate();

  // headers
  const headerInfo = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  }

  const [projectList, setProjectList] = useState([]);
  const [pageNation, setPageNation] = useState([]);
  const [carouselIndex, setCarouselIndex] = useState(1);

  const subStringContent = (str, n) => {
    return str?.length > n
      ? str.substr(0, n - 1) + "..."
      : str;
  }

  const handlePrevious = () => {
    if (pageNation.prev === true) {
      setCarouselIndex(prevIndex => prevIndex - 1);
    }
  };

  const handleNext = () => {
    if (pageNation.next === true) {
      setCarouselIndex(prevIndex => prevIndex + 1);
    }
  };

  // 로그인 상태 검증 핸들러
  const loginCheckHandler = (e) => {
    console.log(`ACCESS_TOKEN = ${ACCESS_TOKEN}`)
    if (!isLogin) {
      alert('로그인 후 이용가능합니다.')
      e.preventDefault();
      redirection('/login');
    }
  }

  const asyncProjectList = async () => {

    // console.log(`ACCESS_TOKEN : ${ACCESS_TOKEN}`); // 토큰 잘 나옴;;

    // http://localhost:8181/api/ddamddam/mypage/project-list?page=1&size=3
    const res = await fetch(`${API_BASE_URL}/project-list?page=${carouselIndex}&size=3`, {
      method: 'GET',
      headers: headerInfo,
    });

    httpStateCatcher(res.status);

    // 오류 없이 값을 잘 받아왔다면
    const result = await res.json();
    // console.log(`result :`, result);
    setProjectList(result.posts);
    setPageNation(result.pageInfo);
  };

  // 첫 렌더링 시 작성 게시글 전체 출력
  useEffect(() => {
    asyncProjectList();
  }, [carouselIndex]);

  return (
    <div className={'mypage-pj-wrapper'}>

      {projectList.length === 0 ? (
        <div>참여중인 프로젝트가 없습니다.</div>
      ) : null}

      {pageNation.prev &&
        <img src={less} alt={"less-icon"} className={'less-icon'} onClick={handlePrevious}/>
      }
      <div className={'pj-wrapper'}>
        {projectList.map((project, index) => (
          <div className={'pj-box'}>
            <Link to={`/projects/detail?projectIdx=${project.boardIdx}`} onClick={loginCheckHandler}>
              <div className={'pj-title'}>{subStringContent(project.boardTitle, 35)}</div>
            </Link>
            <div className={'pj-writer small-text'}>
            </div>
            {/*백, 프론트 리스트 뿌리기*/}
            <div className={'participants-list'}>
              <div className={'list-box'}>
                <p className={'pj-sub-title small-text'}>FRONT</p>
                <div className={'participants-box'}>
                  {
                    project.writerPosition === 'FRONTEND'
                      ? <p className={'small-text writer'}>
                        <RiVipCrownFill/>
                        &nbsp;{project.boardWriter}
                      </p>
                      : null
                  }
                  {project.front.map((front) => (
                    <p className={'small-text'}>{front}</p>
                  ))}
                </div>
              </div>
              <div className={'list-box'}>
                <p className={'pj-sub-title small-text'}>BACK</p>
                {
                  project.writerPosition === 'BACKEND'
                    ? <p className={'small-text writer'}>
                      <RiVipCrownFill/>
                      &nbsp;{project.boardWriter}
                    </p>
                    : null
                }
                {project.back.map((back) => (
                  <p className={'small-text'}>{back}</p>

                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
      {pageNation.next &&
        <img src={than} alt={"than-icon"} className={'than-icon'} onClick={handleNext}/>
      }

    </div>
  );
};

export default MypageProjectList;
