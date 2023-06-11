import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import './scss/MentorsList.scss';
import profile from "../../src_assets/IMG_4525.JPG"

const MentorsList = () => {

    const [mentorsList, setMentorsList] = useState([]);
    const [pageNation, setPageNation] = useState([]);
    // fetch('http://localhost:8181/api/ddamddam/mentors/list?page=&size=&sort=')
    useEffect(() => {
        fetch('http://localhost:8181/api/ddamddam/mentors/list?page=1&size=9&sort=mentorDate')
            .then(res => {
                if (res.status === 500) {
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result => {
                if (!!result) {
                    setMentorsList(result.mentors);
                    setPageNation(result.pageInfo);
                }

            });
    }, []);

    return (
        <Common className={'mentors-list-wrapper'}>
            {mentorsList.map((mentor) => (
                <div className={'mentors-list'}>
                    <div className={'speech-bubble'} key={mentor.title}>
                        {mentor.title}
                    </div>
                    {/*Profile 수정 필요*/}
                    <div className={'profile-img'}></div>

                    <div className={'list-text-wrapper'}>
                        <div className={'writer'} key={mentor.nickName}>
                            {mentor.nickName}
                        </div>
                        <div className={'text'} key={mentor.content}>
                            {mentor.content}
                        </div>
                        <ul className={'category'} key={mentor.subject}>
                            <li>{mentor.subject}</li>
                        </ul>
                        <div className={'career'} key={mentor.current}>경력 : {mentor.current}</div>
                    </div>
                </div>
            ))}
        </Common>
    );
};

export default MentorsList;