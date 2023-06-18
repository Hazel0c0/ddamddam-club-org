import React from 'react';
import {TbStarFilled} from 'react-icons/tb';
import {TbStarHalfFilled} from 'react-icons/tb';
import {TbStar} from 'react-icons/tb'
const ReviewStar = ({starCount}) => {
    const totalStars = 5; // 총 별 개수

    // 별 개수 계산
    const fullStars = Math.floor(starCount); // 정수 부분 별 개수
    const hasHalfStar = starCount % 1 !== 0; // 0.5점 별 유무

    // 별 그리기
    const stars = [];
    for (let i = 0; i < fullStars; i++) {
        stars.push(<span key={i}><TbStarFilled className={'star'}/></span>); // 별 아이콘
    }
    if (hasHalfStar) {
        stars.push(<span key={fullStars}><TbStarHalfFilled className={'star'}/></span>); // 반 별 아이콘
    }

    // 빈 별 그리기
    // 빈 별 그리기
    const emptyStars = totalStars - fullStars - (hasHalfStar ? 1 : 0);
    for (let i = 0; i < emptyStars; i++) {
        stars.push(<span key={`empty-star-${i}`}><TbStar className={'star'}/></span>); // 빈 별 아이콘
    }


    return (
        <div>
            {stars}
        </div>
    );
};

export default ReviewStar;