import React, {useState} from 'react';
import Common from "../common/Common";
import './scss/ReviewSearch.scss';
import searchIcon from '../../src_assets/search-icon.png';
import {Link} from "react-router-dom";

const QnaSearch = ({onSearchChange}) => {
    const [selectedBtn, setSelectedBtn] = useState('전체');

    const handleInputChange = (e) => {
        const value = e.target.textContent;
        // console.log(value);
        onSearchChange(value);
        setSelectedBtn(value);
    }

    //게시글 작성하기
    const qnaWriteHandler = () => {

    }

    return (
        <Common className={'qna-search-wrapper'}>
            <ul className={'sort-btn'}>
                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '전체' ? 'selected' : ''}
                >
                    전체
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '평점순' ? 'selected' : ''}
                >
                    평점순
                </li>

                <li
                    onClick={handleInputChange}
                    className={selectedBtn === '조회순' ? 'selected' : ''}
                >
                    조회순
                </li>

            </ul>
            <div className={'search-wrapper'}>
                <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                <input className={'input-btn'} placeholder={'검색창'} name={'search'}></input>
            </div>
            <Link to={'/reviews/write'}>
                <button className={'write-btn'}>작성하기</button>
            </Link>
        </Common>
    );
};

export default QnaSearch;