import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import CompanyTotal from "./Pagenation/CompanyTotal";
import './scss/CompanyList.scss'
import ReviewTotal from "../review/Pagination/ReviewTotal";
import CompanyFront from "./Pagenation/delete/CompanyFront_Temporary";
import CompanyBack from "./Pagenation/delete/CompanyBack_Temporary";

const CompanyList = ({searchValue, searchKeyword,searchCareer}) => {
    // 전체 목록 리스트 출력
    useEffect(() => {
        console.log('useEffect CompanyList 실행')
        console.log(`searchValue : `,searchValue,` searchKeyword : `,searchKeyword,`searchCareer : `,searchCareer )
    }, [searchValue, searchKeyword,searchCareer]);

    return (
        <Common className={'company-list-wrapper'}>
            {searchValue === '' &&
                <CompanyTotal
                    searchKeyword={searchKeyword}
                    searchValue={searchValue}
                    searchCareer={searchCareer}
                />}
            {searchValue === 'front' &&
                <CompanyFront
                    searchKeyword={searchKeyword}
                    searchValue={searchValue}
                    searchCareer={searchCareer}
                />}
            {searchValue === 'back' &&
                <CompanyBack
                    searchKeyword={searchKeyword}
                    searchValue={searchValue}
                    searchCareer={searchCareer}
                />}
        </Common>
    );
};

export default CompanyList;