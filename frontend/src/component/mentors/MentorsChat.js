import React, {useEffect, useState} from 'react';
import {TfiClose} from "react-icons/tfi";
import Common from "../common/Common";
import {useParams} from "react-router-dom";
import {MENTOR} from "../common/config/HostConfig";
import './scss/MentorChat.scss';

const MentorsChat = () => {
    const {chatPageIdx} = useParams();
    const [detailMember, setDetailMember] = useState([]);

    useEffect(() => {
        fetch(MENTOR + '/detail?mentorIdx=' + chatPageIdx)
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                setDetailMember(result);
                console.log(result);
                console.log(result.idx);
            });
    }, []);

    const {career, content, current, date, idx, mentee, nickName, profile, subject, title} = detailMember;

    return (
        <Common className={'mentors-list-wrapper'}>
            <div className={'mentor-detail-wrapper'}>
                <section className={'top-section'}>
                    <div className={'top-title'}>
                        <h1 className={'top-title-text'}>멘토 소개</h1>
                        <div className={'write-date'}>{date}</div>
                    </div>

                    <div className={'close-btn'}><TfiClose/></div>
                </section>

                <section className={'writer-section'}>
                    <div className={'detail-profile-img'}></div>
                    <div className={'writer-text-wrapper'}>
                        <h2 className={'detail-writer'}>{nickName}</h2>
                        <h3 className={'detail-sub-title'}>{title}</h3>
                        <div className={'etc-wrapper'}>
                            <div className={'member-count'}><p className={'detail-sub-text'}>인원</p>{mentee}명 모집</div>
                            <div className={'subject'}><p className={'detail-sub-text'}>주제</p>{subject}</div>
                            <div className={'career'}><p className={'detail-sub-text'}>경력</p>{career}</div>
                            <div className={'current'}><p className={'detail-sub-text'}>현직</p>{current}</div>
                        </div>
                    </div>
                </section>

                <section className={'main-section'}>
                    <div className={'main-section-text'}>
                        {content}
                    </div>
                </section>

                <div className={'btn-wrapper'}>
                    <button className={'application-btn'}>멘토링중</button>
                </div>
            </div>
        </Common>


    );
};

export default MentorsChat;