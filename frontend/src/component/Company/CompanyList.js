import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import CompanyTotal from "./Pagenation/CompanyTotal";
import './scss/CompanyList.scss'
import ReviewTotal from "../review/Pagination/ReviewTotal";
import CompanyFront from "./Pagenation/CompanyFront";
import CompanyBack from "./Pagenation/CompanyBack";

const CompanyList = ({searchValue, searchKeyword,searchCareer}) => {

    // 전체 목록 리스트 출력
    useEffect(() => {
        console.log(`count : `,count)
    }, [searchValue, searchKeyword]);

    //프론트, 백, 전체 갯수
    const [count,setCount] = useState([]);
    const handleCount = (values) =>{
        setCount(values);
    }

    return (
        <Common className={'company-list-wrapper'}>
            {searchValue === '' &&
                <CompanyTotal
                    searchKeyword={searchKeyword}
                    searchValue={searchValue}
                    searchCareer={searchCareer}
                    onCountChange={handleCount}
                />}
            {searchValue === 'front' &&
                <CompanyFront
                    searchKeyword={searchKeyword}
                    searchValue={searchValue}
                    searchCareer={searchCareer}
                    onCountChange={handleCount}
                />}
            {searchValue === 'back' &&
                <CompanyBack
                    searchKeyword={searchKeyword}
                    searchValue={searchValue}
                    searchCareer={searchCareer}
                    onCountChange={handleCount}
                />}
        </Common>
    );
};

export default CompanyList;