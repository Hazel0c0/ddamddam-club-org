import React, {useState} from 'react';
import ReviewSearch from "./ReviewSearch";
import ReviewMain from "./ReviewMain";
import ReviewList from "./ReviewList";

const QnaTemplate = () => {
    const [searchValue, setSearchValue] = useState('');
    const [searchKeyword, setSearchKeyword] = useState('');

    const handleSearchChange = (value) => {
        setSearchValue(value);
        // console.log(`Search Page에서 넘어오는 카테고리입력 값 : ${value}`)
    }

    //카테고리 값
    const handleSearchKeyword = (value) => {
        setSearchKeyword(value);
        // console.log(`Search Page에서 넘어오는 검색입력 값 : ${value}`)
    }

    return (
        <>
            <ReviewMain />
            <ReviewSearch onSearchChange={handleSearchChange} onSearchKeywordChange={handleSearchKeyword}/>
            <ReviewList searchValue = {searchValue} searchKeyword={searchKeyword}/>
        </>
    );
};

export default QnaTemplate;