import React, {useCallback, useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/ReviewWrite.scss';
import {REVIEW} from "../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import ReviewStarRating from "./StartRating/ReviewStarRating";
import {getToken} from "../common/util/login-util";
// import Tags from '@yaireo/tagify/dist/react.tagify';
// import {getWhitelistFromServer, getValue} from './hashTagConfig/mockServer'

const MentorsWrite = () => {
    const ACCESS_TOKEN = getToken();
    const redirection = useNavigate();
    const [reviewRating, setReviewRating] = useState(0);
    const [textInput, setTextInput] = useState(
        {
            reviewTitle: '',
            reviewContent: '',
            reviewJob: '',
            reviewTenure: '',
            companyName: '',
            reviewLocation: '',
        }
    )

    const handleSelect = (e) => {
        const {name, value} = e.target;
        let parseValue = value;

        setTextInput((prevTextInput) => ({
            ...prevTextInput,
            [name]: parseValue
        }));
    };


    const handleSubmit = async () => {
        // console.log(textInput);

        // 수집한 값들을 이용하여 비동기 POST 요청 수행
        const {
            reviewTitle,
            reviewContent,
            reviewJob,
            reviewTenure,
            companyName,
            reviewLocation,
        } = textInput;

        for (let i = 0; i < textInput.length; i++) {
            if (textInput[i] === '' || textInput[i] === 0) {
                alert('공백없이 입력해주세요.')
                return;
            }
        }

        const data = {
            reviewTitle: reviewTitle,
            reviewContent: reviewContent,
            reviewRating: reviewRating,
            reviewJob: reviewJob,
            reviewTenure: reviewTenure,
            companyName: companyName,
            reviewLocation: reviewLocation,
        };
        // 비동기 POST 요청 처리 로직 작성
        console.log(data); // 확인을 위해 콘솔에 출력


        const res = await fetch(REVIEW + '/write', {
            method: 'POST',
            headers: {
                'content-type': 'application/json',
                // 'Authorization': 'Bearer ' + ACCESS_TOKEN
            },
            body: JSON.stringify(data)
        });
        if (res.status === 500) {
            console.log(`서버 오류`);
            return;
        }
        if (res.status === 400) {
            const text = await res;
            console.log(`오류시 알람 : ${text}`);
            return;
        } else {
            alert('작성이 완료되었습니다.')
            redirection('/reviews')
        }
    };


    useEffect(() => {
    }, [reviewRating])


    return (
        <Common className={'review-write-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>취업 후기</p>
                <p className={'main-sub-title'}>근무했던 기업에 대한 정보를 공유해보세요.</p>
            </div>

            <section className={'write-form-wrapper'}>
                    <h1 className={'sub-title'}>제목</h1>
                    <input
                        type={"text"}
                        placeholder={'제목을 입력하세요'}
                        className={'title-text-input'}
                        name={'reviewTitle'}
                        defaultValue={textInput.reviewTitle}
                        onChange={handleSelect}
                    />
            </section>

            <section className={'review-company-detail'}>
                <div className={'input-company-name'}>
                    <h1 className={'sub-title'}>회사명</h1>
                    <input
                        type={"text"}
                        placeholder={'ex.땀땀컴퍼니'}
                        className={'detail-text-input'}
                        name={'companyName'}
                        defaultValue={textInput.companyName}
                        onChange={handleSelect}/>
                </div>
                <div className={'input-company-job'}>
                    <h1 className={'sub-title'}>직무</h1>
                    <input
                        type={"text"}
                        placeholder={'ex.백엔드 개발자'}
                        className={'detail-text-input'}
                        name={'reviewJob'}
                        defaultValue={textInput.reviewJob}
                        onChange={handleSelect}/>
                </div>

                <div className={'input-company-location'}>
                    <h1 className={'sub-title'}>위치</h1>
                    <input
                        type={"text"}
                        placeholder={'ex.강남구 신사동'}
                        className={'detail-text-input'}
                        name={'reviewLocation'}
                        defaultValue={textInput.reviewLocation}
                        onChange={handleSelect}/>
                </div>

                <div className={'input-company-tenure'}>
                    <h1 className={'sub-title'}>근속년수</h1>
                    <input
                        type={"text"}
                        placeholder={''}
                        className={'tenure-text-input'}
                        name={'reviewTenure'}
                        defaultValue={textInput.reviewTenure}
                        onChange={handleSelect}/>
                    <span className={'fix-text'}>년</span>
                </div>
            </section>

            <section className={'content-wrapper'}>
                <textarea type="text"
                          placeholder={"내용을 입력해주세요"}
                          className={'content'}
                          defaultValue={textInput.reviewContent}
                          name="reviewContent"
                          onChange={handleSelect}
                />
                <div className={'star-rating-wrapper'}>
                    <span className={'star-rating-title'}>이 기업의 평점을 남겨주세요!</span>
                    <ReviewStarRating reviewRating={reviewRating} setReviewRating={setReviewRating} />
                </div>
            </section>

            <div className={'btn-wrapper'}>
                <Link to={'/qna'} className={'close-btn-a'}>
                    <button className={'close-btn'}>취소하기</button>
                </Link>
                {/*<button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>*/}
                <button className={'submit-btn'} onClick={handleSubmit}>작성완료</button>
            </div>
        </Common>
    );
};

export default MentorsWrite;