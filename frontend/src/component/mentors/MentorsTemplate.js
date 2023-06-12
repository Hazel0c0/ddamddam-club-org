import React, {useEffect, useState} from 'react';
import MentorsMain from "./MentorsMain";
import MentorsList from "./MentorsList";
import {MENTOR} from "../common/config/HostConfig";
import {Route, Routes} from "react-router-dom";
import MentorsDetail from "./MentorsDetail";

const MentorsTemplate = () => {
    return (
        <>
            <MentorsMain/>
            <MentorsList/>
            <Routes>
                <Route path="/mentors/detail" element={<MentorsDetail />} />
            </Routes>
        </>
    );
};

export default MentorsTemplate;