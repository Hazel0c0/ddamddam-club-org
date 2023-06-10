import React, {useEffect, useState} from 'react';
import MentorsMain from "./MentorsMain";
import MentorsList from "./MentorsList";
import {MENTOR} from "../common/config/HostConfig";

const MentorsTemplate = () => {
    return (
        <>
            <MentorsMain/>
            <MentorsList/>
        </>
    );
};

export default MentorsTemplate;