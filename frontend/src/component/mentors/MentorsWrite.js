import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/MentorWrite.scss';
import {MENTOR} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import { getToken, getUserIdx, getUserEmail, getUserName, getUserNickname, getUserRegdate,
    getUserBirth, getUserPosition, getUserCareer, getUserPoint, getUserProfile,
    getUserRole, isLogin } from '../common/util/login-util';
import {httpStateCatcherWrite} from "../common/util/HttpStateCatcherWrite";

const MentorsWrite = () => {
    const redirection = useNavigate();
    const ACCESS_TOKEN = getToken(); // 토큰

    // headers
      const headerInfo = {
      'content-type': 'application/json',
      'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

  const [textInput, setTextInput] = useState({
    mentorTitle: '',
    mentorContent: '',
    mentorSubject: '프론트엔드',
    mentorMentee: '1',
    mentorCurrent: ''
  });
  

    const handleSelect = (e) => {
        const {name, value} = e.target;
        let parseValue = value;

        if (name === 'mentorMentee') {
            parseValue = parseInt(value);
        }

        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            [name]: parseValue
        }));
    };


    const handleSubmit = () => {
        // 수집한 값들을 이용하여 비동기 POST 요청 수행
        const {
            mentorTitle,
            mentorSubject,
            mentorCurrent,
            mentorContent,
            mentorMentee
        } = textInput;

        if (mentorTitle.length === 0 || mentorCurrent.length === 0 || mentorContent.length === 0) {
            alert('공백 없이 입력해주세요.');
        } else {
            const data = {
                mentorTitle: mentorTitle,
                mentorContent: mentorContent,
                mentorSubject: mentorSubject,
                mentorCurrent: mentorCurrent,
                mentorMentee: +mentorMentee
            };
            // 비동기 POST 요청 처리 로직 작성
          //  console.log(data); // 확인을 위해 콘솔에 출력

            fetch(MENTOR, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(data)
            })
                .then(res => {
                  httpStateCatcherWrite(res.status);
                   // if(res.status === 400){
                   //  alert('다시 작성하세요');
                   // }
                    return res.json();
                })
                .then(json => {
                    if(json === undefined){
                        return;
                    }
                    alert('작성이 완료되었습니다.');
                    redirection('/mentors')
                    // window.location.href = 'http://localhost:3000/mentors';
                })
        }
        ;
    };

    return (
        <Common className={'mentors-write-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Mentoring</p>
                <p className={'main-sub-title'}>멘토링을 통해 성장하는 동료들과 함께 협업하고 새로운 아이디어를 발전시켜보세요.</p>
            </div>

            <section className={'write-form-wrapper'}>
                <div className={'title-input-wrapper'}>
                    <h1 className={'sub-title'}>제목</h1>
                    <input
                        type={"text"}
                        placeholder={'제목을 입력하세요'}
                        className={'title-text-input'}
                        name={'mentorTitle'}
                        value={textInput.mentorTitle}
                        onChange={handleSelect}
                    />
                </div>
                <div className={'select-input-wrapper'}>
                    <div className={'subject'}>
                        <h1 className={'sub-title'}>주제</h1>
                        <select className="subject-select"
                                onChange={handleSelect}
                                value={textInput.mentorSubject}
                                name="mentorSubject"
                        >
                            {/*<option disabled selected>fruits 🍊</option>*/}
                            <option value="프론트엔드">프론트엔드</option>
                            <option value="백엔드">백엔드</option>
                            <option value="취업진로">취업진로</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>

                    <div className={'mentee'}>
                        <h1 className={'sub-title'}>모집인원</h1>
                        <select className="mentee-text-input"
                                onChange={handleSelect}
                                value={textInput.mentorMentee}
                                name="mentorMentee"
                        >
                            <option value="1">1명</option>
                            <option value="2">2명</option>
                            <option value="3">3명</option>
                            <option value="4">4명</option>
                        </select>
                    </div>
                    <div className={'current'}>
                        <h1 className={'sub-title'}>현직</h1>
                        <input type={"text"}
                               placeholder={'현재 직장을 입력해주세요'}
                               name="mentorCurrent"
                               className={'current-text-input'}
                               value={textInput.mentorCurrent}
                               onChange={handleSelect}
                        />
                    </div>


                </div>
            </section>

            <section>
                <textarea type="text"
                          placeholder={"내용을 입력해주세요"}
                          className={'content'}
                          value={textInput.mentorContent}
                          name="mentorContent"
                          onChange={handleSelect}
                />
            </section>

            <div className={'btn-wrapper'}>
                <Link to={'/mentors'} className={'close-btn-a'}><button className={'close-btn'}>취소하기</button></Link>
                <button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>
            </div>
        </Common>
    );
};

export default MentorsWrite;