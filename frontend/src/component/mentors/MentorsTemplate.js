import React, {useEffect, useState} from 'react';
import MentorsMain from "./MentorsMain";
import MentorsList from "./MentorsList";
import {MENTOR} from "../common/config/HostConfig";
import {Route, Routes} from "react-router-dom";

const MentorsTemplate = () => {
    return (
        <>
            <MentorsMain/>
            <MentorsList/>
        </>
    );
};

export default MentorsTemplate;