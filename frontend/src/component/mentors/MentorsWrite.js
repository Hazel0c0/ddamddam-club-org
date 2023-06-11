import React from 'react';
import Common from "../common/Common";

const MentorsWrite = () => {
    return (
        <Common className={'mentors-top-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>Mentoring</p>
                <p className={'sub-title'}>멘토링을 통해 성장하는 동료들과 함께 협업하고 새로운 아이디어를 발전시켜보세요.</p>
            </div>

            <section className={'write-form-wrapper'}>
                <div className={'title-input-wrapper'}>
                    <h1>제목</h1>
                    <input type={"text"} placeholder={'제목을 입력하세'}/>
                </div>
                <div className={'select-input-wrapper'}>

                </div>
            </section>
        </Common>
    );
};

export default MentorsWrite;