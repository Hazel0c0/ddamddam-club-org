import React, {useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/MentorWrite.scss';

const MentorsWrite = () => {
    const [textInput, setTextInput] = useState(
        {
            mentorTitle: '',
            mentorContent: '',
            mentorSubject: 'frontEnd',
            mentorCurrent: '',
            mentorCareer: '신입',
            mentorMentee: 0
        }
    )

    const handleSelect = (e) => {
        const {name, value} = e.target;
        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            [name]: value
        }));
    };


    const handleSubmit = () => {
        // 수집한 값들을 이용하여 비동기 POST 요청 수행
        const {mentorTitle, mentorSubject, mentorCareer, mentorCurrent, mentorMentee} = textInput;
        const data = {
            title: mentorTitle,
            subject: mentorSubject,
            career: mentorCareer,
            current: mentorCurrent,
            mentee: mentorMentee
        };
        // 비동기 POST 요청 처리 로직 작성
        console.log(data); // 확인을 위해 콘솔에 출력
    };


    return (
        <Common className={'mentors-write-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Mentoring</p>
                <p className={'sub-title'}>멘토링을 통해 성장하는 동료들과 함께 협업하고 새로운 아이디어를 발전시켜보세요.</p>
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
                            <option value="frontEnd">프론트엔드</option>
                            <option value="backEnd">백엔드</option>
                            <option value="employment">취업진로</option>
                            <option value="etc">기타</option>
                        </select>
                    </div>

                    <div className={'career'}>
                        <h1 className={'sub-title'}>경력</h1>
                        <select className="career-select"
                                onChange={handleSelect}
                                value={textInput.mentorCareer}
                                name="mentorCareer"
                        >
                            <option value="신입">신입</option>
                            <option value="1년">1년 이상</option>
                            <option value="3년">3년 이상</option>
                            <option value="5년">5년 이상</option>
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

                    <div className={'mentee'}>
                        <h1 className={'sub-title'}>모집인원</h1>
                        <input type={"text"}
                               placeholder={'멘티 인원을 입력해주세요'}
                               name="mentorMentee"
                               className={'mentee-text-input'}
                               value={textInput.mentorMentee}
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
                <button className={'close-btn'}>취소하기</button>
                <button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>
            </div>
        </Common>
    );
};

export default MentorsWrite;