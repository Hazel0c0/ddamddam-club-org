import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import {Link} from "react-router-dom";
import {MENTOR} from "../common/config/HostConfig";
import './scss/MentorsMain.scss';
import MentorsList from "./MentorsList";

const MentorsMain = () => {

  const [selectedSubjects, setSelectedSubjects] = useState([]);

  useEffect(() => {
    console.log(selectedSubjects)
  }, [selectedSubjects]);

  // 카테고리 추가 또는 제거
  const toggleSubject = (subject) => {
    if (selectedSubjects.includes(subject)) {
      // If the subject is already selected, remove it from the array
      setSelectedSubjects(prevSubjects => prevSubjects.filter(prevSubject => prevSubject !== subject));
    } else {
      // If the subject is not selected yet, add it to the array
      setSelectedSubjects(prevSubjects => [...prevSubjects, subject]);
    }
    // console.log(selectedSubjects);
  }

  // 서버에 요청 보내기
  const sendRequest = () => {
    // TODO: subjects를 이용해 실제 서버에 요청을 보내는 코드를 작성하십시오.
    const subjectsParam = selectedSubjects.join(',');
    const url = `${MENTOR}/sublist?page=1&size=9&subjects=${subjectsParam}`;

    fetch(url)
      .then(/* ... */);

    console.log(selectedSubjects);
    console.log(`url값 : ${url}`);
  }

  return (
    <Common className={'mentors-top-wrapper'}>
      <div className={'title-wrapper'}>
        <p className={'main-title'}>Mentoring</p>
        <p className={'sub-title'}>멘토링을 통해 성장하는 동료들과 함께 협업하고 새로운 아이디어를 발전시켜보세요.</p>
      </div>
      <div className={'mentors-medium-wrapper'}>
        <div className={'mentors-sort-list'}>
          <div className={'sort-title'}>정렬</div>
          {/*<Link to="/mentors?subject=프론트엔드" onClick={() => toggleSubject('프론트엔드')} className={'sort-front'}>프론트엔드</Link>*/}
          {/*<Link to="/mentors?subject=백엔드" onClick={() => toggleSubject('백엔드')} className={'sort-back'}>백엔드</Link>*/}
          {/*<Link to="/mentors?subject=취업·진로" onClick={() => toggleSubject('취업·진로')} className={'sort-employee'}>취업・진로</Link>*/}
          {/*<Link to="/mentors?subject=기타" onClick={() => toggleSubject('기타')} className={'sort-etc'}>기타</Link>*/}
          <button
            onClick={() => toggleSubject('프론트엔드')}
            className={`${selectedSubjects.includes('프론트엔드') ? 'selected' : 'sort-front'}`}
          >
            프론트엔드
          </button>
          <button
            onClick={() => toggleSubject('백엔드')}
            className={` ${selectedSubjects.includes('백엔드') ? 'selected' : 'sort-back'}`}
          >
            백엔드
          </button>
          <button
            onClick={() => toggleSubject('취업·진로')}
            className={` ${selectedSubjects.includes('취업·진로') ? 'selected' : 'sort-employee'}`}
          >
            취업・진로
          </button>
          <button
            onClick={() => toggleSubject('기타')}
            className={` ${selectedSubjects.includes('기타') ? 'selected' : 'sort-etc'}`}
          >
            기타
          </button>
        </div>
        <Link to={'/mentors/write'} className={'mentors-write-btn'}>
          멘토 모집하기
        </Link>
      </div>
      <MentorsList selectedSubjects={selectedSubjects}/>
    </Common>

  );
};

export default MentorsMain;
