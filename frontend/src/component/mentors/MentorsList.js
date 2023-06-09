import React from 'react';
import Common from "../common/Common";
import './scss/MentorsList.scss';
import profile from "../../src_assets/IMG_4525.JPG"
const MentorsList = () => {
    return (
        <Common className={'mentors-list-wrapper'}>
            <div className={'mentors-list'}>
                <div className={'speech-bubble'}>
                    프론트엔드에 고민이 있으신가요?
                </div>
                <div className={'profile-img'}>
                </div>
                <div className={'list-text-wrapper'}>
                    <div className={'writer'}>홍길동</div>
                    <div className={'text'}>
                        안녕하세요! 당신을 도와드릴
                        멘토 홍길동입니다.
                        어려움을 겪고 계시면 신청해주세요!
                    </div>
                    <ul>
                        <li>프론트엔드</li>
                        <li>백엔드</li>
                        <li>취업진로</li>
                    </ul>
                    <div className={'career'}>홍길동</div>
                </div>
            </div>
        </Common>
    );
};

export default MentorsList;