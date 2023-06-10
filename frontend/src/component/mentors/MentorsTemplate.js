import React, {useEffect, useState} from 'react';
import MentorsMain from "./MentorsMain";
import MentorsList from "./MentorsList";
import {MENTOR} from "../common/config/HostConfig";

const MentorsTemplate = () => {
    const [mentorsList, setMentorsList] = useState([]);

    useEffect( () =>{
        fetch(MENTOR)
            .then(res =>{
                if (res.status === 500){
                    alert('잠시 후 다시 접속해주세요.[서버오류]');
                    return;
                }
                return res.json();
            })
            .then(result =>{
                if (!!result){
                    setMentorsList(result);
                }
            });
        });

    return (
        <>
            <MentorsMain/>
            <MentorsList mentorList = {mentors}/>
        </>
    );
};

export default MentorsTemplate;