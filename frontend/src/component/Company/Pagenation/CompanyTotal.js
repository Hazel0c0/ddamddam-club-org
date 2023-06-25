import React, {useEffect, useState} from 'react';
import {COMPANY, REVIEW} from "../../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import {RxCalendar} from "react-icons/rx"
import {Spinner} from 'reactstrap';

const CompanyTotal = ({searchKeyword, searchValue, searchCareer}) => {
    const [companyList, setCompanyList] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [page, setPage] = useState(1);

    const [finalPage, setFinalPage] = useState(1);

    //워크넷 링크 상태관리
    const [goWorknet, setGoWorknet] = useState([]);
    useEffect(() => {
        if (finalPage >= page) {
            fetchData(page);
        }
        if (finalPage > 1 && finalPage === page) {
            setIsLoading(false);
            console.log(`page : `,page)
            console.log(`finalPage : `,finalPage)
        }
        console.log(`finalPage : `, finalPage)
    }, [page, searchKeyword, searchValue, searchCareer]);

    useEffect(() => {
        // 스크롤 이벤트 리스너 등록
        window.addEventListener('scroll', handleScroll);

        return () => {
            // 컴포넌트 언마운트 시 스크롤 이벤트 리스너 제거
            window.removeEventListener('scroll', handleScroll);
        };
    }, []);

    const fetchData = async (page) => {
        console.log(`set 후 현재 page : `, page)
        console.log(`set 후 현재 finalPage : `, finalPage)

        setIsLoading(true)
        // const res = await fetch(`${COMPANY}/search?keyword=&page=${page}&size=10$sort={}`, {
        // const res = await fetch(`${COMPANY}/list?page=${page}&size=10`, {
        const res = await fetch(`${COMPANY}/list?page=${page}&size=10`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }
        console.log(`현재 페이지 url : ${COMPANY}/list?page=${page}&size=10`)
        const result = await res.json();

        //마지막 페이지 계산해서 로딩 막기
        const totalCount = result.pageInfo.totalCount;
        const totalPage = Math.ceil(totalCount / 10);
        setFinalPage(totalPage);

        const companyLists = result.companyList

        //list가공
        const modifyCompanyList = companyLists.map((list) => {
            let endDate = list.companyEnddate;
            if (list.companyEnddate.includes('채용시까지')) {
                endDate = endDate.split("채용시까지")[1].trim();
            }
            const formattedEndDate = convertToEndDate(endDate);

            let modifyLocation = list.companyArea.split(" ");
            let setModifyLocation = modifyLocation[0] + " " + modifyLocation[1];

            return {...list, companyEnddate: endDate, dDay: formattedEndDate, companyArea: setModifyLocation}
        });

        setGoWorknet((prevGoWorknet) => [...prevGoWorknet, ...new Array(companyLists.length).fill(false)]);
        setCompanyList((prevCompanyList) => [...prevCompanyList, ...modifyCompanyList]);
        setIsLoading(false)
        console.log(`modifyCompanyList : `,modifyCompanyList)

    }

    let throttleTimer = null;
    const handleScroll = () => {
        if (throttleTimer !== null) return;
        if (finalPage > 1 && finalPage === page) {
            setIsLoading(false);
           return;
        }


        setIsLoading(false)

        throttleTimer = setTimeout(() => {
            const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
            const windowHeight = window.innerHeight || document.documentElement.clientHeight;
            const documentHeight = document.documentElement.scrollHeight;
            if (scrollTop + windowHeight >= documentHeight - 200 && !isLoading) {
                setPage((prevPage) => prevPage + 1);
            }
            throttleTimer = null;
        }, 1000)

        setIsLoading(true)

    }

    //d-day계산
    const convertToEndDate = (endDate) => {
        const currentYear = new Date().getFullYear();
        const [endYear, endMonth, endDay] = endDate.split('-');

        //년도에 4자리로 만들기
        const formattedEndYear = currentYear - (currentYear % 100) + parseInt(endYear);
        // const formattedEndYear = `20${endYear}`;

        //yyyy-mm-dd
        const formattedEndDate = new Date(`${formattedEndYear}-${endMonth}-${endDay}`);

        const startDate = new Date();
        const timeDiff = formattedEndDate.getTime() - startDate.getTime();
        const dateDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));
        const formattedDdayDate = `D-${dateDiff}`

        return formattedDdayDate;
    }

    const showLinkHandler = (index,companyName) => {
        const updatedGoWorknet = goWorknet.map((item, i) => (i === index ? true : false));
        setGoWorknet(updatedGoWorknet);
    }
    const hiddenLinkHandler = (index) => {
        setGoWorknet(new Array(goWorknet.length).fill(false));
    }
    return (
        <>
            <section className={'sort-wrapper'}>
                <span className={'sort-dDay'}>D-day</span>
                <span className={'sort-career'}>경력</span>
                <span className={'sort-companyName'}>회사명</span>
                <span className={'sort-title'}>채용내용</span>
                <span className={'sort-date'}>날짜</span>
            </section>
            {companyList.map((company, index) => (
                <>
                    <section
                        key={`${company.companyIdx}-${index}`}
                        className={'company-list'}
                        onMouseEnter={() => showLinkHandler(index)}
                        onMouseLeave={() => hiddenLinkHandler(index)}
                    >
                        <div className={'d-day'}>{company.dDay}</div>
                        <div className={'company-career'}>{company.companyCareer}</div>
                        <div className={'companyName'}>{company.companyName}</div>
                        <section className={'title-wrapper'}>
                            <div className={'title'}>{company.companyTitle}</div>

                            <div className={'info-wrapper'}>
                                <div className={'info-salary-text'}>
                                    <span className={'info-title'}>월급</span>
                                    <span className={'info-content'}>{company.companySal}</span>
                                </div>
                                <div className={'info-location-text'}>
                                    <span className={'info-title'}>위치</span>
                                    <span className={'info-content'}>{company.companyArea}</span>
                                </div>
                            </div>
                        </section>

                        <div className={'date-wrapper'}>
                            <RxCalendar className={'date-icon'}/>
                            <span className={'date'}>{company.companyDate} ~ {company.companyEnddate}</span>
                        </div>
                        {goWorknet[index] &&
                            <button onClick={() => window.open(`${company.companyUrl}`, '_blank')}
                                    className={'go-worknet'}>
                                클릭시 워크넷 채용정보 페이지로 이동합니다.
                            </button>
                        }

                    </section>

                </>
            ))}

            {isLoading &&
                <div className={'grow-wrapper'}>
                    <Spinner type={"grow"}></Spinner>
                    <Spinner type={"grow"}></Spinner>
                    <Spinner type={"grow"}></Spinner>
                </div>
            }
        </>
    )
        ;
};

export default CompanyTotal;
