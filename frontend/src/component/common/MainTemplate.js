import React, {useEffect, useState} from 'react';
import Common from "./Common";
import TypeIt from "typeit-react";
import './scss/MainTemplate.scss';
import {SlArrowDown} from 'react-icons/sl';
import AddInfo from "./AddInfo";

const MainTemplate = () => {
    const inputText = 'Hi, We Are \nDDAMDDAM Club!';
    const subText = '땀땀클럽은 개발자들간의 커뮤니티 공간입니다.\n멘토링, 프로젝트 모집 등 다양한 콘텐츠를 즐겨보세요!';
    const [arrow, setArrow] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setArrow(true);
        }, 5800);
        return () => clearTimeout(timer);
    }, []);

    return (
        <Common>
            <section className={'top-wrapper'}>
                <div className={'input-font'}>
                    <TypeIt
                        getBeforeInit={(instance) => {
                            instance.type(";;").pause(1200).delete(2).pause(500).type(inputText);
                            return instance;
                        }}
                    />
                </div>
                <div className={'sub-title'}>{subText}</div>
                <div>
                    {arrow &&
                        <div className={'show-arrow-wrapper'}>
                            <p className={'arrow-text'}>스크롤을 아래로 내려 땀땀클럽을 구경해보세요!</p>
                            <div className={'arrow-icon'}><SlArrowDown/></div>
                        </div>
                    }
                </div>
            </section>

            <AddInfo/>

        </Common>

    )
        ;
};

export default MainTemplate;