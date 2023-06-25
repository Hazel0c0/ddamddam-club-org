import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import CompanyTotal from "./Pagenation/CompanyTotal";
import './scss/CompanyList.scss'
import ReviewTotal from "../review/Pagination/ReviewTotal";

const CompanyList = ({searchValue, searchKeyword,searchCareer}) => {

    // 전체 목록 리스트 출력
    useEffect(() => {

    }, [searchValue, searchKeyword]);

    return (
        <Common className={'company-list-wrapper'}>
            {searchValue === '' && <CompanyTotal searchKeyword={searchKeyword} searchValue={searchValue} searchCareer={searchCareer}/>}
            {searchValue === 'front' && <CompanyTotal searchKeyword={searchKeyword} searchValue={searchValue} searchCareer={searchCareer}/>}
            {searchValue === 'back' && <CompanyTotal searchKeyword={searchKeyword} searchValue={searchValue} searchCareer={searchCareer}/>}
        </Common>
    );
};

export default CompanyList;