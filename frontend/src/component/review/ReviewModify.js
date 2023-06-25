import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {Link, useNavigate, useParams} from "react-router-dom";
import './scss/ReviewWrite.scss';
import {getToken} from "../common/util/login-util";
import {REVIEW} from "../common/config/HostConfig";
import ReviewStarRating from "./StartRating/ReviewStarRating";
import Common from "../common/Common";

const ReviewModify = () => {
  const redirection = useNavigate();
  const {reviewIdx} = useParams();
  const [detailReview, setDetailReview] = useState([]);
  const [reviewRating, setReviewRating] = useState(detailReview.reviewRating);
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

  const ACCESS_TOKEN = getToken();
  const requestHeader = {
    'content-type': 'application/json',
    'Authorization': 'Bearer ' + ACCESS_TOKEN
  };

  // 수정 전 정보 띄워주기
  const asyncDetail = async () => {
    console.log(reviewIdx);
    const res = await fetch(`${REVIEW}/detail?reviewIdx=${reviewIdx}`, {
      method: 'GET',
      headers: requestHeader
    });

    if (res.status === 500) {
      alert('잠시 후 다시 접속해주세요.[서버오류]');
      return;
    }

    const result = await res.json();
    console.log(`result = `, result)
    setDetailReview(result);
  }

  const handleSelect = (e) => {
    const {name, value} = e.target;
    let parseValue = value;

    setTextInput((prevTextInput) => ({
      ...prevTextInput,
      [name]: parseValue
    }));
  };

  const handleSubmit = async () => {
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
      reviewLocation: reviewLocation
    };

    console.log(`요청 보낼 data : ${data}`);

    const res = await fetch(`${REVIEW}/modify/${reviewIdx}`, {
      method: 'PATCH',
      headers: requestHeader,
      body: JSON.stringify(data)
    });

    if (res.status === 400) {
      alert('잘못된 요청 값 입니다.')
      return;
    } else if (res.status === 401) {
      alert('로그인이 만료되었습니다.')
      window.location.href = "/";
    } else if (res.status === 403) {
      alert('권한이 없습니다.')
      window.location.href = "/";
    } else if (res.status === 404) {
      alert('요청을 찾을 수 없습니다.');
      return;
    } else if (res.status === 500) {
      alert('잠시 후 다시 접속해주세요.[서버오류]');
      return;
    } else if (res.status === 200) {
      alert('작성이 완료되었습니다.')
      redirection('/reviews')
    }
  };

  useEffect(() => {
    asyncDetail();



  }, []);

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
        <button className={'submit-btn'} onClick={handleSubmit}>수정완료</button>
      </div>
    </Common>
  );
};

ReviewModify.propTypes = {

};

export default ReviewModify;
