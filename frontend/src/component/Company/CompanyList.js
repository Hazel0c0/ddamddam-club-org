import React, {useEffect, useState} from 'react';
import Common from "../common/Common";
import CompanyTotal from "./Pagenation/CompanyTotal";
import './scss/CompanyList.scss'

const CompanyList = ({searchValue, searchKeyword}) => {
    const [pageTrue, setPageTrue] = useState({
        total: true,
        rating: false,
        view: false,
        search: false
    })


    // 전체 목록 리스트 출력
    useEffect(() => {
        console.log(`searchValue = ${searchKeyword}`)
        if (searchKeyword !== null && searchKeyword !== '') {
            setPageTrue({
                total: false,
                rating: false,
                view: false,
                search: true
            });
            return;
        }

        if (searchValue === '' || searchValue === '전체') {
            searchKeyword = '';
            setPageTrue({
                total: true,
                rating: false,
                view: false,
                search: false
            });
        } else if (searchValue === '평점순') {
            searchKeyword = '';
            setPageTrue({
                total: false,
                rating: true,
                view: false,
                search: false
            });
        } else if (searchValue === '조회순') {
            searchKeyword = '';
            setPageTrue({
                total: false,
                rating: false,
                view: true,
                search: false
            });
        }
    }, [searchValue, searchKeyword]);

    return (
        <Common className={'company-list-wrapper'}>
            <CompanyTotal/>
            {/*{pageTrue.total && <CompanyTotal/>}*/}
            {/*{pageTrue.rating && <ReviewRating loginCheck={loginCheck}/>}*/}
            {/*{pageTrue.view && <ReviewView loginCheck={loginCheck}/>}*/}
            {/*{pageTrue.search && <ReviewSearchKeyword loginCheck={loginCheck} searchKeyword={searchKeyword}/>}*/}
        </Common>
    );
};

export default CompanyList;