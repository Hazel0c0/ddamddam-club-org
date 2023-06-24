import React, {useState} from 'react';
import QnaMain from "./QnaMain";
import QnaSearch from "./QnaSearch";
import QnaList from "./QnaList";

const QnaTemplate = () => {
    const [searchValue, setSearchValue] = useState('');
    const [searchKeyword, setSearchKeyword] = useState('');
    const handleSearchChange = (value) => {
        setSearchValue(value);
        // console.log(`Search Page에서 넘어오는 카테고리입력 값 : ${value}`)
    }

    const handleSearchKeyword = (value) => {
        setSearchKeyword(value);
        console.log(`Search Page에서 넘어오는 검색입력 값 : ${value}`)
    }

    return (
        <>
            <QnaMain/>
            <QnaSearch onSearchChange={handleSearchChange} onSearchKeywordChange={handleSearchKeyword}/>
            <QnaList searchValue={searchValue} searchKeyword={searchKeyword}/>
        </>
    );
};

export default QnaTemplate;