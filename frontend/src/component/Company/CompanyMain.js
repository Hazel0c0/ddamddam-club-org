import React, {useEffect, useRef, useState} from 'react';
import Common from "../common/Common";
import './scss/CompanyMain.scss'
import searchIcon from "../../src_assets/search-icon.png";
import {GrPowerReset} from "react-icons/gr";
import {COMPANY} from "../common/config/HostConfig";
import UseDebounce from "../user/UseDebounce";
import useDebounce from "../user/UseDebounce";

const CompanyMain = ({onSearchChange, onSearchKeywordChange, onSearchCareerChange}) => {
    const [selectedBtn, setSelectedBtn] = useState('전체');
    const inputVal = useRef();
    const careerVal = useRef();

    const handleInputChange = (e) => {
        const value = e.target.closest('.frontend-filter');
        // if (value) {
        //     onSearchChange("front");
        //     setSelectedBtn("front");
        // } else {
        //     const value = e.target.closest('.backend-filter');
        //     if (value) {
        //         onSearchChange("back");
        //         setSelectedBtn("back");
        //     } else {
        //         onSearchChange("");
        //         setSelectedBtn("");
        //     }
        // }
    }
    // const searchHandler = (e) => {
    //     // onSearchKeywordChange(e.target.value);
    //     // //엔터
    //     // if (e.keyCode === 13) {
    //     //     onSearchKeywordChange(e.target.value);
    //     // }
    //     // if (e.target.value === '') {
    //     //     onSearchKeywordChange('');
    //     // }
    //     const searchValue = e.target.value;
    //     const debouncedSearchValue = UseDebounce(searchValue, 500);
    //
    //     useEffect(() => {
    //         onSearchKeywordChange(debouncedSearchValue); // 디바운스된 검색어를 사용하여 검색 실행
    //     }, [debouncedSearchValue]);
    //
    //     if (e.keyCode === 13 || searchValue === '') {
    //         onSearchKeywordChange(searchValue);
    //     }
    // }


        //리셋버튼
    const resetHandler = () => {
        inputVal.current.value = '';
        onSearchKeywordChange('');
        onSearchCareerChange('')
        careerVal.current.value = '전체';
    }

    //경력 선택 버튼
    const careerHandler = (e) => {
        let careerValue = e.target.value;
        console.log(careerValue)
        if (careerValue==='전체'){
            careerValue='';
        }
        onSearchCareerChange(careerValue);
    }

    //갯수 출력
    const [count,setCount] = useState({totalCount :0, frontCount:0,backCount:0});
    useEffect(()=>{
        fetchCount();
    },[])

    const fetchCount = async () =>{

        //1.전체
        let res = await fetch(
            `${COMPANY}/searchAll?keyword= &page=1&size=10&career=`,
            {
                method: 'GET',
                headers: {'content-type': 'application/json'}
            });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        let result = await res.json();
        let totalCount = result.pageInfo.totalCount;
        setCount((prev)=>({...prev, totalCount : totalCount}));

        console.log(`1.전체 count : `,totalCount)

        //2.프론트
        res = await fetch(
            `${COMPANY}/searchFront?keyword= &page=1&size=10&career=`,
            {
                method: 'GET',
                headers: {'content-type': 'application/json'}
            });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        result = await res.json();
        totalCount = result.pageInfo.totalCount;
        setCount((prev)=>({...prev, frontCount : totalCount}));

        console.log(`2.프론트엔드 count : `,totalCount)

        //3.백
        res = await fetch(
            `${COMPANY}/searchBack?keyword= &page=1&size=10&career=`,
            {
                method: 'GET',
                headers: {'content-type': 'application/json'}
            });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        result = await res.json();
        totalCount = result.pageInfo.totalCount;
        setCount((prev)=>({...prev, backCount : totalCount}));

        console.log(`3.백 count : `,totalCount)

    }


    return (
        <Common className={'company-top-wrapper'}>
            <div className={'title-wrapper'}>
                <p className={'main-title'}>채용공고</p>
                <p className={'main-sub-title'}>
                    땀땀클럽은 최신 채용 정보와 고용 관련을 위해 워크넷 API와 연동되었습니다.
                    <br/>다양한 직업 정보와 채용 소식을 신속하게 확인해보세요!
                </p>
            </div>

            <section className={'select-view-wrapper'}>
                <div className={'frontend-filter'} onClick={handleInputChange}>
                    <div className={'frontend-title'}>
                        <span className={'title-text selected'}>프론트엔드</span>
                        <span className={'title-add'}>+채용공고 보러가기</span>
                    </div>
                    <span className={'frontend-count'}>{count.frontCount}</span>
                </div>

                <div className={'backend-filter'} onClick={handleInputChange}>
                    <div className={'backend-title'}>
                        <span className={'title-text'}>백엔드</span>
                        <span className={'title-add'}>+채용공고 보러가기</span>
                    </div>
                    <span className={'backend-count'}>{count.backCount}</span>
                </div>

                <div className={'total-filter'} onClick={handleInputChange}>
                    <div className={'total-title'}>
                        <span className={'title-text'}>개발직군 전체</span>
                        <span className={'title-add'}>+채용공고 보러가기</span>
                    </div>
                    <span className={'total-count'}>{count.totalCount}</span>
                </div>
            </section>

            {/*------------------------------------------------------------------------*/}
            <section className={'search-container'}>
                <div className={'search-wrapper'}>
                    <span className={'search-title'}>Search Keyword</span>
                    <div className={'search-input'}>
                        <img src={searchIcon} alt={'search-icon'} className={'search-icon'}/>
                        <input
                            className={'input-btn'}
                            placeholder={'검색창'}
                            name={'search'}
                            // onKeyUp={searchHandler}
                            // onKeyUp={SearchHandlerComponent}
                            ref={inputVal}>
                        </input>
                    </div>
                </div>

                <div className={'select-wrapper'}>
                    <span className={'search-title'}>경력</span>
                    <select className={'select-career'} name={'userCareer'} defaultValue={'전체'} onChange={careerHandler}
                            ref={careerVal}>
                        <option value={'전체'}>전체</option>
                        <option value={'신입'}>신입</option>
                        <option value={'경력'}>경력</option>
                        <option value={'관계없음'}>경력무관</option>
                    </select>
                </div>
                <button className={'reset-btn'} onClick={resetHandler}><GrPowerReset/></button>
            </section>

        </Common>
    );
};

export default CompanyMain;