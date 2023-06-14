import React from 'react';
import Common from "../common/Common";
import './scss/QnaSearch.scss';
import searchIcon from '../../src_assets/search-icon.png';
const QnaSearch = () => {
    return (
        <Common className={'qna-search-wrapper'}>
            <ul className={'sort-btn'}>
                <li>전체</li>
                <li>미채택</li>
                <li>채택완료</li>
            </ul>
            <div className={'search-wrapper'}>
                <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                <input className={'input-btn'} placeholder={'검색창'} name={'search'}></input>
            </div>
            <button className={'write-btn'}>작성하기</button>
        </Common>
    );
};

export default QnaSearch;