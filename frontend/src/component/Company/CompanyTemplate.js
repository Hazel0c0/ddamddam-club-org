import React, {useState} from 'react';
import CompanyMain from "./CompanyMain";
import CompanyList from "./CompanyList";

const CompanyTemplate = () => {
    const [searchValue, setSearchValue] = useState('');
    const [searchKeyword, setSearchKeyword] = useState('');

    const handleSearchChange = (value) => {
        setSearchValue(value);
        console.log(`Search Page에서 넘어오는 카테고리입력 값 : ${value}`)
    }

    const handleSearchKeyword = (value) => {
        setSearchKeyword(value);
        console.log(`Search Page에서 넘어오는 검색입력 값 : ${value}`)
    }

    return (
        <>
            <CompanyMain onSearchChange={handleSearchChange} onSearchKeywordChange={handleSearchKeyword}/>
            <CompanyList searchValue = {searchValue} searchKeyword={searchKeyword}/>
        </>
    );
};

export default CompanyTemplate;