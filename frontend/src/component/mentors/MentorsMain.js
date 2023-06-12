import React from 'react';
import Common from "../common/Common";
import {Link} from "react-router-dom";
import './scss/MentorsMain.scss'
import MentorsList from "./MentorsList";

const MentorsMain = () => {
    return (
        <Common className={'mentors-top-wrapper'}>
                <div className={'title-wrapper'}>
                    <p className={'main-title'}>Mentoring</p>
                    <p className={'sub-title'}>멘토링을 통해 성장하는 동료들과 함께 협업하고 새로운 아이디어를 발전시켜보세요.</p>
                </div>
                <div className={'mentors-medium-wrapper'}>
                    <div className={'mentors-sort-list'}>
                        <div className={'sort-title'}>정렬</div>
                        <input type="button" className={'sort-front'} value={'프론트엔드'} />
                        <input type="button" className={'sort-back'} value={'백엔드'} />
                        <input type="button" className={'sort-employee'} value={'취업・진로'} />
                        <input type="button" className={'sort-etc'} value={'기타'} />
                    </div>

                    <Link to={'/mentors/write'} className={'mentors-write-btn'}>
                        멘토 모집하기
                    </Link>
                    {/*<MentorsList />*/}
                </div>
        </Common>
    );
};

export default MentorsMain;