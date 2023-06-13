import React, { useState } from 'react';
import Common from "../common/Common";
import { Link } from "react-router-dom";
import { MENTOR } from "../common/config/HostConfig";
import './scss/MentorsMain.scss';

const MentorsMain = () => {

    const [selectedSubjects, setSelectedSubjects] = useState([]);

  // 카테고리 추가 또는 제거
  const toggleSubject = (subject) => {
    if (selectedSubjects.includes(subject)) {
      // If the subject is already selected, remove it from the array
      setSelectedSubjects(prevSubjects => prevSubjects.filter(prevSubject => prevSubject !== subject));
    } else {
      // If the subject is not selected yet, add it to the array
      setSelectedSubjects(prevSubjects => [...prevSubjects, subject]);
    }
  }

  // 서버에 요청 보내기
  const sendRequest = () => {
    // TODO: subjects를 이용해 실제 서버에 요청을 보내는 코드를 작성하십시오.
    fetch(MENTOR + '/list?page=1&size=9&subjects=' + encodeURIComponent(selectedSubjects.join(',')))
      .then(/* ... */)
      // ...
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
          <Link to="/mentors?subject=프론트엔드" onClick={() => toggleSubject('프론트엔드')} className={'sort-front'}>프론트엔드</Link>
        <Link to="/mentors?subject=백엔드" onClick={() => toggleSubject('백엔드')} className={'sort-back'}>백엔드</Link>
        <Link to="/mentors?subject=취업·진로" onClick={() => toggleSubject('취업·진로')} className={'sort-employee'}>취업・진로</Link>
        <Link to="/mentors?subject=기타" onClick={() => toggleSubject('기타')} className={'sort-etc'}>기타</Link>

        </div>

        <Link to={'/mentors/write'} className={'mentors-write-btn'}>
          멘토 모집하기
        </Link>
        {/* <MentorsList /> */}
      </div>
    </Common>
  );
};

export default MentorsMain;
