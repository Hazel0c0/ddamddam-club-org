import React, {useState} from 'react';
import CompanyMain from "./CompanyMain";
import CompanyList from "./CompanyList";
import {TfiArrowUp} from "react-icons/tfi";

const CompanyTemplate = () => {
    const [searchValue, setSearchValue] = useState('');
    const [searchKeyword, setSearchKeyword] = useState(' ');
    const [searchCareer, setSearchCareer] = useState('');
    // const [searchCareer, setSearchCareer]
    const handleSearchChange = (value) => {
        setSearchValue(value);
        // console.log(`Search Page에서 넘어오는 카테고리입력 값 : ${value}`)
    }

    const handleSearchKeyword = (value) => {
        if (value.trim() === ''){
            value = ` `
        }
        setSearchKeyword(value);
        // console.log(`Search Page에서 넘어오는 검색입력 값 : ${value}`)
    }

    const handleSearchCareer = (value) => {
               setSearchCareer(value);
        // console.log(`Search Page에서 넘어오는 경력입력 값 : ${value}`)
    }

    const scrollTop = () => {
        window.scrollTo(0, 0);
    }

    return (
        <>
            <CompanyMain
                onSearchChange={handleSearchChange}
                onSearchKeywordChange={handleSearchKeyword}
                onSearchCareerChange={handleSearchCareer}
            />
            <CompanyList
                searchValue={searchValue}
                searchKeyword={searchKeyword}
                searchCareer={searchCareer}
            />
            <button className={'go-top-btn'} onClick={scrollTop}>
                <TfiArrowUp className={'top-arrow'}/>
            </button>
        </>
    );
};

export default CompanyTemplate;