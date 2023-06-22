import React, {useEffect, useState} from 'react';
import {COMPANY, REVIEW} from "../../common/config/HostConfig";
import {Link, useNavigate} from "react-router-dom";
import ReviewStar from "../../review/StartRating/ReviewStar";
import {IoIosArrowForward} from "react-icons/io";
import viewIcon from "../../../src_assets/view-icon.png";
import PageNation from "../../common/pageNation/PageNation";
import {RxCalendar} from "react-icons/rx"

const CompanyTotal = () => {
    const [companyList, setReviewList] = useState([]);
    const [pageNation, setPageNation] = useState([]);
    const [clickCurrentPage, setClickCurrentPage] = useState(1);

    useEffect(() => {
        asyncCompanyTotalList();
    }, [clickCurrentPage])

    const asyncCompanyTotalList = async () => {
        console.log(`asyncCompanyTotalList 실행`)
        // const responseUrl = `/list?page=${clickCurrentPage}&size=10`
        const res = await fetch(`${COMPANY}/list`, {
            method: 'GET',
            headers: {'content-type': 'application/json'}
        });

        if (res.status === 500) {
            alert('잠시 후 다시 접속해주세요.[서버오류]');
            return;
        }

        const result = await res.json();
        console.log(`전체 result : `, result)

        setPageNation(result.pageInfo);

        const companyLists = result.companyList

        const modifyCompanyList = companyLists.map((list) => {
            let endDate = list.companyEnddate;
            if (list.companyEnddate.includes('채용시까지')) {
                endDate = endDate.split("채용시까지")[1].trim();
            }
            const formattedEndDate = convertToEndDate(endDate);
            return {...list, dDay: formattedEndDate}
        });

        setReviewList(modifyCompanyList);
        console.log(`modifyCompanyList의 값 : `, modifyCompanyList)
    }

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

        // console.log(`formattedDdayDate 값 : `,formattedDdayDate);
        return formattedDdayDate;
    }


    const currentPageHandler = (clickPageNum) => {
        console.log(`페이지 클릭 시 현재 페이지 번호 : ${clickPageNum}`)
        setClickCurrentPage(clickPageNum);
    }

    const redirection = useNavigate();

    return (
        /*
        <>
            {reviewList.map((review) => (
                <section className={'review-list'}>

                    <div className={'company-info-wrapper'}>
                        <span className={'company'}>{review.reviewCompanyName}</span>
                        <ReviewStar starCount={review.reviewRating}/>
                    </div>
                    <section className={'text-wrapper'}>
                        <div className={'main-content'}>
                            <div className={'text-title'}>{review.reviewTitle}</div>
                            <div className={'detail-wrapper'}>
                                <div className={'detail-reviewJob'}><span
                                    className={'sub-title'}>직무</span> {review.reviewJob}</div>
                                <div className={'detail-reviewJob'}><span
                                    className={'sub-title'}>근속년수</span>{review.reviewTenure}년
                                </div>
                                <div className={'detail-reviewJob'}><span
                                    className={'sub-title'}>위치</span>{review.reviewLocation}</div>
                            </div>

                        </div>
                        <div className={'text-content'}>{review.reviewContent}</div>
                    </section>
                    <div className={'right-section'}>
                        <Link to={`/reviews/detail/${review.reviewIdx}`} className={'text'} onClick={loginCheckHandler}>
                            <div className={'go-detail'}>
                                <div className={'go-detail-text'}>더보기</div>
                                <i className={'go-detail-icon'}><IoIosArrowForward/></i>
                            </div>
                        </Link>

                        <section className={'review-info'}>
                            <div className={'icon-wrapper'}>
                                <div className={'view-count-wrapper'}>
                                    <img src={viewIcon} alt={'view-count'} className={'view-count-icon'}/>
                                    <span>{review.reviewView}</span>
                                </div>
                            </div>
                            <div className={'write-date'}>{review.reviewDate}</div>
                        </section>
                    </div>
                </section>
            ))}

            <ul>
                <PageNation
                    pageNation={pageNation}
                    currentPageHandler={currentPageHandler}
                    clickCurrentPage={clickCurrentPage} />
            </ul>
        </>
         */
        <>
            <section className={'sort-wrapper'}>
                <span className={'sort-dDay'}>D-day</span>
                <span className={'sort-career'}>경력</span>
                <span className={'sort-companyName'}>회사명</span>
                <span className={'sort-title'}>채용내용</span>
                <span className={'sort-date'}>날짜</span>
            </section>

            <section className={'company-list'}>
                <div className={'d-day'}>D-23</div>
                <div className={'company-career'}>신입</div>
                <div className={'companyName'}>토탈QA마케팅</div>
                <section className={'title-wrapper'}>
                    <div className={'title'}>홈페이지 제작 신입사원 및 팀장님 모십니다.</div>

                    <div className={'info-wrapper'}>
                        <div className={'info-salary-text'}>
                            <span className={'info-title'}>월급</span>
                            <span className={'info-content'}>220만원 ~ 500만원</span>
                        </div>
                        <div className={'info-location-text'}>
                            <span className={'info-title'}>위치</span>
                            <span className={'info-content'}>경기도 수원시</span>
                        </div>
                    </div>
                </section>

                <div className={'date-wrapper'}>
                    <RxCalendar className={'date-icon'}/>
                    <span className={'date'}>2023-06-21 ~ 2023-07-07</span>
                </div>
            </section>

            <section className={'company-list'}>
                <div className={'d-day'}>D-23</div>
                <div className={'company-career'}>신입</div>
                <div className={'companyName'}>토탈QA마케팅</div>
                <section className={'title-wrapper'}>
                    <div className={'title'}>홈페이지 제작 신입사원 및 팀장님 모십니다.</div>

                    <div className={'info-wrapper'}>
                        <div className={'info-salary-text'}>
                            <span className={'info-title'}>월급</span>
                            <span className={'info-content'}>220만원 ~ 500만원</span>
                        </div>
                        <div className={'info-location-text'}>
                            <span className={'info-title'}>위치</span>
                            <span className={'info-content'}>경기도 수원시</span>
                        </div>
                    </div>
                </section>

                <div className={'date-wrapper'}>
                    <RxCalendar className={'date-icon'}/>
                    <span className={'date'}>2023-06-21 ~ 2023-07-07</span>
                </div>
            </section>


        </>
    );
};

export default CompanyTotal;